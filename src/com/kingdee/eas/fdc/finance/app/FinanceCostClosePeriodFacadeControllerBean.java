package com.kingdee.eas.fdc.finance.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.CostSplitCollection;
import com.kingdee.eas.fdc.aimcost.CostSplitFactory;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.util.FDCChecker;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.FIProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ISettledMonthly;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.SettledMonthlyInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class FinanceCostClosePeriodFacadeControllerBean extends AbstractFinanceCostClosePeriodFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.finance.app.FinanceCostClosePeriodFacadeControllerBean");

	private ProjectPeriodStatusInfo getPrjPeriodStatus(Context ctx, String prjId) throws BOSException, EASBizException {
		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		if (ctx == null) {
			prjInfo = ProjectPeriodStatusFactory.getRemoteInstance().getProjectPeriodStatusCollection(viewPrj).get(0);
		} else {
			prjInfo = ProjectPeriodStatusFactory.getLocalInstance(ctx).getProjectPeriodStatusCollection(viewPrj).get(0);
		}
		return prjInfo;
	}

	/**
	 * 月结
	 */
	protected String _traceFinanceCostClose(Context ctx, List idList) throws BOSException, EASBizException {
		// 将付款拆分数据存入月结数据表，用做竣工结算的已实现成本
 		String strSuccess = null;
 		String strFaild = null;
		String strUnHandle = null;
		String strNoCost = null;
		String strHasClose = null;
		String strNoUse = null;
		String strNoPeirod = null;
		String strNotAuditCon = null;
		String strExitPayment = null;
		String strExitWorkLoad = null;// 存在未审批的工程量确认单
		//审批状态的付款单的状态是12
		//即判断是否存在没有审批的付款单
		//判断时排除掉暂缓的单据 add by zhiyuan_tang 2011-01-12
		StringBuffer checkPayment = new StringBuffer();
		checkPayment.append("select a.fnumber,c.fnumber,a.fbillstatus,c.fid from  t_cas_paymentbill a ");
		checkPayment.append(" inner join T_Con_ContractBill contract on contract.fid=a.fcontractbillid ");
		checkPayment.append(" inner join t_con_payrequestbill b on a.ffdcPayReqID=b.fid ");
		checkPayment.append(" inner join t_bd_period c on c.fid=b.fperiodid " );
		checkPayment.append(" inner join t_fdc_curproject d on d.fid=a.fcurprojectid" );
		checkPayment.append(" left join T_FNC_FDCPaymentBill fdcPayment on a.fid=fdcPayment.fPaymentBillID" );
		checkPayment.append(" where  a.fbillstatus<12 and c.fid=? and d.fid=? and contract.fiscostsplit=1 " );
		checkPayment.append(" and ( fdcPayment.fisRespite is null or fdcPayment.fisRespite = 0 ) " );
		
		String checkWorkLoad = "select fnumber from  T_FNC_WorkLoadConfirmBill bill where bill.fcurProjectId=? and bill.fperiodid=? and bill.fstate<>? and bill.fisRespite=0 ";

		Connection cn = null;
		try {
			cn = getConnection(ctx);
			// 用于批量保存
			IORMappingDAO iSettleMonth = ORMappingDAO.getInstance(new SettledMonthlyInfo().getBOSType(), ctx, cn);

			for (int i = 0, size = idList.size(); i < size; i++) {
				String prjId = idList.get(i).toString();

				// 先检查，以免计算完毕，不能月结，浪费时间 
				//重复的校验，不需要了，而且不支持满足条件的月结  by sxhong 2009-07-31 20:24:49
//				 ProjectPeriodStatusUtil.checkNext(ctx, prjId, false);

				CurProjectInfo curprjInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)));
				String numberAndName = curprjInfo.getLongNumber().replace('!', '.') + " " + curprjInfo.getDisplayName() + " ";
				// 先把表给锁进来,避免并发的影响
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("update t_fnc_projectperiodstatus set fstartperiodid=fstartperiodid where fprojectid=");
				builder.appendParam(prjId);
				builder.executeUpdate();
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, false);

				ProjectPeriodStatusUtil.doImportData(ctx, prjId, false, currentPeriod);

				// 下一个期间是否存在
				PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(ctx, currentPeriod);
				if (nextPeriod == null) {
					if (strNoPeirod == null)
						strNoPeirod = "";
					strNoPeirod = strNoPeirod + numberAndName;
					continue;
				}
				String nextPeriodStr = Integer.toString(nextPeriod.getNumber());
				String currentPeriodStr = Integer.toString(currentPeriod.getNumber());
				String currentPeriodId = currentPeriod.getId().toString();
				ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, prjId);

				if (!prjInfo.isIsClosed()) {
					if (strNoUse == null)
						strNoUse = "";
					strNoUse = strNoUse + numberAndName;
					continue;
				} else if (!prjInfo.isIsCostEnd()) {
					if (strNoCost == null)
						strNoCost = "";
					strNoCost = strNoCost + numberAndName;
					continue;
				} else if (prjInfo.isIsFinacialEnd()) {
					if (strHasClose == null)
						strHasClose = "";
					strHasClose = strHasClose + numberAndName;
					continue;
				}

				// 存在未审批的无文本合同
				FilterInfo filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				filterCon.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				//暂缓的不处理
				filterCon.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				if (ContractWithoutTextFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotAuditCon == null)
						strNotAuditCon = "";
					strNotAuditCon = strNotAuditCon + numberAndName;
					continue;
				}

				// 存在未审批的付款单
				builder.clear();
				builder.appendSql(checkPayment.toString());
				builder.addParam(currentPeriodId);
				builder.addParam(prjId);
				IRowSet rowSet = builder.executeQuery(ctx);
				if (rowSet != null && rowSet.next()) {
					if (strExitPayment == null)
						strExitPayment = "";
					strExitPayment = strExitPayment + numberAndName;
					continue;
				}

				// 存在未审批的工程量确认单
				builder.clear();
				builder.appendSql(checkWorkLoad);
				builder.addParam(prjId);
				builder.addParam(currentPeriodId);
				builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
				rowSet = builder.executeQuery();
				if (rowSet != null && rowSet.next()) {
					if (strExitWorkLoad == null)
						strExitWorkLoad = "";
					strExitWorkLoad = strExitWorkLoad + numberAndName;
					continue;
				}
				

				// 待处理合同
				FilterInfo filterPrj = new FilterInfo();
				filterPrj.getFilterItems().add(new FilterItemInfo("curProject.id", prjId));
				filterPrj.getFilterItems().add(new FilterItemInfo("conSplitExecState", ConSplitExecStateEnum.INVALID_VALUE, CompareType.EQUALS));
				filterPrj.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
				filterPrj.getFilterItems().add(new FilterItemInfo("isCoseSplit", Boolean.TRUE, CompareType.EQUALS));
				if (ContractBillFactory.getLocalInstance(ctx).exists(filterPrj)) {
					if (strUnHandle == null)
						strUnHandle = "";
					strUnHandle = strUnHandle + numberAndName;
					continue;
				}
				// 待处理无文本
				filterPrj = new FilterInfo();
				filterPrj.getFilterItems().add(new FilterItemInfo("curProject.id", prjId));
				filterPrj.getFilterItems().add(new FilterItemInfo("conSplitExecState", ConSplitExecStateEnum.INVALID_VALUE, CompareType.EQUALS));
				filterPrj.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
				filterPrj.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE, CompareType.EQUALS));
				if (ContractWithoutTextFactory.getLocalInstance(ctx).exists(filterPrj)) {
					if (strUnHandle == null)
						strUnHandle = "";
					strUnHandle = strUnHandle + numberAndName;
					continue;
				}

				// 是否存在未拆分的数据
				if (hasNoSplitData(ctx, prjId, currentPeriod)) {
					if (strFaild == null)
						strFaild = "";
					strFaild = strFaild + numberAndName;
					continue;
				}

				/***************************************************************
				 * 调整凭证新加校验 是否当前当前期间及以前的来源系统为房地产的付款单都生成凭证，若不是，则不能财务成本月结
				 * 是否所有的调整单都已生成凭证，若不是，则不能财务成本月结
				 */
				// EntityViewInfo viewInfo = new EntityViewInfo();
				// FilterInfo filter = new FilterInfo();
				// filter.getFilterItems().add(new
				// FilterItemInfo("fiVouchered",Boolean.FALSE,CompareType.EQUALS));
				// filter.getFilterItems().add(new
				// FilterItemInfo("sourceType",new
				// Integer(SourceTypeEnum.FDC_VALUE),CompareType.EQUALS));
				/***************************************************************
				 * 以上为校验
				 */
				saveToSettledMonth(ctx, iSettleMonth, prjId, currentPeriod, nextPeriod);
				FDCCostRptFacadeFactory.getLocalInstance(ctx).saveSnapShot(prjId, CostMonthlySaveTypeEnum.FINANCEMONTHLY);

				ProjectPeriodStatusUtil.next(ctx, prjId, false);

				if (strSuccess == null)
					strSuccess = "";
				strSuccess = strSuccess + curprjInfo.getLongNumber().replace('!', '.') + curprjInfo.getDisplayName() + " ";
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		} finally {
			SQLUtils.cleanup(cn);
		}

		String end = null;
		if (strSuccess != null) {
			strSuccess = "财务成本月结成功项目：" + strSuccess + "\n";
		} else {
			strSuccess = "";
		}
		if (strHasClose != null) {
			strHasClose = "已经财务成本月结,不能财务成本月结的项目：" + strHasClose + "\n";
		} else {
			strHasClose = "";
		}
		if (strNotAuditCon != null) {
			strNotAuditCon = "存在未审批的无文本合同，不能财务成本月结的项目：" + strNotAuditCon + "\n";
		} else {
			strNotAuditCon = "";
		}
		// rowSet
		if (strExitPayment != null) {
			strExitPayment = "存在未审批的付款单，不能财务成本月结的项目：" + strExitPayment + "\n";
		} else {
			strExitPayment = "";
		}
		if (strExitWorkLoad!= null) {
			strExitWorkLoad = "存在未审批的工程量确认单，不能财务成本月结的项目：" + strExitWorkLoad + "\n";
		} else {
			strExitWorkLoad = "";
		}
		
		if (strUnHandle != null) {
			strUnHandle = "存在待处理数据，不能财务成本月结的项目：" + strUnHandle + "\n";
		} else {
			strUnHandle = "";
		}
		
		if (strFaild != null) {
			strFaild = "存在未拆分数据，不能财务成本月结的项目：" + strFaild + "\n";
		} else {
			strFaild = "";
		}
		
		if (strNoCost != null) {
			strNoCost = "成本未月结，不能财务成本月结的项目：" + strNoCost + "\n";
		} else {
			strNoCost = "";
		}
		if (strNoUse != null) {
			strNoUse = "未启用(即未结束初始化)，不能财务成本月结的项目: " + strNoUse + "\n";
		} else {
			strNoUse = "";
		}

		if (strNoPeirod != null) {
			strNoPeirod = "下一个期间不存在,需要先维护基础资料[期间].不能成本月结的项目: " + strNoPeirod + "\n";
		} else {
			strNoPeirod = "";
		}

		end = strSuccess + strNoPeirod + strNoUse + strNoCost + strExitPayment+strExitWorkLoad+ strHasClose + strNotAuditCon +strUnHandle+strFaild;

		return end;
	}

	/**
	 * 先抽出方法,以后再优化
	 * 
	 * @param ctx
	 * @param iSettleMonth
	 * @param prjId
	 * @param currentPeriod
	 * @param nextPeriod
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void saveToSettledMonth(Context ctx, IORMappingDAO iSettleMonth, String prjId, PeriodInfo currentPeriod, PeriodInfo nextPeriod) throws BOSException, EASBizException, SQLException {
		String currentPeriodStr = Integer.toString(currentPeriod.getNumber());
		String currentPeriodId = currentPeriod.getId().toString();

		Set set = new HashSet();
		// 付款拆分

		Set splitSet = new HashSet();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct splitentry.fparentid from t_fnc_paymentsplitentry splitentry "
				+ "where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account "
				+ "where account.FCurProject=?) and splitentry.fparentid in ("
				+ "select split.fid from t_fnc_paymentsplit split where (split.fstate<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid=?))"
				+ "and split.fperiodid in (" + "select period.fid from t_bd_period period where period.fnumber<=?))");

		builder.addParam(prjId);
		builder.addParam(currentPeriodId);
		builder.addParam(currentPeriodStr);
		IRowSet rowSetSplit = builder.executeQuery();
		while (rowSetSplit.next()) {
			splitSet.add(rowSetSplit.getString("fparentid"));
		}

		if (splitSet.size() > 0) {
			// SysUtil.abort();
			EntityViewInfo viewContractSplit = new EntityViewInfo();
			FilterInfo filterContractSplit = new FilterInfo();
			// filterContractSplit.getFilterItems().add(
			// new FilterItemInfo("paymentBill.curProject.id", prjId));
			// filterContractSplit.getFilterItems().add(
			// new FilterItemInfo("state",
			// FDCBillStateEnum.INVALID_VALUE,
			// CompareType.NOTEQUALS));
			// filterContractSplit.getFilterItems().add(
			// new FilterItemInfo("period.number", Integer
			// .toString(currentPeriod.getNumber()),
			// CompareType.LESS_EQUALS));
			filterContractSplit.getFilterItems().add(new FilterItemInfo("id", splitSet, CompareType.INCLUDE));
			viewContractSplit.setFilter(filterContractSplit);
			viewContractSplit.getSelector().add("isWorkLoadBill");
			viewContractSplit.getSelector().add("id");
			viewContractSplit.getSelector().add("paymentBill.contractBillId");
			viewContractSplit.getSelector().add("workLoadConfirmBill.contractBill.id");
			PaymentSplitCollection payment = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(viewContractSplit);
			PaymentSplitInfo paymentInfo = new PaymentSplitInfo();
			// SettledMonthlyInfo settle = new SettledMonthlyInfo();
			for (Iterator it = payment.iterator(); it.hasNext();) {
				paymentInfo = (PaymentSplitInfo) (it.next());
				BOSObjectType bosType = paymentInfo.getBOSType();
				String objectId = paymentInfo.getId().toString();
				if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(bosType.toString());
					settle.setObjectID(objectId);
					settle.setSettlePeriod(currentPeriod);
					String contractid = null;
					if (paymentInfo.isIsWorkLoadBill()) {
						contractid = paymentInfo.getWorkLoadConfirmBill().getContractBill().getId().toString();
					} else {
						contractid = paymentInfo.getPaymentBill().getContractBillId();
					}
					settle.setContractID(contractid);
					// settleMonth.addnew(settle);
					settle.setIsCost(false);
					iSettleMonth.addNewBatch(settle);
				}
				set.add(paymentInfo.getId().toString());
			}

			// 付款拆分中间表
			if (set.size() > 0) {
				EntityViewInfo viewConCostSplit = new EntityViewInfo();
				FilterInfo filterConCostSplit = new FilterInfo();
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.PAYMENTSPLIT, CompareType.EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("costBillId");
				viewConCostSplit.getSelector().add("splitBillId");
				CostSplitCollection conCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				CostSplitInfo conCostInfo = new CostSplitInfo();

				for (Iterator it = conCost.iterator(); it.hasNext();) {
					conCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conCostInfo.getBOSType();
					String objectId = conCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						SettledMonthlyInfo settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(bosType.toString());
						settle.setObjectID(objectId);
						settle.setSettlePeriod(currentPeriod);

						SelectorItemCollection selectorContractID = new SelectorItemCollection();
						selectorContractID.add("id");
						selectorContractID.add("paymentBill.contractBillId");
						selectorContractID.add("isWorkLoadBill");
						selectorContractID.add("workLoadConfirmBill.contractBill.id");
						String contractid = null;
						PaymentSplitInfo contractIDInfo = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitInfo(new ObjectUuidPK(conCostInfo.getSplitBillId()), selectorContractID);
						if (contractIDInfo.isIsWorkLoadBill()) {
							contractid = contractIDInfo.getWorkLoadConfirmBill().getContractBill().getId().toString();
						} else {
							contractid = contractIDInfo.getPaymentBill().getContractBillId();
						}

						settle.setContractID(contractid);
						// settleMonth.addnew(settle);
						settle.setIsCost(false);
						iSettleMonth.addNewBatch(settle);
					}
				}

				// 无文本合同付款拆分中间表
				viewConCostSplit = new EntityViewInfo();
				filterConCostSplit = new FilterInfo();
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.NOTEXTCONSPLIT, CompareType.EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("costBillId");
				viewConCostSplit.getSelector().add("splitBillId");
				CostSplitCollection conWithoutTextCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				CostSplitInfo conWithoutTextCostInfo = new CostSplitInfo();
				for (Iterator it = conWithoutTextCost.iterator(); it.hasNext();) {
					conWithoutTextCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conWithoutTextCostInfo.getBOSType();
					String objectId = conWithoutTextCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						SettledMonthlyInfo settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(bosType.toString());
						settle.setObjectID(objectId);
						settle.setSettlePeriod(currentPeriod);

						SelectorItemCollection selectorContractID = new SelectorItemCollection();
						selectorContractID.add("id");
						selectorContractID.add("paymentBill.contractBillId");
						PaymentSplitInfo contractIDInfo = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitInfo(new ObjectUuidPK(conWithoutTextCostInfo.getSplitBillId()), selectorContractID);

						settle.setContractID(contractIDInfo.getPaymentBill().getContractBillId());
						// settleMonth.addnew(settle);
						settle.setIsCost(false);
						iSettleMonth.addNewBatch(settle);
					}
				}
			}
		}

		// 最终批量提交数据库
		iSettleMonth.executeBatch();

	}

	/**
	 * 存在未拆分数据,存在返回true,不存在返回false 
	 * @param ctx
	 * @param prjId
	 * @param currentPeriod
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private boolean hasNoSplitData(Context ctx, String prjId, PeriodInfo currentPeriod) throws BOSException, SQLException {
		Integer currentPeriodNumber = new Integer(currentPeriod.getNumber());
		// 按最新与罗忠慧的沟通结果只要判断当前期间之前最新版本的拆分单据id与单据相等就可以确认当前期间是否全部拆分完成了
		// 因为之前已经做了待处理的检验,只要存在作废的,则之前一定是拆过的.
		// 1.判断工程量拆分
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select count(*) as count from ( \n");
		builder.appendSql("	select distinct fid from T_FNC_WorkLoadConfirmBill where fcurProjectId=? and fisRespite=0 and fperiodId in (select fid from t_bd_period period where period.fnumber<=?) \n");
		builder.appendSql("	union all  \n");
		builder.appendSql("	select distinct  bill.fid from t_Cas_Paymentbill bill \n");
		builder.appendSql("	inner join t_Con_Payrequestbill payReq on payReq.fid=bill.ffdcpayReqid  \n");
		//TODO by zhiyuan_tang 应该是未暂缓的单据
		builder.appendSql(" left join T_FNC_FDCPaymentBill fdcPayment on bill.fid=fdcPayment.fPaymentBillID" );
		builder.appendSql("	where bill.fcurProjectId=? and  payReq.fperiodid in (select fid from t_Bd_Period where fnumber<=?)  \n");
		builder.appendSql(" and ( fdcPayment.fisRespite is null or fdcPayment.fisRespite = 0 ) " );
		builder.appendSql("	and (exists (select 1 from T_con_contractBill where fid=bill.fcontractbillid and fisCostSplit=1)  \n");
		builder.appendSql("	or exists (select 1 from t_con_contractwithouttext where fid=bill.fcontractbillid and fisCostSplit=1)) \n");
		builder.appendSql(")t \n");
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		IRowSet rowSet=builder.executeQuery();
		int billSize =0;
		if(rowSet!=null&&rowSet.next()){
			billSize=rowSet.getInt("count");
		}

		// 拆分单数量
		builder.clear();
		builder.appendSql("select count(*) as count from ( \n");
		builder.appendSql("select distinct fworkLoadBillId from T_FNC_PaymentSplit  split inner join T_FNC_WorkLoadConfirmBill bill on split.fworkLoadBillId=bill.fid where split.fcurProjectId=? and split.fislastVerThisPeriod=1 and bill.fisRespite=0 ");
		builder.appendSql(" and split.fperiodId in (select fid from t_bd_period period where period.fnumber<=?) ");
		builder.appendSql(" union all ");
		builder.appendSql("select distinct split.fpaymentbillId from T_FNC_PaymentSplit  split inner join T_CAS_PaymentBill bill on split.fpaymentbillId=bill.fid ");
		//TODO by zhiyuan_tang 应该是未暂缓的单据
		builder.appendSql(" left join T_FNC_FDCPaymentBill fdcPayment on bill.fid=fdcPayment.fPaymentBillID" );
		builder.appendSql(" where split.fcurProjectId=? and split.fislastVerThisPeriod=1 ");
		builder.appendSql(" and split.fperiodId in (select fid from t_bd_period period where period.fnumber<=?) ");
		builder.appendSql(" and ( fdcPayment.fisRespite is null or fdcPayment.fisRespite = 0 ) " );
		builder.appendSql(")t \n");
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		rowSet = builder.executeQuery();
		int splitSize=0;
		if(rowSet!=null&&rowSet.next()){
			splitSize=rowSet.getInt("count");
		}
		return splitSize!=billSize;
	}

	/**
	 * 冻结
	 */
	protected void _frozenProject(Context ctx, List idList) throws BOSException, EASBizException {
		for (int i = 0, size = idList.size(); i < size; i++) {
			ProjectPeriodStatusUtil.freeze(ctx, idList.get(i).toString(), false);
			// ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, idList
			// .get(i).toString());
			// prjInfo.setIsFinaclaFreeze(true);
			// SelectorItemCollection selector = new SelectorItemCollection();
			// selector.add("isFinaclaFreeze");
			// ProjectPeriodStatusFactory.getLocalInstance(ctx).updatePartial(
			// prjInfo, selector);
		}
	}

	/**
	 * 反月结
	 */
	protected String _antiCostClose(Context ctx, List idList) throws BOSException, EASBizException {
		// 仅改写了期间，其他操作没有补充
		String end = null;
		String strSuccess = null;
		String strEnd = null;
		String strEqualStart = null;
		String strNotEqual = null;
		String strNotCompany = null;
		String strHasFIProduct = null;
		String strHasDynamicCost = null;
		String strHasProjectIndex = null;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("longNumber");
		sic.add("displayName");
		sic.add("fullOrgUnit.id");

		for (int i = 0, size = idList.size(); i < size; i++) {
			String prjId = idList.get(i).toString();

			// 先检查期间能否反
			// ProjectPeriodStatusUtil.checkLast(ctx, prjId, true);

			CurProjectInfo curprjInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)), sic);

			PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, false);
			PeriodInfo shouldPeriod = PeriodUtils.getPrePeriodInfo(ctx, currentPeriod);
			BOSUuid companyID = curprjInfo.getFullOrgUnit().getId();
			PeriodInfo companyPeriod = SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, new ObjectUuidPK(companyID));

			ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, prjId);
			PeriodInfo startPeriod = prjInfo.getStartPeriod();

			String numberName = curprjInfo.getLongNumber().replace('!', '.') + " " + curprjInfo.getDisplayName() + " ";
			if (prjInfo.isIsEnd()) {
				// 已经关闭
				if (strEnd == null)
					strEnd = "";
				strEnd = strEnd + numberName;
				continue;
			} else if (currentPeriod.getId().toString().equals(startPeriod.getId().toString())) {
				// 在启动期间不能反月结
				if (strEqualStart == null)
					strEqualStart = "";
				strEqualStart = strEqualStart + numberName;
				continue;
			} else if (prjInfo.isIsCostEnd() && !prjInfo.isIsFinacialEnd()) {
				// 成本已经月结
				if (strNotEqual == null)
					strNotEqual = "";
				strNotEqual = strNotEqual + numberName;
				continue;
			} else if (companyPeriod.getNumber() >= currentPeriod.getNumber()) {
				// 财务组织的当前期间大于当前工程项目的期间
				if (strNotCompany == null)
					strNotCompany = "";
				strNotCompany = strNotCompany + numberName;
				continue;
			}

			// 存在期间在此期间的竣工结算帐务处理
			FilterInfo filterProduct = new FilterInfo();
			filterProduct.getFilterItems().add(new FilterItemInfo("curProjProductEntries.curProject.id", prjId));
			filterProduct.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(shouldPeriod.getNumber()), CompareType.GREATER));
			if (FIProductSettleBillFactory.getLocalInstance(ctx).exists(filterProduct)) {
				if (strHasFIProduct == null)
					strHasFIProduct = "";
				strHasFIProduct = strHasFIProduct + numberName;
				continue;
			}

			// 检查动态成本是否已经更新
			// FilterInfo filter = new FilterInfo();
			// filter.getFilterItems().add(
			// new FilterItemInfo("account.curProject.id", prjId));
			// filter.getFilterItems().add(
			// new FilterItemInfo("period.number", Integer
			// .toString(shouldPeriod.getNumber()),
			// CompareType.GREATER));
			// if (DynamicCostFactory.getLocalInstance(ctx).exists(filter)) {
			// if (strHasDynamicCost == null)
			// strHasDynamicCost = "";
			// strHasDynamicCost = strHasDynamicCost + numberName;
			// continue;
			// }

			// 是否存在当前期间及之后的指标数据
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("projOrOrgID", prjId));
			filter.getFilterItems().add(new FilterItemInfo("period.number", Integer.toString(shouldPeriod.getNumber()), CompareType.GREATER));
			if (ProjectIndexDataFactory.getLocalInstance(ctx).exists(filter)) {
				if (strHasProjectIndex == null)
					strHasProjectIndex = "";
				strHasProjectIndex = strHasProjectIndex + numberName;
				continue;
			}

			// 更新快照
			FDCCostRptFacadeFactory.getLocalInstance(ctx).reverseFinanceMonthSettled(prjId, shouldPeriod);

			// 检查通过之后，删除当前期间的月结表中的数据
			deleteSettleMonth(ctx, prjId, shouldPeriod.getId().toString());

			// 反月结
			ProjectPeriodStatusUtil.last(ctx, prjId, false);

			if (strSuccess == null)
				strSuccess = "";
			strSuccess = strSuccess + numberName;

		}
		if (strSuccess != null) {
			strSuccess = "财务成本反月结成功项目：" + strSuccess + "\n";
		} else {
			strSuccess = "";
		}
		if (strEnd != null) {
			strEnd = "已经关闭，不能反月结的项目：" + strEnd + "\n";
		} else {
			strEnd = "";
		}
		if (strEqualStart != null) {
			strEqualStart = "当前财务成本期间等于启用期间，不能反月结的项目：" + strEqualStart + "\n";
		} else {
			strEqualStart = "";
		}
		if (strNotEqual != null) {
			strNotEqual = "成本期间大于财务成本期间，不能财务成本反月结的项目：" + strNotEqual + "\n";
		} else {
			strNotEqual = "";
		}
		if (strNotCompany != null) {
			strNotCompany = "财务组织期间与财务成本期间不匹配，不能财务成本反月结的项目：" + strNotCompany + "\n";
		} else {
			strNotCompany = "";
		}
		if (strHasFIProduct != null) {
			strHasFIProduct = "有当前期间的竣工结算帐务处理，不能财务成本反月结的项目： " + strHasFIProduct + "\n";
		} else {
			strHasFIProduct = "";
		}

		if (strHasDynamicCost != null) {
			strHasDynamicCost = "当前期间已经更新动态成本分摊设置，不能财务成本反月结的项目： " + strHasDynamicCost + "\n";
		} else {
			strHasDynamicCost = "";
		}

		if (strHasProjectIndex != null) {
			strHasProjectIndex = "当前期间已经更新指标，不能成本反月结的项目： " + strHasProjectIndex + "\n";
		} else {
			strHasProjectIndex = "";
		}

		end = strSuccess + strEnd + strEqualStart + strNotEqual + strNotCompany + strHasFIProduct + strHasDynamicCost + strHasProjectIndex;
		return end;
	}

	// 删除本月数据
	protected void deleteSettleMonth(Context ctx, String projectId, String periodId) throws BOSException, EASBizException {
		ISettledMonthly settleMonth = SettledMonthlyFactory.getLocalInstance(ctx);
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.getFilterItems().add(new FilterItemInfo("curProjectID", projectId));

		filterPrj.getFilterItems().add(new FilterItemInfo("settlePeriod.id", periodId));
		filterPrj.getFilterItems().add(new FilterItemInfo("isCost", Boolean.FALSE));

		settleMonth.delete(filterPrj);
	}

	// 下面写的一些东西待有时间再做吧
	private void beforeTrace(Context ctx, List idList) throws EASBizException, BOSException {
		initData(ctx, idList);
		handleImportData(ctx, idList);
		checkData(ctx, idList);
	}

	private Map checkData(Context ctx, List idList) {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String prjId = (String) iter.next();
		}
		return null;
	}

	/**
	 * 处理导入的数据
	 * 
	 * @param ctx
	 * @param idList
	 * @throws BOSException
	 */
	private void handleImportData(Context ctx, List idList) throws BOSException {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String prjId = (String) iter.next();
			ProjectPeriodStatusUtil.doImportData(ctx, prjId, false, getCurrentPeriod(ctx, prjId));
		}
	}

	private void initData(Context ctx, List idList) throws EASBizException, BOSException {
		Map map = (Map) ctx.get("initCurrentPeriod_currentPeriodMap");
		if (map == null) {
			map = FDCUtils.getCurrentPeriod(ctx, new HashSet(idList), false);
			ctx.put("initCurrentPeriod_currentPeriodMap", map);
		}
	}

	private PeriodInfo getCurrentPeriod(Context ctx, String prjId) {
		Map map = (Map) ctx.get("initCurrentPeriod_currentPeriodMap");
		if (map == null) {
			return null;
		}
		return (PeriodInfo) map.get(prjId);
	}

	private void clearCache(Context ctx) {
		Map map = (Map) ctx.get("initCurrentPeriod_currentPeriodMap");
		if (map != null) {
			map.clear();
			map = null;
			ctx.put("initCurrentPeriod_currentPeriodMap", map);
		}
	}

	private FDCChecker checker = null;

	private FDCChecker getChecker() {
		if (checker == null) {
			// 设置checkitem

		}
		return checker;
	}

}