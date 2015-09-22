package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ICommonBOSType;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.ormapping.impl.ImplUtils;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.log.LogUtil;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractWithProgramInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.db.SQLUtils;

/**
 * 
 * 描述:无文本合同
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class ContractWithoutTextControllerBean extends
		AbstractContractWithoutTextControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractWithoutTextControllerBean");
	// 付款申请单编码
	private String payReqNumber = new String("");

	// 保存
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		IObjectPK pk = super._save(ctx, con);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SAVED);
		return pk;

	}
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		super._save(ctx, pk, model);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SAVED);
	}

	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		checkBillForSubmit(ctx, con);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		super._submit(ctx, pk, model);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SUBMITTED);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		FDCBillInfo con = ((FDCBillInfo) model);
		checkBillForSubmit(ctx, con);
		PayRequestBillInfo prbi = (PayRequestBillInfo) con.get("PayRequestBillInfo");
		con.setAmount(FDCHelper.multiply(con.getOriginalAmount(), prbi.getExchangeRate()));
		prbi.setAmount(con.getAmount());
		IObjectPK pk = super._submit(ctx, con);
		con.setId(BOSUuid.read(pk.toString()));
		createPayRequestBill(ctx, con, prbi, FDCBillStateEnum.SUBMITTED);
		return pk;
	}

	protected void trimName(FDCBillInfo fDCBillInfo) {
		super.trimName(fDCBillInfo);
	}

	// 审核
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		checkBillForAudit(ctx, billId, null);

		super._audit(ctx, billId);
		try {
			synUpdateBillByRelation(ctx,billId,true,null);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn(e);
		}
		// 同步标记付款申请单为审批状态
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("contractId", billId.toString()));
		evi.getSelector().add(new SelectorItemInfo("id"));
		evi.setFilter(filterInfo);

		IPayRequestBill iPayReq = PayRequestBillFactory.getLocalInstance(ctx);
		PayRequestBillCollection prbc = iPayReq
				.getPayRequestBillCollection(evi);

		if (prbc.size() > 0) {
			iPayReq.audit(prbc.get(0).getId());
		}
		ContractExecInfosFactory.getLocalInstance(ctx).updateContract(
				ContractExecInfosInfo.EXECINFO_AUDIT, billId.toString());
	}

	
	/**
	 * 1 .当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（无文本签约金额+变更金额），控制余额=控制金额-无文本签约金额 2
	 * .当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额 3
	 * .反写时点在合同单据审批通过时、变更审批单审批通过时、变更指令单结算时、合同结算审批通过时。 4.
	 * 合同修订审批后，规划余额=规划金额-（修订后的签约金额+变更金额），控制余额=控制金额-修订后的签约金额。
	 * 
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 * @throws SQLException
	 */
	private void synUpdateBillByRelation(Context ctx, BOSUuid billId, boolean flag, String relateAmt) throws EASBizException, BOSException,
			SQLException {
		String billIdStr = billId.toString();
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		IRowSet rowSet = null;
//		builder.appendSql(" select parent.fid conId from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
//		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
//		builder.appendSql(" where  entry.FRowkey='am' and ");
//		builder.appendParam(" con.fid", billIdStr);
//		rowSet = builder.executeQuery();
//		if (rowSet.next()) {
//			billIdStr = rowSet.getString("conId").toString();
//		}
		ContractWithoutTextInfo contractWithoutTextInfo = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(billIdStr), getSic());
		if (contractWithoutTextInfo.getProgrammingContract() == null)
			return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = contractWithoutTextInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		// 规划余额
		BigDecimal balanceAmt = pcInfo.getBalance();
		// 控制余额
		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// 无文本合同签约金额
		BigDecimal signAmount = contractWithoutTextInfo.getAmount();
		// if(mainSignAmount != null){
		// if(flag){
		// signAmount = FDCHelper.subtract(signAmount, mainSignAmount);
		// }else{
		// signAmount = FDCHelper.add(signAmount, mainSignAmount);
		// }
		// }
		// 框架合约无文本合同金额
		BigDecimal signUpAmount = pcInfo.getWithOutTextAmount();
		// 差额
		BigDecimal otherSignUpAmount = FDCHelper.ZERO;
		if (flag) {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, signAmount));
				pcInfo.setWithOutTextAmount(FDCHelper.add(signUpAmount, signAmount));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, relateAmt));
				pcInfo.setWithOutTextAmount(FDCHelper.add(signUpAmount, relateAmt));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, relateAmt));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, relateAmt), signUpAmount);
			}
			pcInfo.setBudgetAmount(FDCHelper.ZERO);
			pcInfo.setWithOutTextName(contractWithoutTextInfo.getName());
			pcInfo.setWithOutTextNumber(contractWithoutTextInfo.getNumber());
			pcInfo.setBillId(billIdStr);
		} else {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, signAmount));
				pcInfo.setWithOutTextAmount(FDCHelper.subtract(signUpAmount, signAmount));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, signAmount));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, relateAmt));
				pcInfo.setWithOutTextAmount(FDCHelper.subtract(signUpAmount, relateAmt));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, relateAmt));
				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, relateAmt), signUpAmount);
			}
			pcInfo.setBudgetAmount(pcInfo.getAmount());
			pcInfo.setWithOutTextName(contractWithoutTextInfo.getName());
			pcInfo.setWithOutTextNumber(contractWithoutTextInfo.getNumber());
			pcInfo.setBillId(null);
		}
		
		pcInfo.setWithOutTextName(contractWithoutTextInfo.getName());
		pcInfo.setWithOutTextNumber(contractWithoutTextInfo.getNumber());
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("withOutTextAmount");
		sict.add("withOutTextNumber");
		sict.add("withOutTextName");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("srcId");
		sict.add("budgetAmount");
		sict.add("billId");
