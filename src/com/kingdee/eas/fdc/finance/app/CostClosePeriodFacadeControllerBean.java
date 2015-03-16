package com.kingdee.eas.fdc.finance.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryCollection;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryFactory;
import com.kingdee.eas.fdc.aimcost.AdjustRecordEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.CostSplitCollection;
import com.kingdee.eas.fdc.aimcost.CostSplitFactory;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostInfo;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.DynamicCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataCollection;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.FIProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ISettledMonthly;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.ProductSettleBillFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fdc.finance.SettledMonthlyFactory;
import com.kingdee.eas.fdc.finance.SettledMonthlyHelper;
import com.kingdee.eas.fdc.finance.SettledMonthlyInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class CostClosePeriodFacadeControllerBean extends AbstractCostClosePeriodFacadeControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.finance.app.CostClosePeriodFacadeControllerBean");

	protected void _tracePayment(Context ctx, List idList) throws BOSException, EASBizException {
		// 合同拆分
		Set set = new HashSet();
		Set idSet = FDCHelper.list2Set(idList);
		EntityViewInfo viewContractSplit = new EntityViewInfo();
		FilterInfo filterContractSplit = new FilterInfo();
		filterContractSplit.getFilterItems().add(new FilterItemInfo("contractBill.curProject.id", idSet, CompareType.INCLUDE));
		filterContractSplit.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		viewContractSplit.setFilter(filterContractSplit);
		viewContractSplit.getSelector().add("id");
		CoreBillBaseCollection contract = ContractCostSplitFactory.getLocalInstance(ctx).getCoreBillBaseCollection(viewContractSplit);
		for (Iterator it = contract.iterator(); it.hasNext();) {
			set.add(((CoreBillBaseInfo) (it.next())).getId().toString());
		}

		// 变更拆分
		EntityViewInfo viewChangeSplit = new EntityViewInfo();
		FilterInfo filterChangeSplit = new FilterInfo();
		filterChangeSplit.getFilterItems().add(new FilterItemInfo("contractChange.curProject.id", idSet, CompareType.INCLUDE));
		filterChangeSplit.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));

		viewChangeSplit.setFilter(filterChangeSplit);
		viewChangeSplit.getSelector().add("id");
		CoreBillBaseCollection change = ConChangeSplitFactory.getLocalInstance(ctx).getCoreBillBaseCollection(viewChangeSplit);
		for (Iterator it = change.iterator(); it.hasNext();) {
			set.add(((CoreBillBaseInfo) (it.next())).getId().toString());
		}

		EntityViewInfo viewPaymentSplit = new EntityViewInfo();
		FilterInfo filterPaymentSplit = new FilterInfo();
		filterPaymentSplit.getFilterItems().add(new FilterItemInfo("parent.paymentBill.curProject.id", idSet, CompareType.INCLUDE));
		filterPaymentSplit.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
		filterPaymentSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.NOTINCLUDE));

		viewPaymentSplit.setFilter(filterPaymentSplit);
		viewPaymentSplit.getSelector().add("parent.*");
		viewPaymentSplit.getSelector().add("id");

		Map values = new HashMap();

		PaymentSplitEntryCollection payment = PaymentSplitEntryFactory.getLocalInstance(ctx).getPaymentSplitEntryCollection(viewPaymentSplit);
		for (Iterator it = payment.iterator(); it.hasNext();) {
			String key = ((PaymentSplitEntryInfo) (it.next())).getParent().getId().toString();
			if (values.containsKey(key)) {
				continue;
			} else {
				values.put(key, ((PaymentSplitEntryInfo) (it.next())).getParent());
			}
		}

		// 将这部分付款拆分作废，进入待处理合同

		SysUtil.abort();
	}

	protected void _checkCostSplit(Context ctx, List idList) throws BOSException, EASBizException {
		// 检查在本期间内的成本单据是否处理完成（完成合同拆分，变更拆分，结算拆分，产品结算单）

	}

	private ProjectPeriodStatusInfo getPrjPeriodStatus(Context ctx, String prjId) throws BOSException, EASBizException {
		ProjectPeriodStatusInfo prjInfo = new ProjectPeriodStatusInfo();
		EntityViewInfo viewPrj = new EntityViewInfo();
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.appendFilterItem("project.id", prjId);
		viewPrj.setFilter(filterPrj);
		viewPrj.getSelector().add("*");
		viewPrj.getSelector().add("costPeriod.*");
		viewPrj.getSelector().add("finacialPeriod.*");
		if (ctx == null) {
			prjInfo = ProjectPeriodStatusFactory.getRemoteInstance().getProjectPeriodStatusCollection(viewPrj).get(0);
		} else {
			prjInfo = ProjectPeriodStatusFactory.getLocalInstance(ctx).getProjectPeriodStatusCollection(viewPrj).get(0);
		}
		return prjInfo;
	}

	/**
	 * 成本月结
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.finance.app.AbstractCostClosePeriodFacadeControllerBean#_traceCostClose(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.finance.app.AbstractCostClosePeriodFacadeControllerBean#_traceCostClose(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected String _traceCostClose(Context ctx, List idList) throws BOSException, EASBizException {
		String strSuccess = null;
		String strNoUse = null;
		String strNoPeirod = null;
		String strFaild = null;
		String strHasClose = null;
		String strHasProduct = null;
		String strNotCon = null;
		String strNotLoad = null;
		String strNotAuditCon = null;
		String strNotSettle = null;
		Connection cn = null;
		try {
			cn = getConnection(ctx);
			// 用于批量保存
			IORMappingDAO iSettleMonth = ORMappingDAO.getInstance(new SettledMonthlyInfo().getBOSType(), ctx, cn);
			// 月结之前，保存分摊方案到月结中间表
			// saveDyCostSplitData(ctx,idList);
			for (int i = 0, size = idList.size(); i < size; i++) {
				String prjId = idList.get(i).toString();

				// 先检查，以免计算完毕，不能月结，浪费时间(与后面的检查重复，且暂时不需要抛出异常
				// ProjectPeriodStatusUtil.checkNext(ctx, prjId, true);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("id");
				selector.add("longNumber");
				selector.add("displayName");
				selector.add("fullOrgUnit.id");
				// 工程项目
				CurProjectInfo curprjInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)));
				String prjStr = curprjInfo.getLongNumber().replace('!', '.') + " " + curprjInfo.getDisplayName() + " ";
				// 工程项目的当前期间,锁定数据库以避免并发的影响
				FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
				sql.appendSql("update t_fnc_projectperiodstatus set fstartperiodid=fstartperiodid where fprojectid=");
				sql.appendParam(prjId);
				sql.executeUpdate();
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);

				boolean isFinacial = FDCUtils.IsFinacial(ctx, curprjInfo.getFullOrgUnit().getId().toString());

				// 处理导入的数据
				ProjectPeriodStatusUtil.doImportData(ctx, prjId, true, currentPeriod);

				// 下一个期间是否存在
				PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(ctx, currentPeriod);
				if (nextPeriod == null || nextPeriod.getId() == null) {
					if (strNoPeirod == null)
						strNoPeirod = "";
					strNoPeirod = strNoPeirod + prjStr;
					continue;
				}
//				String nextPeriodStr = Integer.toString(nextPeriod.getNumber());
				String currentPeriodStr = Integer.toString(currentPeriod.getNumber());
				String currentPeriodId = currentPeriod.getId().toString();
				ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, prjId);
				if (!prjInfo.isIsClosed()) {
					if (strNoUse == null)
						strNoUse = "";
					strNoUse = strNoUse + prjStr;
					continue;
				} else if (prjInfo.isIsCostEnd()) {
					if (strHasClose == null)
						strHasClose = "";
					strHasClose = strHasClose + prjStr;
					continue;
				}

				// 是否存在未审批的合同
				FilterInfo filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				filterCon.getFilterItems().add(new FilterItemInfo("isCoseSplit", Boolean.TRUE));//只对成本类的做处理
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				//暂缓的不处理
				filterCon.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				if (ContractBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotCon == null)
						strNotCon = "";
					strNotCon = strNotCon + prjStr;
					continue;
				}

				// 是否存在未审批的工程量确认单
				FilterInfo filterLoad = new FilterInfo();
				filterLoad.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterLoad.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				filterLoad.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
				//暂缓的不处理
				filterLoad.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				if (WorkLoadConfirmBillFactory.getLocalInstance(ctx).exists(filterLoad)) {
					if (strNotLoad == null)
						strNotLoad = "";
					strNotLoad = strNotLoad + prjStr;
					continue;
				}
				
				// 是否存在未审批的结算
				filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE));//只对成本类的做处理
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				//暂缓的不处理
				filterCon.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				if (ContractSettlementBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotSettle == null)
						strNotSettle = "";
					strNotSettle = strNotSettle + prjStr;
					continue;
				}

				// 是否存在未审批的变更审批单
				filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				//TODO 审批中
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
//				filterCon.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE));//只对成本类的做处理
				//暂缓的不处理
				filterCon.getFilterItems().add(new FilterItemInfo("isRespite", Boolean.FALSE));
				filterCon.setMaskString(" #0 and #1 and (#2 or #3) and #4 ");
				if (ChangeAuditBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotAuditCon == null)
						strNotAuditCon = "";
					strNotAuditCon = strNotAuditCon + prjStr;
					continue;
				}

				// 是否存在未审批的变更指令
				filterCon = new FilterInfo();
				filterCon.getFilterItems().add(new FilterItemInfo("curproject.id", prjId));
				filterCon.getFilterItems().add(new FilterItemInfo("period.id", currentPeriodId));
				//TODO 审批中
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE));
				filterCon.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE));
				filterCon.getFilterItems().add(new FilterItemInfo("isCostSplit", Boolean.TRUE));//只对成本类的做处理
				//暂缓的不处理
				filterCon.getFilterItems().add(new FilterItemInfo("changeAudit.isRespite", Boolean.FALSE));
				filterCon.setMaskString(" #0 and #1 and (#2 or #3) and #4 and #5");
				if (ContractChangeBillFactory.getLocalInstance(ctx).exists(filterCon)) {
					if (strNotAuditCon == null)
						strNotAuditCon = "";
					strNotAuditCon = strNotAuditCon + prjStr;
					continue;
				}
				
				// 检查拆分是否有未完成的拆分

				// 判断时 加上之后期拆分的数据
				if (hasNoSplitBill(ctx, prjId, currentPeriod)) {
					if (strFaild == null)
						strFaild = "";
					strFaild = strFaild + prjStr;
					continue;
				}

				// 是否存在上一期间以前的未处理的产品结算单
				if (isFinacial) {
					FilterInfo filterProduct = new FilterInfo();
					filterProduct.getFilterItems().add(new FilterItemInfo("curProjProductEntries.curProject.id", prjId));
					filterProduct.getFilterItems().add(new FilterItemInfo("fiVouchered", Boolean.FALSE));
					filterProduct.getFilterItems().add(new FilterItemInfo("period.number", currentPeriodStr, CompareType.LESS));
					if (ProductSettleBillFactory.getLocalInstance(ctx).exists(filterProduct)) {
						if (strHasProduct == null)
							strHasProduct = "";
						strHasProduct = strHasProduct + prjStr;
						continue;
					}
				}

				// 以上为检验 by sxhong 2009-06-01 16:35:19

				saveToSettledMonth(ctx, iSettleMonth, prjId, currentPeriod, nextPeriod);

				FDCCostRptFacadeFactory.getLocalInstance(ctx).saveSnapShot(prjId, CostMonthlySaveTypeEnum.COSTMONTHLY);

				ProjectPeriodStatusUtil.next(ctx, prjId, true);
				if (strSuccess == null)
					strSuccess = "";
				strSuccess = strSuccess + prjStr;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new BOSException(e);
		} finally {
			SQLUtils.cleanup(cn);
		}

		String end = null;
		if (strSuccess != null) {
			strSuccess = "月结成功项目：" + strSuccess + "\n";
		} else {
			strSuccess = "";
		}
		if (strHasClose != null) {
			strHasClose = "已经月结，不能成本月结的项目：" + strHasClose + "\n";
		} else {
			strHasClose = "";
		}
		if (strNotCon != null) {
			strNotCon = "存在未审批的合同，不能成本月结的项目：" + strNotCon + "\n";
		} else {
			strNotCon = "";
		}
		if (strNotLoad != null) {
			strNotLoad = "存在未审批的工程量确认单，不能成本月结的项目：" + strNotLoad + "\n";
		} else {
			strNotLoad = "";
		}
		if (strNotAuditCon != null) {
			strNotAuditCon = "存在未审批的变更审批单，不能成本月结的项目：" + strNotAuditCon + "\n";
		} else {
			strNotAuditCon = "";
		}
		if (strNotSettle != null) {
			strNotSettle = "存在未审批的结算单，不能成本月结的项目：" + strNotSettle + "\n";
		} else {
			strNotSettle = "";
		}
		if (strFaild != null) {
			strFaild = "存在未拆分数据，不能成本月结的项目：" + strFaild + "\n";
		} else {
			strFaild = "";
		}
		if (strHasProduct != null) {
			strHasProduct = "上一期存在未竣工结算帐务处理的产品结算单，不能成本月结的项目：" + strHasProduct + "\n";
		} else {
			strHasProduct = "";
		}
		if (strNoUse != null) {
			strNoUse = "未启用(即未结束初始化)，不能成本月结的项目: " + strNoUse + "\n";
		} else {
			strNoUse = "";
		}

		if (strNoPeirod != null) {
			strNoPeirod = "下一个期间不存在,需要先维护基础资料[期间].不能成本月结的项目: " + strNoPeirod + "\n";
		} else {
			strNoPeirod = "";
		}
		end = strSuccess + strNoPeirod + strNoUse + strHasClose + strNotCon + strNotLoad + strNotAuditCon + strNotSettle + strFaild + strHasProduct;
		return end;
	}

	/**
	 * 是否还有没有拆分的单据 by sxhong 存在未拆分数据,存在返回true,不存在返回false
	 * 
	 * 添加以下注释 Added by Owen_wen 2011-07-21 <br>
	 * 只需要统计进态成本的数据（即fisCostSplit=1）<br>
	 * 非成本的合同折分T_CON_ConNoCostSplit、<br>
	 * 非成本的变更拆分T_CON_ConChangeNoCostSplit、<br>
	 * 非成本的结算拆分T_CON_SettNoCostSplit <br>
	 * 
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private boolean hasNoSplitBill(Context ctx, String prjId, PeriodInfo currentPeriod) throws BOSException, SQLException {
		Integer currentPeriodNumber = new Integer(currentPeriod.getNumber());
		// 取出所有之前期间的合同,变更,结算
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select count(*) as count from (");
		builder.appendSql("select distinct fid from T_Con_Contractbill where fcurProjectId=? and fisRespite=0 and fisCostSplit=1 and fisAmtWithoutCost=0 and fperiodid in \n");
		builder.appendSql("(select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql("union all \n");
		builder.appendSql("select distinct bill.fid from t_Con_Contractchangebill bill inner join T_con_contractbill con on con.fid=bill.fcontractbillid  inner join t_Con_Changeauditbill changeaudit on changeaudit.fid=bill.fchangeauditid where con.fisAmtWithoutCost=0 and bill.fcurProjectId=? and changeaudit.fisRespite=0 and bill.fisCostSplit=1 and  bill.fperiodid in \n");
		builder.appendSql("(select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql("union all \n");
		builder.appendSql("select distinct bill.fid from t_Con_Contractsettlementbill  bill inner join T_con_contractbill con on con.fid=bill.fcontractbillid where con.fisAmtWithoutCost=0 and bill.fcurProjectId=? and bill.fisRespite=0 and  bill.fisCostSplit=1 and bill.fperiodid in \n");
		builder.appendSql("(select fid from t_Bd_Period where fnumber<=?) \n");

		builder.appendSql(")t ");
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);

		IRowSet rowSet = builder.executeQuery();
		int billCount = 0;
		if (rowSet.next()) {
			billCount = rowSet.getInt("count");
		}

		// //取出所有之前期间的合同,变更,结算的拆分
		builder.clear();
		builder.appendSql("select count(*) as count from ( \n");
		builder.appendSql("select distinct fcontractBillId from t_Con_Contractcostsplit split inner join T_con_contractbill bill on split.fcontractbillid=bill.fid where split.fcurProjectId=? and split.fislastVerThisPeriod=1  and bill.fisRespite=0 \n");
		builder.appendSql("and  split.fperiodid in (select fid from t_Bd_Period where fnumber<=?)\n");
		builder.appendSql("union all  \n");
		builder.appendSql("select distinct fcontractchangeid from t_Con_Conchangesplit split inner join T_con_contractchangebill bill on split.fcontractchangeid=bill.fid inner join T_con_changeauditbill changeaudit on changeaudit.fid=bill.fchangeauditid where split.fcurProjectId=? and split.fislastVerThisPeriod=1 and changeaudit.fisRespite=0 \n");
		builder.appendSql("and  split.fperiodid in (select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql("union all \n");
		builder.appendSql("select distinct fsettlementBillid from t_con_settlementcostsplit split inner join T_con_ContractSettlementBill bill on split.fsettlementBillid=bill.fid where split.fcurProjectId=? and fislastVerThisPeriod=1  and bill.fisRespite=0 \n");
		builder.appendSql("and  split.fperiodid in (select fid from t_Bd_Period where fnumber<=?) \n");
		builder.appendSql(") t  \n");

		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);
		builder.addParam(prjId);
		builder.addParam(currentPeriodNumber);

		int splitCount = 0;
		rowSet = builder.executeQuery();
		if (rowSet.next()) {
			splitCount = rowSet.getInt("count");
		}

		return billCount != splitCount;
	}

	/**
	 * 冻结
	 */
	protected void _frozenProject(Context ctx, List idList) throws BOSException, EASBizException {
		for (int i = 0, size = idList.size(); i < size; i++) {
			ProjectPeriodStatusUtil.freeze(ctx, idList.get(i).toString(), true);
			// ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, idList
			// .get(i).toString());
			// prjInfo.setIsCostFreeze(true);
			// SelectorItemCollection selector = new SelectorItemCollection();
			// selector.add("isCostFreeze");
			// ProjectPeriodStatusFactory.getLocalInstance(ctx).updatePartial(
			// prjInfo, selector);
		}
	}

	/**
	 * 反月结
	 */
	protected String _antiCostClose(Context ctx, List idList) throws BOSException, EASBizException {
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
		// 成功反月结的工程项目
		Set prjIds = new HashSet();

		for (int i = 0, size = idList.size(); i < size; i++) {
			String prjId = idList.get(i).toString();

			// 先检查
			// ProjectPeriodStatusUtil.checkLast(ctx, prjId, true);

			// 工程项目
			CurProjectInfo curprjInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(BOSUuid.read(prjId)), sic);

			// 工程项目的当前期间
			PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo shouldPeriod = PeriodUtils.getPrePeriodInfo(ctx, currentPeriod);

			BOSUuid companyID = curprjInfo.getFullOrgUnit().getId();
			// 工程项目所在财务组织的系统期间
			PeriodInfo companyPeriod = SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, new ObjectUuidPK(companyID));

			// 工程项目状态
			ProjectPeriodStatusInfo prjInfo = getPrjPeriodStatus(ctx, prjId);
			PeriodInfo startPeriod = prjInfo.getStartPeriod();

			String numberName = curprjInfo.getLongNumber().replace('!', '.') + " " + curprjInfo.getDisplayName() + " ";

			if (prjInfo.isIsEnd()) {
				if (strEnd == null)
					strEnd = "";
				// 工程项目已经关闭
				strEnd = strEnd + numberName;
				continue;
			} else if (currentPeriod.getId().toString().equals(startPeriod.getId().toString())) {
				if (strEqualStart == null)
					strEqualStart = "";
				// 在启动期间不能反月结
				strEqualStart = strEqualStart + numberName;
				continue;
			} else if (!prjInfo.isIsCostEnd() && prjInfo.isIsFinacialEnd()) {
				if (strNotEqual == null)
					strNotEqual = "";
				// 财务已经月结
				strNotEqual = strNotEqual + numberName;
				continue;
			} else if (companyPeriod.getNumber() >= currentPeriod.getNumber()) {
				if (strNotCompany == null)
					strNotCompany = "";
				// 财务组织的当前期间大于当前工程项目的期间
				strNotCompany = strNotCompany + numberName;
				continue;
			}

			// 存在期间在此期间或者之后的竣工结算帐务处理
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

			// 删除快照
			FDCCostRptFacadeFactory.getLocalInstance(ctx).reverseCostMonthSettled(prjId, shouldPeriod);
			// 检查通过之后，删除当前期间的月结表中的数据
			deleteSettleMonth(ctx, prjId, shouldPeriod.getId().toString());
			// 成功反月结的工程项目
			prjIds.add(prjId);
			// 反月结
			ProjectPeriodStatusUtil.last(ctx, prjId, true);

			if (strSuccess == null)
				strSuccess = "";
			strSuccess = strSuccess + numberName;

		}
		if (strSuccess != null) {
			strSuccess = "成本反月结成功项目：" + strSuccess + "\n";
		} else {
			strSuccess = "";
		}
		if (strEnd != null) {
			strEnd = "已经关闭，不能反月结的项目：" + strEnd + "\n";
		} else {
			strEnd = "";
		}
		if (strEqualStart != null) {
			strEqualStart = "当前成本期间等于启用期间，不能反月结的项目：" + strEqualStart + "\n";
		} else {
			strEqualStart = "";
		}
		if (strNotEqual != null) {
			strNotEqual = "财务成本期间与成本期间相同，不能成本反月结的项目：" + strNotEqual + "\n";
		} else {
			strNotEqual = "";
		}
		if (strNotCompany != null) {
			strNotCompany = "财务组织期间与成本期间不匹配，不能成本反月结的项目：" + strNotCompany + "\n";
		} else {
			strNotCompany = "";
		}
		if (strHasFIProduct != null) {
			strHasFIProduct = "有当前期间的竣工结算帐务处理，不能成本反月结的项目： " + strHasFIProduct + "\n";
		} else {
			strHasFIProduct = "";
		}

		if (strHasDynamicCost != null) {
			strHasDynamicCost = "当前期间已经更新动态成本分摊设置，不能成本反月结的项目： " + strHasDynamicCost + "\n";
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

	/**
	 * 删除月结数据表中的数据: 合同修订
	 * 
	 */
	protected void _delete(Context ctx, String contractId, String periodId) throws BOSException, EASBizException {
		ISettledMonthly settleMonth = SettledMonthlyFactory.getLocalInstance(ctx);
		FilterInfo filterPrj = new FilterInfo();
		if (contractId != null) {
			filterPrj.getFilterItems().add(new FilterItemInfo("contractID", contractId));
		}
		filterPrj.getFilterItems().add(new FilterItemInfo("settlePeriod.id", periodId));

		settleMonth.delete(filterPrj);
	}

	// 删除本月数据
	protected void deleteSettleMonth(Context ctx, String projectId, String periodId) throws BOSException, EASBizException {
		ISettledMonthly settleMonth = SettledMonthlyFactory.getLocalInstance(ctx);
		FilterInfo filterPrj = new FilterInfo();
		filterPrj.getFilterItems().add(new FilterItemInfo("curProjectID", projectId));

		filterPrj.getFilterItems().add(new FilterItemInfo("settlePeriod.id", periodId));
		filterPrj.getFilterItems().add(new FilterItemInfo("isCost", Boolean.TRUE));

		settleMonth.delete(filterPrj);
	}

	// 保存分摊方案到月结中间表
	protected void saveDyCostSplitData(Context ctx, List list) throws BOSException, EASBizException {
		if (list == null || list.size() < 0) {
			return;
		}
		Connection cn = null;
		try {
			cn = getConnection(ctx);
			// 用于批量保存
			IORMappingDAO iSettleMonth = new ORMappingDAO(new SettledMonthlyInfo().getBOSType(), ctx, cn);
			for (int i = 0, size = list.size(); i < size; i++) {
				String prjId = list.get(i).toString();
				// 该项目下dynamicId
				Set dynamicIds = new HashSet();
				// 获得改项目当前期间
				PeriodInfo currentPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.getSelector().add("*");
				filter.getFilterItems().add(new FilterItemInfo("account.curProject.id", prjId));
				view.setFilter(filter);
				DynamicCostCollection coll = DynamicCostFactory.getLocalInstance(ctx).getDynamicCostCollection(view);
				for (Iterator it = coll.iterator(); it.hasNext();) {
					DynamicCostInfo dynamicInfo = (DynamicCostInfo) it.next();
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(dynamicInfo.getBOSType().toString());
					settle.setObjectID(dynamicInfo.getId().toString());
					settle.setSettlePeriod(currentPeriod);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);
					dynamicIds.add(dynamicInfo.getId().toString());
				}
				// 产品分摊方案
				view = new EntityViewInfo();
				filter = new FilterInfo();
				view.getSelector().add("*");
				view.setFilter(filter);
				view.getFilter().getFilterItems().add(new FilterItemInfo("dynamicCostId", dynamicIds, CompareType.INCLUDE));
				// 取最新版本的分摊方案
				view.getFilter().getFilterItems().add(new FilterItemInfo("isLatestVer", new Integer(1)));
				DynamicCostProductSplitEntryCollection splits = DynamicCostProductSplitEntryFactory.getLocalInstance(ctx).getDynamicCostProductSplitEntryCollection(view);
				for (Iterator it = splits.iterator(); it.hasNext();) {
					DynamicCostProductSplitEntryInfo entry = (DynamicCostProductSplitEntryInfo) it.next();
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(entry.getBOSType().toString());
					settle.setObjectID(entry.getId().toString());
					settle.setSettlePeriod(currentPeriod);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);
				}
			}
			iSettleMonth.executeBatch();

		} finally {
			SQLUtils.cleanup(cn);
		}
	}

	// 反月结之后，更新分摊方案isLatestVer为false，更新本期isLaterstVer为true
	protected void updateSplitData(Context ctx, Set prjIds) throws BOSException, EASBizException {
		Map map = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("account.curProject.id", prjIds, CompareType.INCLUDE));
		view.setFilter(filter);
		DynamicCostCollection dynamicCostCollection = DynamicCostFactory.getLocalInstance(ctx).getDynamicCostCollection(view);
		Set dynamicIds = new HashSet();
		for (int i = 0; i < dynamicCostCollection.size(); i++) {
			DynamicCostInfo dynamic = dynamicCostCollection.get(i);
			dynamicIds.add(dynamic.getId().toString());
		}

	}

	/**
	 * 先抽成一个方法以后再优化 by sxhong 2009-06-01 17:30:24
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
		// 工程项目对应的成本中心
		/**
		 * R091119-224财务成本月结通不过: 提示合同拆分未拆,可是为什么成本月结能够通过
		 * 经与罗忠慧分析，由于跨项目拆分科目引起，科目所在工程项目无成本月结数据
		 * 处理：合同及变更(结算不用处理)拆分的组织取科目对应的工程项目的成本中心(如为被跨项目拆分引用，则有多个成本中心)
		 * by hpw 2009-12-11
		 */