//		sict.add("estimateAmount");
//		
//		//主要是补充合同，把合约规划的预估金额置为零，
//		BigDecimal  estimateAmount = pcInfo.getEstimateAmount();
//		pcInfo.setEstimateAmount(FDCHelper.ZERO);
//		pcInfo.setBalance(pcInfo.getBalance().add(estimateAmount));
		service.updatePartial(pcInfo, sict);
		//对应的预估变动单变为是否最新为false
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		ContractEstimateChangeBillCollection  coll = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
//		if(coll.size()>0){
//			ContractEstimateChangeBillInfo contInfo = coll.get(0);
//			contInfo.setIsLastest(false);
//			SelectorItemCollection sel = new SelectorItemCollection();
//			sel.add("isLastest");
//			ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
//		}
		
		//==========================
		// 更新其他的合约规划版本金额
//		String progId = pcInfo.getId().toString();
//		while (progId != null) {
//			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//			IRowSet rowSet = null;
//			String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//			if (nextVersionProgId != null) {
//				pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//				pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
//				pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherSignUpAmount));
//				pcInfo.setSignUpAmount(FDCHelper.add(pcInfo.getSignUpAmount(), otherSignUpAmount));
//				service.updatePartial(pcInfo, sict);
//				progId = pcInfo.getId().toString();
//			} else {
//				progId = null;
//			}
//		}
	}
	
	private String getNextVersionProg(Context ctx, String nextProgId, FDCSQLBuilder builder, IRowSet rowSet) throws BOSException, SQLException {
		String tempId = null;
		builder.clear();
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			tempId = rowSet.getString("fid");
		}
		return tempId;
	}
	// 反审核
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {

		if (billId == null)
			return;
		// 在反审批的时候判断有没有付款单,如果有不允许反审批
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBillId", billId.toString()));
		if (PaymentBillFactory.getLocalInstance(ctx).exists(filter)) {
			throw new ContractException(ContractException.HASPAYMENTBILL);
		}

		checkBillForUnAudit(ctx, billId, null);

		String sql = "update T_CON_PayRequestBill set fstate=?,fhasClosed=0 where fcontractid=?";
		String[] params = new String[] { FDCBillStateEnum.SUBMITTED_VALUE,
				billId.toString() };
		DbUtil.execute(ctx, sql, params);

		EntityViewInfo view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", billId.toString()));
		view.setFilter(filter);

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("contractId");
		selector.add("curProject.id");
		selector.add("orgUnit.id");

		PayRequestBillCollection col = PayRequestBillFactory.getLocalInstance(
				ctx).getPayRequestBillCollection(view);
		for (int i = 0; i < col.size(); i++) {

			PayRequestBillInfo payRequestBillInfo = col.get(i);
			String id = col.get(i).getId().toString();
			// 20050511 吴志敏提供预算接口
			HashMap param = FDCUtils.getDefaultFDCParam(ctx, payRequestBillInfo
					.getOrgUnit().getId().toString());
			// boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx,
			// billInfo.getId().toString());
			boolean isMbgCtrl = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_STARTMG).toString())
					.booleanValue();
			if (isMbgCtrl) {
				IBgControlFacade iBgCtrl = BgControlFacadeFactory
						.getLocalInstance(ctx);
				iBgCtrl.cancelRequestBudget(id);
			}
		}

		super._unAudit(ctx, billId);

		try {
			synUpdateBillByRelation(ctx,billId,false,null);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn(e);
		}
		
		// 自动删除本期月结数据
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("period.id");

		ContractWithoutTextInfo info = (ContractWithoutTextInfo) this.getValue(
				ctx, new ObjectUuidPK(billId), selectors);
		if (info.getPeriod() != null) {
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(
					info.getId().toString(),
					info.getPeriod().getId().toString());
		}
		ContractExecInfosFactory.getLocalInstance(ctx).updateContract(
				ContractExecInfosInfo.EXECINFO_UNAUDIT, billId.toString());
	}

	// 新增
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		IObjectPK objectPK = super._addnew(ctx, model);

		// 同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, true, objectPK
				.toString());

		/** 添加反写ContractBaseDataID的代码 -by neo */
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder
				.appendSql("update t_con_contractwithouttext set fcontractbasedataid ="
						+ "(select fid from t_CON_contractbasedata where fcontractid = t_con_contractwithouttext.fid) "
						+ "where");
		builder.appendParam("fid", objectPK.toString());
		builder.executeUpdate(ctx);
		/** 添加反写ContractBaseDataID的代码 -by neo */
		return objectPK;
	}

	// 修改
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		super._update(ctx, pk, model);

		// 同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, true, pk.toString());
	}

	protected void _updatePartial(Context ctx, IObjectValue model,
			SelectorItemCollection selector) throws BOSException,
			EASBizException {
		super._updatePartial(ctx, model, selector);

		// 同步到合同基本资料，用于合同做核算项目
		String id = ((FDCBillInfo) model).getId().toString();
		ContractBaseDataHelper.synToContractBaseData(ctx, true, id);

	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		try {//除掉合约规划是否关联了合同
			updateOldProg(ctx,pk);
		} catch (Exception e) {
			logger.error(e);
		}
		_delete(ctx, new IObjectPK[] { pk });
	}
	
	/**
	 * 找出所关联的框架合约的记录数(无文本已经废除，不再查找)
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByProg(Context ctx, String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select count(1) count from T_INV_InviteProject ");
		buildSQL.appendSql(" where FProgrammingContractId = '" + proContId + "' ");
		buildSQL.appendSql(" union ");
		buildSQL.appendSql(" select count(1) count from T_CON_ContractWithoutText ");
		buildSQL.appendSql(" where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			while (iRowSet.next()) {
				count += iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (SQLException e) {
			logger.error(e);
		}
		return count;
	}

	/**
	 * 检查无文本合同是否关联框架合约
	 * 
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre(Context ctx, IObjectPK pk) throws Exception {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		builder.clear();
		builder.appendSql("select fprogrammingContract from T_CON_ContractWithoutText where 1=1 and ");
		builder.appendParam("fid", pk.toString());
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				return rowSet.getString("fprogrammingContract").toString();
			}
		}
		return null;
	}
	
	private int isCitingByContractWithoutText(Context ctx,String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 更新老框架合约是否被引用
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	private void updateOldProg(Context ctx, IObjectPK pk) throws Exception {
		String checkReaPre = checkReaPre(ctx, pk);
		if(checkReaPre!=null){
			int linkContractWithoutText = isCitingByContractWithoutText(ctx,checkReaPre);// 本合约关联无文本合同招立项数
			if (linkContractWithoutText <= 1 ) {
				updateProgrammingContract(ctx, checkReaPre, 0);
			}
		}
	}
	
	private String isUpdateNextProgState(Context ctx, String progId) throws Exception {
//		String flag = null;
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql(" select fid from t_con_programmingContract where ");
//		builder.appendParam("fSrcId", progId);
//		IRowSet rowSet = builder.executeQuery();
//		while (rowSet.next()) {
//			flag = rowSet.getString("fid").toString();
//		}
		return null;
	}
	
	private boolean preVersionProg(Context ctx, String progId) throws BOSException, SQLException {
		boolean isCityingProg = false;
		int tempIsCiting = 0;
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql(" select t1.FIsCiting isCiting from t_con_programmingContract t1 where t1.fid = (");
		buildSQL.appendSql(" select t2.FSrcId from t_con_programmingContract t2 where t2.fid = '" + progId + "')");
		IRowSet rowSet = buildSQL.executeQuery();
		while (rowSet.next()) {
			tempIsCiting = rowSet.getInt("isCiting");
		}
		if (tempIsCiting > 0) {
			isCityingProg = true;
		}
		return isCityingProg;
	}
	
	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(Context ctx, String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsWTCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		super._cancel(ctx, pk);

		// 同步删除合同基本资料
		String sql = "delete t_con_contractbasedata where fcontractid = ?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });

		sql = "delete from T_CON_PayRequestBillEntry where fparentid in (select fid from T_CON_PayRequestBill where fcontractid =?) "; // TODO
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });
		sql = "delete from T_CON_PayRequestBill where fcontractid =?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });
		sql = "delete from T_CON_ContractBaseData where fcontractid =?";
		DbUtil.execute(ctx, sql, new Object[] { pk.toString() });

	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		Set set = new HashSet();
		// String str="'";
		for (int i = 0; i < arrayPK.length; i++) {
			set.add(arrayPK[i]);
			// str+=arrayPK[i].toString()+"','";
		}
		// str=str.substring(0,str.length()-2);
		FilterInfo filter = new FilterInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", set, CompareType.INCLUDE));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.HASAUDIT);
		}

		// 在反审批的时候判断有没有付款单,如果有不允许反审批
		// filter.getFilterItems().add(new
		// FilterItemInfo("contractBillId",set,CompareType.INCLUDE));
		// if(PaymentBillFactory.getLocalInstance(ctx).exists(filter)){
		// throw new ContractException(ContractException.HASPAYMENTBILL);
		// }
		// SQL操作，不记录日志
		// String sql=
		// "delete from T_CON_PayRequestBillEntry where fparentid in (select fid from T_CON_PayRequestBill where fcontractid in ("
		// +str+"));"; //TODO
		// DbUtil.execute(ctx, sql);
		//sql="delete from T_CON_PayRequestBill where fcontractid in ("+str+");"
		// ;
		// DbUtil.execute(ctx, sql);
		// IObjectPK payrequestPK =null;
		// PayRequestBillFactory.getLocalInstance(ctx).delete(payrequestPK);

		//
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent.contractid", set,
						CompareType.INCLUDE));
		PayRequestBillEntryFactory.getLocalInstance(ctx).delete(filter);
		// 单据，需要记录日志
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractid", set, CompareType.INCLUDE));
		view.setFilter(filter);
		PayRequestBillCollection payRequestBills = PayRequestBillFactory
				.getLocalInstance(ctx).getPayRequestBillCollection(view);
		IObjectPK[] payPks = new IObjectPK[payRequestBills.size()];
		int i = 0;
		String paysLogInfo = "";
		for (Iterator it = payRequestBills.iterator(); it.hasNext();) {
			PayRequestBillInfo pay = (PayRequestBillInfo) it.next();
			payPks[i++] = new ObjectUuidPK(pay.getId());
			paysLogInfo += paysLogInfo.equals("") ? pay.getNumber() : ","
					+ pay.getNumber();
		}
		// begin log
		IObjectPK logPk = LogUtil.beginLog(ctx, "fin_payRequst_delete",
				(new PayRequestBillInfo()).getBOSType(), null, paysLogInfo,
				"fin_payRequst_delete");
		// do delete
		PayRequestBillFactory.getLocalInstance(ctx).deleteForContWithoutText(
				payPks);
		// after log
		LogUtil.afterLog(ctx, logPk);
		// delete payrequestbill end

		/*
		 * //不能直接删除，如果被作为核算项目被横表引用的话是不允许删除的，掉ORMap的方法会有这类的检查 String
		 * sql="delete from T_CON_ContractBaseData where fcontractid in ("
		 * +str+");"; DbUtil.execute(ctx, sql);
		 */
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractId", set, CompareType.INCLUDE));
		ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		
		super._delete(ctx, arrayPK);
	}

	private void createPayRequestBill(Context ctx, FDCBillInfo con,
			PayRequestBillInfo prbi, FDCBillStateEnum state)
			throws BOSException, EASBizException {

		// 删除之前的多余付款申请单
		String sql = "delete from T_CON_PayRequestBill where FContractId = ? ";
		Object[] params = new Object[] { con.getId().toString() };
		DbUtil.execute(ctx, sql, params);
		checkNumber(ctx, con.getNumber());
		// 付款申请单编码重复性校验
		String number = payReqNumber;

		// UI传入
		// 付款申请单
		PayRequestBillInfo prbiNew = new PayRequestBillInfo();

		// 付款类型
		prbiNew.setPaymentType(prbi.getPaymentType());
		// 用款部门
		prbiNew.setUseDepartment(prbi.getUseDepartment());
		// 付款申请单编码
		// prbiNew.setNumber(prbi.getNumber());
		// if(prbi.getId() == null){
		// 2009-2-5 始终使用checkNumber方法计算出的编码生成付款申请单
		prbiNew.setNumber(number);
		// }else{
		// prbiNew.setNumber(prbi.getNumber());
		// }
		// 付款日期
		prbiNew.setPayDate(prbi.getPayDate());
		// 收款单位
		prbiNew.setSupplier(prbi.getSupplier());
		// 实际收款单位
		prbiNew.setRealSupplier(prbi.getRealSupplier());
		prbiNew.setPerson(prbi.getPerson());
		// 结算方式
		prbiNew.setSettlementType(prbi.getSettlementType());
		// 收款银行
		prbiNew.setRecBank(prbi.getRecBank());
		// 收款帐号
		prbiNew.setRecAccount(prbi.getRecAccount());
		// 紧急程度
		prbiNew.setUrgentDegree(prbi.getUrgentDegree());
		// 币别
		prbiNew.setCurrency(prbi.getCurrency());
		
		prbiNew.setLocalCurrency(prbi.getLocalCurrency());
		// 最新造价
		prbiNew.setLatestPrice(prbi.getLatestPrice());
		// 汇率
		prbiNew.setExchangeRate(prbi.getExchangeRate());
		// 大写金额
		prbiNew.setCapitalAmount(prbi.getCapitalAmount());
		// 付款比例
		prbiNew.setPaymentProportion(prbi.getPaymentProportion());
		// 已完工工程量金额
		prbiNew.setCompletePrjAmt(prbi.getCompletePrjAmt());
		// 本位币金额
		prbiNew.setAmount(prbi.getAmount());
		// 原币金额
		prbiNew.setOriginalAmount(prbi.getOriginalAmount());
		// 款项说明
		prbiNew.setUsage(prbi.getUsage());
		// 款项说明
		prbiNew.setMoneyDesc(prbi.getMoneyDesc());
		// 备注
		prbiNew.setDescription(prbi.getDescription());

		// 附件
		prbiNew.setAttachment(prbi.getAttachment());
		// 工程项目
		prbiNew.setCurProject(prbi.getCurProject());
		// 关闭状态
		prbiNew.setHasClosed(false);
		// 申请日期
		prbiNew.setBookedDate(con.getBookedDate());
		// 期间
		prbiNew.setPeriod(con.getPeriod());

		// 此处获取的
		// 合同号
		prbiNew.setContractNo(con.getNumber());
		// 合同ID
		prbiNew.setContractId(con.getId().toString());
		prbiNew.setSource(con.getBOSType().toString());
		// 合同名称
		prbiNew.setContractName(con.getName());
		// 合同造价
		prbiNew.setContractPrice(con.getAmount());
		// 状态
		prbiNew.setState(state);
		// 付款比例
		// prbiNew.setPaymentProportion(new BigDecimal(100));
		prbiNew.setPaymentProportion(FDCHelper.ONE_HUNDRED);
		// 已完工工程量金额
		prbiNew.setCompletePrjAmt(con.getAmount());// 已完工工程量金额
		// 成本中心
		prbiNew.setOrgUnit(con.getOrgUnit());
		// 管理单元
		prbiNew.setCU(con.getCU());
		
	
		// 无需付款的申请单 update by liang_ren at 2010-5-10
	
		prbiNew.setIsPay(!((ContractWithoutTextInfo) con).isIsNeedPaid());
			
		// 发票号
		prbiNew.setInvoiceNumber(prbi.getInvoiceNumber());
		// 发票日期
		prbiNew.setInvoiceDate(prbi.getInvoiceDate());
		// 发票金额
		prbiNew.setInvoiceAmt(prbi.getInvoiceAmt());
		// 累计发票金额
		prbiNew.setAllInvoiceAmt(prbi.getInvoiceAmt());
		
		prbiNew.setIsBgControl(prbi.isIsBgControl());
		prbiNew.setApplier(prbi.getApplier());
		prbiNew.setApplierCompany(prbi.getApplierCompany());
		prbiNew.setApplierOrgUnit(prbi.getApplierOrgUnit());
		prbiNew.setCostedCompany(prbi.getCostedCompany());
		prbiNew.setCostedDept(prbi.getCostedDept());
		prbiNew.setPayBillType(prbi.getPayBillType());
		prbiNew.setPayContentType(prbi.getPayContentType());
		prbiNew.setCreator(prbi.getCreator());
		prbiNew.setLastUpdateUser(prbi.getLastUpdateUser());
		for(int i=0;i<prbi.getBgEntry().size();i++){
			prbi.getBgEntry().get(i).setId(null);
			prbiNew.getBgEntry().add(prbi.getBgEntry().get(i));
		}
		PayRequestBillFactory.getLocalInstance(ctx).addnew(prbiNew);

	}

	// 单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap)
			throws BOSException, EASBizException {

		paramMap.put("isCost", Boolean.FALSE);
		Map initMap = super._fetchInitData(ctx, paramMap);

		return initMap;
	}

	private SelectorItemCollection getSic() {
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("period.id"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("conChargeType.id"));
	     // wangxin 
		sic.add(new SelectorItemInfo("hasSettled"));
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("*"));
		return sic;
	}

	// 提交时校验单据期间不能在工程项目的当前期间之前
	private void checkBillForSubmit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		// 不能落于当前成本期间之前
		ContractWithoutTextInfo contractBill = (ContractWithoutTextInfo) model;

		String comId = null;
		if (contractBill.getCurProject().getFullOrgUnit() == null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(
							new ObjectUuidPK(contractBill.getCurProject()
									.getId().toString()), sic);

			comId = curProject.getFullOrgUnit().getId().toString();
		} else {
			comId = contractBill.getCurProject().getFullOrgUnit().getId()
					.toString();
		}

		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,
					contractBill.getCurProject().getId().toString(), false);
			if (contractBill.getPeriod().getBeginDate().before(
					bookedPeriod.getBeginDate())) {
				// 单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
//		IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
//		BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill(contractBill));
//		Map bgItemMap=new HashMap();
//		for (int i = 0; i < contractBill.getBgEntry().size(); i++) {
//			BgItemInfo bgItem=contractBill.getBgEntry().get(i).getBgItem();
//			for (int j = 0; j < coll.size(); j++) {
//				if (bgItem.getNumber().equals(coll.get(j).getItemCombinNumber())) {
//					if(BgCtrlTypeEnum.NoCtrl.equals(coll.get(j).getCtrlType())){
//						break;
//					}
//					String isFromWorkflow=(String) contractBill.get("isFromWorkflow");
//					String approveIsPass=(String) contractBill.get("approveIsPass");
//					if (isFromWorkflow != null && isFromWorkflow.equals("true")&&approveIsPass!=null&&approveIsPass.equals("false")) {
//						break;
//					}
//					BigDecimal balanceAmount=FDCHelper.ZERO;
//					BigDecimal requestAmount=contractBill.getBgEntry().get(i).getRequestAmount();
//					if(coll.get(j).getBalance() != null){
//						balanceAmount=coll.get(j).getBalance();
//					}
//					if(bgItemMap.containsKey(bgItem.getNumber())){
//						BigDecimal sumAmount=(BigDecimal) bgItemMap.get(bgItem.getNumber());
//						balanceAmount=balanceAmount.subtract(sumAmount);
//						bgItemMap.put(bgItem.getNumber(), sumAmount.add(requestAmount));
//					}else{
//						bgItemMap.put(bgItem.getNumber(), requestAmount);
//					}
//					BigDecimal balance=FDCHelper.ZERO;
//					try {
//						balance = balanceAmount.subtract(getAccActOnLoadBgAmount(ctx,bgItem.getNumber(),contractBill));
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					if(requestAmount.compareTo(balance)>0){
//						throw new EASBizException(new NumericExceptionSubItem("000",bgItem.getName()+"超过预算余额！"));
//					}
//				}
//			}
//		}
	}
	protected BigDecimal getAccActOnLoadBgAmount(Context ctx,String bgItemNumber,ContractWithoutTextInfo contractBill) throws BOSException, SQLException{
		if(contractBill.getCostedDept()==null) return FDCHelper.ZERO;
		
		String bgComItem="";
		Set bgComItemSet=new HashSet();
		bgComItemSet.add(bgItemNumber);
		FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select distinct t2.fformula fformula from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno where t1.FOrgUnitId ='"+contractBill.getCostedDept().getId().toString()+"'");
		_builder.appendSql(" and t1.fgroupno!='-1' and t2.fgroupno!='-1' and t1.fformula like '%"+bgItemNumber+"%' and t2.fformula not like '%"+bgItemNumber+"%'");
		IRowSet rowSet = _builder.executeQuery();
		
		while(rowSet.next()){
			if(rowSet.getString("fformula")!=null){
				String formula=rowSet.getString("fformula");
				bgComItemSet.add(formula.substring(9, formula.indexOf(",")-1));
			}
		}
		Object[] bgObject = bgComItemSet.toArray();
    	for(int i=0;i<bgObject.length;i++){
        	if(i==0){
        		bgComItem=bgComItem+"'"+bgObject[i].toString()+"'";
        	}else{
        		bgComItem=bgComItem+",'"+bgObject[i].toString()+"'";
        	}
        }
    	
		_builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid ");
		_builder.appendSql(" where bill.fisBgControl=1 and bill.FCostedDeptId='"+contractBill.getCostedDept().getId().toString()+"'  and bgItem.fnumber in("+bgComItem+")");
		_builder.appendSql(" and bill.fstate in('3AUDITTING','4AUDITTED') ");
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null");
		
		if(contractBill.getId()!=null){
			_builder.appendSql(" and bill.fcontractid!='"+contractBill.getId().toString()+"'");
		}
		rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
    protected PaymentBillInfo createTempPaymentBill(ContractWithoutTextInfo contractBill) throws BOSException{
		PaymentBillInfo pay=new PaymentBillInfo();
		for(int i=0;i<contractBill.getBgEntry().size();i++){
			PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
			BigDecimal requestAmount=contractBill.getBgEntry().get(i).getRequestAmount();
			
			entry.setAmount(requestAmount);
			entry.setLocalAmt(requestAmount);
            entry.setActualAmt(requestAmount);
            entry.setActualLocAmt(requestAmount);
            entry.setCurrency(contractBill.getCurrency());
            entry.setExpenseType(contractBill.getBgEntry().get(i).getExpenseType());
            entry.setCostCenter(contractBill.getCostedDept());
            pay.getEntries().add(entry);
		}
		pay.setCompany(contractBill.getCostedCompany());
		pay.setCostCenter(contractBill.getCostedDept());
		pay.setPayDate(FDCCommonServerHelper.getServerTimeStamp());
		pay.setCurrency(contractBill.getCurrency());
		
		return pay;
	}
	// 审核校验
	private void checkBillForAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {

		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;

		if (model == null) {
			model = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(
					billId.toString()), getSic());
		}
		// ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new
		// ObjectUuidPK(billId.toString()),sic);
		// 检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// 是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			if (!ProjectPeriodStatusUtil._isClosed(ctx, curProject)) {
				throw new ProjectPeriodStatusException(
						ProjectPeriodStatusException.ISNOT_INIT,
						new Object[] { model.getCurProject().getDisplayName() });
			}
			// //成本已经月结
			// PeriodInfo bookedPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,true);
			//if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate(
			// ))){
			// throw new ContractException(ContractException.AUD_AFTERPERIOD,new
			// Object[]{model.getNumber()});
			// }

			// 财务还没有月结，不能审核单据{0}
			// PeriodInfo finPeriod =
			// FDCUtils.getCurrentPeriod(ctx,curProject,false);
			//if(model.getPeriod().getBeginDate().after(finPeriod.getEndDate()))
			// {
			// throw new ContractException(ContractException.AUD_FINNOTCLOSE,new
			// Object[]{model.getNumber()});
			// }
		}
		// 根据参数是否显示合同费用项目
		// 单据审批时预算控制
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeAuditBudgetCtrl(ctx, model);
		}
	}

	// 单据审批时 预算控制
	private void invokeAuditBudgetCtrl(Context ctx,
			ContractWithoutTextInfo conBill) throws BOSException,
			EASBizException {
		boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, conBill.getId()
				.toString());
		if (!useWorkflow) {
			IBgControlFacade bgControlFacade = BgControlFacadeFactory
					.getLocalInstance(ctx);
			IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory
					.getLocalInstance(ctx);

			boolean isPass = false;
			isPass = iBgBudget
					.getAllowAccessNoWF(FDCConstants.ContractWithoutText);
			if (isPass) {
				// 5.1.1暂时屏蔽
				bgControlFacade.bgAuditAllowAccess(conBill.getId(),
						FDCConstants.ContractWithoutText, null);
			} else {
				isPass = bgControlFacade.bgAudit(conBill.getId().toString(),
						FDCConstants.ContractWithoutText, null);
			}
			// 根据isPass判断是否抛异常
			if (!isPass) {
				throw new ContractException(ContractException.BEFOREBGBAL);
			}
		}
	}

	// 单据反审批时 预算控制
	private void invokeUnAuditBudgetCtrl(Context ctx,
			ContractWithoutTextInfo conBill) throws BOSException,
			EASBizException {

		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
		iBgCtrl.cancelRequestBudget(conBill.getId().toString());
	}

	// 审核校验
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,
			FDCBillInfo billInfo) throws BOSException, EASBizException {
		ContractWithoutTextInfo model = (ContractWithoutTextInfo) billInfo;
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("conChargeType.id"));

		if (model == null) {
			model = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(
					billId.toString()), getSic());
		}
		// 检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId()
				.toString();

		// 是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation(ctx, comId);
		if (isInCore) {
			String curProject = model.getCurProject().getId().toString();

			// 单据期间在工程项目当前期间之前，不能反审核
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,
					curProject, false);
			if (model.getPeriod().getBeginDate().before(
					bookedPeriod.getBeginDate())) {
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}

			// if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
			// throw new
			// ProjectPeriodStatusException(ProjectPeriodStatusException
			// .CLOPRO_HASEND,new
			// Object[]{model.getCurProject().getDisplayName()});
			// }
		}
		// 根据参数是否显示合同费用项目
		// 单据审批时预算控制
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx, null,
				FDCConstants.FDC_PARAM_CHARGETYPE);
		if (isShowCharge && model.getConChargeType() != null) {
			invokeUnAuditBudgetCtrl(ctx, model);
		}
	}

	// 是否成本
	protected boolean isCost() {
		return false;
	}

	// 获取合同类型编码
	protected String _getContractTypeNumber(Context ctx, IObjectPK id)
			throws BOSException, EASBizException {
		//
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longNumber"));
		ContractWithoutTextInfo contractBill = (ContractWithoutTextInfo) this
				.getValue(ctx, id, sic);

		if (contractBill.getContractType() != null) {
			return contractBill.getContractType().getLongNumber().replace('!',
					'.');
		} else {
			return "*";
		}
	}

	// 如果编码重复重新取编码
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info)
			throws BOSException, CodingRuleException, EASBizException {
		handleIntermitNumberForReset(ctx, info);
	}

	// 检查编码是否相同
	private void checkNumber(Context ctx, String number) {
		if (number==null || number.equals("")) {
			return;
		}
		payReqNumber = number;
		// 付款申请单编码重复性校验
		String sql = "select fid from T_CON_PayRequestBill where FNumber = ? ";
		Object[] params = new Object[] { number };

		RowSet rowset;
		try {
			rowset = DbUtil.executeQuery(ctx, sql, params);
			try {
				if (rowset.next()) {
					payReqNumber = payReqNumber + "(无文本)";
					checkNumber(ctx, payReqNumber);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}

	protected String _getUseDepartment(Context ctx, String id)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("contractId",id.toString()));
		view.setSelector(new SelectorItemCollection());
		view.getSelector().add("useDepartment.id");
		view.getSelector().add("useDepartment.name");
		view.getSelector().add("useDepartment.number");
		view.getSelector().add("useDepartment.longNumber");
		PayRequestBillCollection payReqBillCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		if(payReqBillCol.size() > 0){
			PayRequestBillInfo billInfo = payReqBillCol.get(0);
			return billInfo.getUseDepartment().getLongNumber().replace('!', '.');
		}
		return "*";
	}

	protected String _getPaymentType(Context ctx, BOSUuid contractId)
			throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("contractId",contractId.toString()));
		view.setSelector(new SelectorItemCollection());
		view.getSelector().add("paymentType.id");
		view.getSelector().add("paymentType.name");
		view.getSelector().add("paymentType.number");
		PayRequestBillCollection payReqBillCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		if(payReqBillCol.size() > 0){
			PayRequestBillInfo billInfo = payReqBillCol.get(0);
			return billInfo.getPaymentType().getNumber();
		}
		return "*";
	}

	protected void _synUpdateProgramming(Context ctx, String billId,IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("amount");
		ContractWithoutTextInfo contractBillInfo = this.getContractWithoutTextInfo(ctx, new ObjectUuidPK(billId), sel);
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcBalance=pcInfo.getBalance();
		BigDecimal pcSignUpAmount=pcInfo.getSignUpAmount();
		BigDecimal amount = contractBillInfo.getAmount();
		
		pcInfo.setBalance(FDCHelper.subtract(pcBalance, amount));
		pcInfo.setSignUpAmount(FDCHelper.add(pcSignUpAmount, amount));
		pcInfo.setIsWTCiting(true);
		
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("signUpAmount");
		sict.add("isWTCiting");
		service.updatePartial(pcInfo, sict);
	}
	protected void _synReUpdateProgramming(Context ctx, String billId, IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcAmount=pcInfo.getAmount();
		
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("amount");
		sel.add("head.contractChangeBill.id");
		sel.add("head.contractChangeSettleBill.conChangeBill.id");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setSelector(sel);
		ContractWithoutTextCollection wtcol=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<wtcol.size();i++){
			amount =FDCHelper.add(amount,wtcol.get(i).getAmount()) ;
		}
		if(wtcol.size()==0){
			pcInfo.setIsWTCiting(false);
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		view.setSelector(sel);
		ContractBillCollection col=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view);
		for(int i=0;i<col.size();i++){
			amount =FDCHelper.add(amount,col.get(i).getAmount()) ;
			pcInfo.setIsCiting(true);
		}
		if(col.size()==0){
			BigDecimal signUpAmount=FDCHelper.ZERO;
    		BigDecimal changeAmount=FDCHelper.ZERO;
    		BigDecimal settleAmount=FDCHelper.ZERO;
    		
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("head.contractSettleBill.id",null,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
			view.setFilter(filter);
			view.setSelector(sel);
			ContractPCSplitBillEntryCollection spcol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
			if(spcol.size()==0){
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.contractBill.id",null,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
				view.setFilter(filter);
				view.setSelector(sel);
				ContractPCSplitBillEntryCollection signUpCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
				for(int j=0;j<signUpCol.size();j++){
					signUpAmount =FDCHelper.add(signUpAmount,signUpCol.get(j).getAmount()) ;
					pcInfo.setIsCiting(true);
				}
				
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.contractChangeBill.id",null,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
				view.setFilter(filter);
				view.setSelector(sel);
				ContractPCSplitBillEntryCollection changeCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
				for(int j=0;j<changeCol.size();j++){
					BigDecimal csAmount=FDCHelper.ZERO;
					EntityViewInfo csview = new EntityViewInfo();
					FilterInfo csfilter = new FilterInfo();
					csfilter.getFilterItems().add(new FilterItemInfo("head.contractChangeSettleBill.conChangeBill.id",changeCol.get(j).getHead().getContractChangeBill().getId().toString()));
					csfilter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
					csview.setFilter(csfilter);
					csview.setSelector(sel);
					ContractPCSplitBillEntryCollection csCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(csview);
					for(int k=0;k<csCol.size();k++){
						csAmount =FDCHelper.add(csAmount,csCol.get(k).getAmount()) ;
					}
					if(csAmount.compareTo(FDCHelper.ZERO)>0){
						changeAmount =FDCHelper.add(changeAmount,csAmount) ;
					}else{
						changeAmount =FDCHelper.add(changeAmount,changeCol.get(j).getAmount()) ;
					}
				}
				
				view = new EntityViewInfo();
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.contractChangeSettleBill.id",null,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
				view.setFilter(filter);
				view.setSelector(sel);
				ContractPCSplitBillEntryCollection csCol=ContractPCSplitBillEntryFactory.getLocalInstance(ctx).getContractPCSplitBillEntryCollection(view);
				for(int j=0;j<csCol.size();j++){
					FilterInfo cbfilter = new FilterInfo();
					cbfilter.getFilterItems().add(new FilterItemInfo("head.contractChangeBill.id",changeCol.get(j).getHead().getContractChangeSettleBill().getConChangeBill().getId().toString()));
					cbfilter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
					if(!ContractPCSplitBillEntryFactory.getLocalInstance(ctx).exists(cbfilter)){
						changeAmount =FDCHelper.add(changeAmount,changeCol.get(j).getAmount()) ;
					}
				}
			}else{
				for(int j=0;j<spcol.size();j++){
					settleAmount =FDCHelper.add(settleAmount,spcol.get(j).getAmount()) ;
				}
			}
			if(settleAmount.compareTo(FDCHelper.ZERO)>0){
				pcInfo.setBalance(FDCHelper.subtract(pcAmount, FDCHelper.add(amount, settleAmount)));
			}else{
				pcInfo.setBalance(FDCHelper.subtract(pcAmount, FDCHelper.add(amount, FDCHelper.add(signUpAmount, changeAmount))));
			}
			pcInfo.setSignUpAmount(FDCHelper.add(amount, signUpAmount));
		}else{
			pcInfo.setBalance(FDCHelper.subtract(pcAmount, amount));
			pcInfo.setSignUpAmount(amount);
		}
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("signUpAmount");
		sict.add("isWTCiting");
		sict.add("isCiting");
		service.updatePartial(pcInfo, sict);
	}
	protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._setSubmitStatus(ctx, billId);
		PayRequestBillCollection pay=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection("select id from where contractId='"+billId+"'");
		if(pay.size()>0){
			PayRequestBillFactory.getLocalInstance(ctx).setSubmitStatus(pay.get(0).getId());
		}
	}
	protected void _setAudittingStatus(Context ctx, BOSUuid billId)throws BOSException, EASBizException {
		super._setAudittingStatus(ctx,billId);
		PayRequestBillCollection pay=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection("select id from where contractId='"+billId+"'");
		if(pay.size()>0){
			PayRequestBillFactory.getLocalInstance(ctx).setAudittingStatus(pay.get(0).getId());
		}
	}
	protected IObjectValue _getNoPValue(Context ctx, IObjectPK pk,SelectorItemCollection sel) throws BOSException {
		Connection cn = null;
        IObjectValue iobjectvalue;
        cn = getConnection(ctx);
        EntityViewInfo view = new EntityViewInfo();
        IMetaDataLoader loader = MetaDataLoaderFactory.getLocalMetaDataLoader(ctx);
        EntityObjectInfo bo = null;
        if((this instanceof ICommonBOSType) && ((ICommonBOSType)this).getPK() != null)
            bo = loader.getEntity(((ICommonBOSType)this).getPK());
        else
            bo = loader.getEntity(getBOSType());
        FilterInfo filterData = ImplUtils.createFilter(bo, pk);
        view.setFilter(filterData);
        if(sel!=null){
        	view.setSelector(sel);
        }else if(bo.getDefaultView() != null)
        {
            SelectorItemCollection selector = bo.getDefaultView().getSelector();
            view.setSelector(selector);
        }
        iobjectvalue = getDAO(ctx, cn).getValue(view);
        SQLUtils.cleanup(cn);
        return iobjectvalue;
	}
}