//		String orgUnitId = FDCUtils.findCostCenterOrgId(ctx, prjId);
		String currentPeriodStr = Integer.toString(currentPeriod.getNumber());
		String nextPeriodStr = Integer.toString(nextPeriod.getNumber());
		String currentPeriodId = currentPeriod.getId().toString();
		// 记录月结数据
		Set set = new HashSet();
		Set splitSet = new HashSet();

		// 合同拆分
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("select distinct splitentry.fparentid from t_con_contractcostsplitentry splitentry ");
		builder.appendSql("where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=?) ");
		builder.appendSql("and splitentry.fparentid in (																	");
		builder
				.appendSql("	select split.fid from t_con_contractcostsplit split where (split.fstate<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid=?)) ");
		builder.appendSql("	and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber<=?) 		");
		builder.appendSql("	and split.forgunitid in (	select fcostcenterid from t_fdc_curproject where fid in ( ");
		builder.appendSql(" select fcurprojectid from t_con_contractcostsplit where fid in( select distinct splitentry.fparentid from t_con_contractcostsplitentry splitentry ");

		builder.appendSql(" where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=? )))) ");
		builder.appendSql("	)");
		builder.addParam(prjId);
		builder.addParam(currentPeriodId);
		builder.addParam(currentPeriodStr);
		builder.addParam(prjId);
//		builder.addParam(orgUnitId);
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			splitSet.add(rowSet.getString("fparentid"));
		}

		EntityViewInfo viewContractSplit = new EntityViewInfo();
		FilterInfo filterContractSplit = new FilterInfo();
		// 拆分中间表
		EntityViewInfo viewConCostSplit = new EntityViewInfo();
		FilterInfo filterConCostSplit = new FilterInfo();
		CostSplitCollection conCost = new CostSplitCollection();
		CostSplitInfo conCostInfo = new CostSplitInfo();
		if (splitSet.size() > 0) {

			filterContractSplit.getFilterItems().add(new FilterItemInfo("id", splitSet, CompareType.INCLUDE));
			viewContractSplit.setFilter(filterContractSplit);
			viewContractSplit.getSelector().add("id");
			viewContractSplit.getSelector().add("contractBill.id");
			ContractCostSplitCollection contract = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(viewContractSplit);
			ContractCostSplitInfo contractInfo = new ContractCostSplitInfo();
			for (Iterator it = contract.iterator(); it.hasNext();) {
				// SettledMonthlyInfo settle = new SettledMonthlyInfo();
				contractInfo = (ContractCostSplitInfo) (it.next());
				BOSObjectType bosType = contractInfo.getBOSType();
				String objectId = contractInfo.getId().toString();
				if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(bosType.toString());
					settle.setObjectID(objectId);
					settle.setSettlePeriod(currentPeriod);
					settle.setContractID(contractInfo.getContractBill().getId().toString());
					// settleMonth.addnew(settle);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);

					set.add(contractInfo.getId().toString());
				}
			}

			// 合同拆分中间表
			viewConCostSplit = new EntityViewInfo();
			filterConCostSplit = new FilterInfo();
			conCost = new CostSplitCollection();
			conCostInfo = new CostSplitInfo();
			if (set.size() > 0) {
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.CONTRACTSPLIT_VALUE, CompareType.EQUALS));
				// filterConCostSplit.getFilterItems().add(
				// new FilterItemInfo("period.number", Integer
				// .toString(currentPeriod.getNumber()),
				// CompareType.LESS_EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("costBillId");
				conCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				conCostInfo = new CostSplitInfo();
				for (Iterator it = conCost.iterator(); it.hasNext();) {
					// SettledMonthlyInfo settle = new
					// SettledMonthlyInfo();
					conCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conCostInfo.getBOSType();
					String objectId = conCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						SettledMonthlyInfo settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(bosType.toString());
						settle.setObjectID(objectId);
						settle.setSettlePeriod(currentPeriod);
						settle.setContractID(conCostInfo.getCostBillId().toString());
						// settleMonth.addnew(settle);
						settle.setIsCost(true);
						iSettleMonth.addNewBatch(settle);
					}
				}
			}
		}

		// 变更拆分
		set = new HashSet();
		splitSet = new HashSet();

		builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder
				.appendSql("select distinct splitentry.fparentid from t_con_conchangesplitentry splitentry where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=?) \n");
		builder.appendSql(" and splitentry.fparentid in (select split.fid from t_con_conchangesplit split where (split.fstate<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid=?)) \n");
		builder.appendSql(" and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber<=? ) \n");
		builder.appendSql("	and split.forgunitid in (	select fcostcenterid from t_fdc_curproject where fid in ( \n");
		builder.appendSql(" 	select fcurprojectid from t_con_conchangesplit where fid in( select distinct splitentry.fparentid from t_con_conchangesplitentry splitentry \n");
		builder.appendSql(" 	where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=? )))) \n");
		builder.appendSql("	)");
		builder.addParam(prjId);
		builder.addParam(currentPeriodId);
		builder.addParam(currentPeriodStr);
		builder.addParam(prjId);
//		builder.addParam(orgUnitId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			splitSet.add(rowSet.getString("fparentid"));
		}

		EntityViewInfo viewChangeSplit = new EntityViewInfo();
		FilterInfo filterChangeSplit = new FilterInfo();
		if (splitSet.size() > 0) {
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("contractChange.curProject.id", idList
			// .get(i)));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("state",
			// FDCBillStateEnum.INVALID_VALUE,
			// CompareType.NOTEQUALS));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("period.number", Integer
			// .toString(currentPeriod.getNumber()),
			// CompareType.LESS_EQUALS));
			filterChangeSplit.getFilterItems().add(new FilterItemInfo("id", splitSet, CompareType.INCLUDE));
			viewChangeSplit.setFilter(filterChangeSplit);
			viewChangeSplit.getSelector().add("id");
			viewChangeSplit.getSelector().add("contractChange.contractBill.id");
			ConChangeSplitCollection conChange = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(viewChangeSplit);
			ConChangeSplitInfo conChangeInfo = new ConChangeSplitInfo();
			for (Iterator it = conChange.iterator(); it.hasNext();) {
				SettledMonthlyInfo settle = new SettledMonthlyInfo();
				conChangeInfo = (ConChangeSplitInfo) (it.next());
				BOSObjectType bosType = conChangeInfo.getBOSType();
				String objectId = conChangeInfo.getId().toString();
				if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
					// settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(bosType.toString());
					settle.setObjectID(objectId);
					settle.setSettlePeriod(currentPeriod);
					settle.setContractID(conChangeInfo.getContractChange().getContractBill().getId().toString());
					// settleMonth.addnew(settle);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);
					set.add(conChangeInfo.getId().toString());
				}
			}

			// 变更拆分中间表
			if (set.size() > 0) {
				viewConCostSplit = new EntityViewInfo();
				filterConCostSplit = new FilterInfo();
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.CNTRCHANGESPLIT_VALUE, CompareType.EQUALS));
				// filterConCostSplit.getFilterItems().add(
				// new FilterItemInfo("period.number", Integer
				// .toString(currentPeriod.getNumber()),
				// CompareType.LESS_EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("splitBillId");

				conCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				conCostInfo = new CostSplitInfo();
				for (Iterator it = conCost.iterator(); it.hasNext();) {
					SettledMonthlyInfo settle = new SettledMonthlyInfo();
					conCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conCostInfo.getBOSType();
					String objectId = conCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						// settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(bosType.toString());
						settle.setObjectID(objectId);
						settle.setSettlePeriod(currentPeriod);

						SelectorItemCollection selectorContractID = new SelectorItemCollection();
						selectorContractID.add("id");
						selectorContractID.add("contractChange.contractBill.id");
						ConChangeSplitInfo contractIDInfo = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitInfo(new ObjectUuidPK(conCostInfo.getSplitBillId()), selectorContractID);

						settle.setContractID(contractIDInfo.getContractChange().getContractBill().getId().toString());
						// settleMonth.addnew(settle);
						settle.setIsCost(true);
						iSettleMonth.addNewBatch(settle);
					}
				}
			}
		}

		// 结算拆分
		set = new HashSet();
		splitSet = new HashSet();

		builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder
				.appendSql("select distinct splitentry.fparentid from t_con_settlementcostsplitentry splitentry where splitentry.fcostaccountid in (select account.fid from t_fdc_costaccount account where account.FCurProject=?) and splitentry.fparentid in (select split.fid from t_con_settlementcostsplit split where (split.fstate<>'9INVALID' or (split.FIslastVerThisPeriod=1 and split.fstate='9INVALID' and split.fperiodid=?)) and split.fperiodid in (select period.fid from t_bd_period period where period.fnumber<=?))");
		builder.addParam(prjId);
		builder.addParam(currentPeriodId);
		builder.addParam(currentPeriodStr);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			splitSet.add(rowSet.getString("fparentid"));
		}

		if (splitSet.size() > 0) {
			viewChangeSplit = new EntityViewInfo();
			filterChangeSplit = new FilterInfo();
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo(
			// "settlementBill.contractBill.curProject.id", idList
			// .get(i)));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("state",
			// FDCBillStateEnum.INVALID_VALUE,
			// CompareType.NOTEQUALS));
			// filterChangeSplit.getFilterItems().add(
			// new FilterItemInfo("period.number", Integer
			// .toString(currentPeriod.getNumber()),
			// CompareType.LESS_EQUALS));

			filterChangeSplit.getFilterItems().add(new FilterItemInfo("id", splitSet, CompareType.INCLUDE));

			viewChangeSplit.setFilter(filterChangeSplit);
			viewChangeSplit.getSelector().add("id");
			viewChangeSplit.getSelector().add("settlementBill.contractBill.id");
			SettlementCostSplitCollection settlement = SettlementCostSplitFactory.getLocalInstance(ctx).getSettlementCostSplitCollection(viewChangeSplit);
			SettlementCostSplitInfo settlementInfo = new SettlementCostSplitInfo();
			for (Iterator it = settlement.iterator(); it.hasNext();) {
				SettledMonthlyInfo settle = new SettledMonthlyInfo();

				settlementInfo = (SettlementCostSplitInfo) (it.next());
				BOSObjectType bosType = settlementInfo.getBOSType();
				String objectId = settlementInfo.getId().toString();
				if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
					// settle = new SettledMonthlyInfo();
					settle.setCurProjectID(prjId);
					settle.setEntityID(bosType.toString());
					settle.setObjectID(objectId);
					settle.setSettlePeriod(currentPeriod);
					settle.setContractID(settlementInfo.getSettlementBill().getContractBill().getId().toString());

					// settleMonth.addnew(settle);
					settle.setIsCost(true);
					iSettleMonth.addNewBatch(settle);

					set.add(settlementInfo.getId().toString());
				}
			}

			// 结算拆分中间表
			if (set.size() > 0) {
				viewConCostSplit = new EntityViewInfo();
				filterConCostSplit = new FilterInfo();
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("splitBillId", set, CompareType.INCLUDE));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("isInvalid", Boolean.TRUE, CompareType.NOTEQUALS));
				filterConCostSplit.getFilterItems().add(new FilterItemInfo("costBillType", CostSplitBillTypeEnum.SETTLEMENTSPLIT_VALUE, CompareType.EQUALS));
				// filterConCostSplit.getFilterItems().add(
				// new FilterItemInfo("period.number", Integer
				// .toString(currentPeriod.getNumber()),
				// CompareType.LESS_EQUALS));

				viewConCostSplit.setFilter(filterConCostSplit);
				viewConCostSplit.getSelector().add("id");
				viewConCostSplit.getSelector().add("splitBillId");

				conCost = CostSplitFactory.getLocalInstance(ctx).getCostSplitCollection(viewConCostSplit);
				conCostInfo = new CostSplitInfo();
				for (Iterator it = conCost.iterator(); it.hasNext();) {
					SettledMonthlyInfo settle = new SettledMonthlyInfo();

					conCostInfo = (CostSplitInfo) (it.next());
					BOSObjectType bosType = conCostInfo.getBOSType();
					String objectId = conCostInfo.getId().toString();
					if (!SettledMonthlyHelper.existObject(ctx, bosType, objectId, currentPeriodId)) {
						// settle = new SettledMonthlyInfo();
						settle.setCurProjectID(prjId);
						settle.setEntityID(conCostInfo.getBOSType().toString());
						settle.setObjectID(conCostInfo.getId().toString());
						settle.setSettlePeriod(currentPeriod);

						SelectorItemCollection selectorContractID = new SelectorItemCollection();
						selectorContractID.add("id");
						selectorContractID.add("settlementBill.contractBill.id");
						SettlementCostSplitInfo contractIDInfo = SettlementCostSplitFactory.getLocalInstance(ctx).getSettlementCostSplitInfo(new ObjectUuidPK(conCostInfo.getSplitBillId()),
								selectorContractID);

						settle.setContractID(contractIDInfo.getSettlementBill().getContractBill().getId().toString());
						// settleMonth.addnew(settle);
						settle.setIsCost(true);
						iSettleMonth.addNewBatch(settle);
					}
				}
			}
		}
		// 目标成本
		EntityViewInfo aimView = new EntityViewInfo();
		FilterInfo aimFilter = new FilterInfo();
		aimFilter.getFilterItems().add(new FilterItemInfo("orgOrProId", prjId));
		aimFilter.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
		// aimFilter.getFilterItems().add(
		// new FilterItemInfo("isLastVersion", Boolean.TRUE));
		aimView.setFilter(aimFilter);
		aimView.getSelector().add("id");
		aimView.getSorter().add(new SorterItemInfo("lastUpdateTime"));
		aimView.getSorter().get(0).setSortType(SortType.DESCEND);
		AimCostCollection aimColl = AimCostFactory.getLocalInstance(ctx).getAimCostCollection(aimView);
		if (aimColl.iterator().hasNext()) {
			AimCostInfo aimInfo = aimColl.get(0);
			SettledMonthlyInfo settle = new SettledMonthlyInfo();
			settle.setCurProjectID(prjId);
			settle.setEntityID(aimInfo.getBOSType().toString());
			settle.setObjectID(aimInfo.getId().toString());
			settle.setSettlePeriod(currentPeriod);
			// settleMonth.addnew(settle);
			settle.setIsCost(true);
			iSettleMonth.addNewBatch(settle);
		}

		// 工程项目指标
		aimView = new EntityViewInfo();
		aimFilter = new FilterInfo();
		aimFilter.getFilterItems().add(new FilterItemInfo("projOrOrgID", prjId));
		//只将最新版本的数据加入到期间里面,由于最新版本字段会被更新,反月结可能引起数据错误,取不到指标数据 by sxhong 2009-06-08 17:25:02
		aimFilter.appendFilterItem("isLatestVer", Boolean.TRUE);
		aimFilter.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
		// aimFilter.getFilterItems().add(
		// new FilterItemInfo("isLatestVer", Boolean.TRUE));
		aimView.setFilter(aimFilter);
		aimView.getSelector().add("id");
		aimView.getSorter().add(new SorterItemInfo("lastUpdateTime"));
		aimView.getSorter().get(0).setSortType(SortType.DESCEND);
		ProjectIndexDataCollection indexColl = ProjectIndexDataFactory.getLocalInstance(ctx).getProjectIndexDataCollection(aimView);
		for (Iterator it = indexColl.iterator(); it.hasNext();) {
			ProjectIndexDataInfo indexInfo = (ProjectIndexDataInfo) it.next();
			SettledMonthlyInfo settle = new SettledMonthlyInfo();
			settle.setCurProjectID(prjId);
			settle.setEntityID(indexInfo.getBOSType().toString());
			settle.setObjectID(indexInfo.getId().toString());
			settle.setSettlePeriod(currentPeriod);
			// settleMonth.addnew(settle);
			settle.setIsCost(true);
			iSettleMonth.addNewBatch(settle);
		}

		// 待发生调整
		aimView = new EntityViewInfo();
		aimFilter = new FilterInfo();
		aimFilter.getFilterItems().add(new FilterItemInfo("parent.account.curProject.id", prjId));
		aimFilter.getFilterItems().add(new FilterItemInfo("period.number", nextPeriodStr, CompareType.LESS));
		aimView.setFilter(aimFilter);
		aimView.getSelector().add("id");
		AdjustRecordEntryCollection adjustColl = AdjustRecordEntryFactory.getLocalInstance(ctx).getAdjustRecordEntryCollection(aimView);
		for (Iterator it = adjustColl.iterator(); it.hasNext();) {
			AdjustRecordEntryInfo adjustInfo = (AdjustRecordEntryInfo) it.next();
			SettledMonthlyInfo settle = new SettledMonthlyInfo();
			settle.setCurProjectID(prjId);
			settle.setEntityID(adjustInfo.getBOSType().toString());
			settle.setObjectID(adjustInfo.getId().toString());
			settle.setSettlePeriod(currentPeriod);
			// settleMonth.addnew(settle);
			settle.setIsCost(true);
			iSettleMonth.addNewBatch(settle);
		}

		// 最终批量提交数据库
		iSettleMonth.executeBatch();
	}
}