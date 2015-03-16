package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryTempInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitTempFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitTempInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.SettNoCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadException;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * 描述:结算单
 * 
 * @author liupd date:2006-10-13
 *         <p>
 * @version EAS5.1.3
 */
public class ContractSettlementBillControllerBean extends
		AbstractContractSettlementBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractSettlementBillControllerBean");
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		final ContractSettlementBillInfo settle = (ContractSettlementBillInfo) model;
		checkFinalSettleDup(ctx, settle);
		if(settle.getContractBill()!=null){
			settle.setIsCostSplit(settle.getContractBill().isIsCoseSplit());
		}
		super._submit(ctx, pk, model);
	}

	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		super._cancel(ctx,pk);
		
		updateSettInfoForContrct(ctx, BOSUuid.read(pk.toString()), false);
	}	
	
	/**
	 * 改后金额与拆分金额合计不等，则清除拆分及相关的付款拆分  by Cassiel_peng
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		
		//提交前检查
		final ContractSettlementBillInfo settle = (ContractSettlementBillInfo) model;
		
		//合同可进行多次结算
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("orgUnit.id");
		selector.add("isFinalSettle");
		selector.add("contractBill.id");
		selector.add("curProject.id");
		selector.add("curProject.fullOrgUnit.id");
		
		selector.add("auditTime");
		selector.add("bookedDate");
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		//ContractSettlementBillInfo billInfo = this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId),selector);

		if(BooleanEnum.TRUE.equals(settle.getIsFinalSettle())){
			String contractId = settle.getContractBill().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.FALSE));
			filter.setMaskString("#0 and ( #1 or #2 or #3 )  and  #4");
			if (settle.getId() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", settle.getId().toString(), CompareType.NOTEQUALS));
				filter.setMaskString("#0 and ( #1 or #2 or #3 )  and  #4  and #5");
			} 
			//是否存在其他未审核的结算单
			if (this.exists(ctx,filter)) {
				throw new ContractException(ContractException.AUDITFIRST);
			}
		}
		
		checkBillForSubmit( ctx,model);
		//①、此次修改后的本位币金额
		BigDecimal amount=FDCHelper.toBigDecimal(settle.getSettlePrice(),2);
		FilterInfo filter =null;
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(settle!=null&&settle.getId()!=null&&settle.getContractBill()!=null&&settle.getContractBill().isIsCoseSplit()){	//成本类拆分
				try {
					//判断该结算单是否已经被拆分过，如果未被拆分过则后续操作都不再进行，提高效率。  by Cassiel_peng
					builder.clear();
					builder.appendSql("select fid from T_CON_SettlementCostSplit where FSettlementBillID=? ");
					builder.addParam(settle.getId().toString());
					IRowSet set=builder.executeQuery();
					while(set.next()){//已经被拆分过了
						builder.clear();
						builder.appendSql("select famount from T_CON_SettlementCostSplit where FSettlementBillID=? ");
						builder.addParam(settle.getId().toString());
						IRowSet rowSet=builder.executeQuery();
						while(rowSet.next()){
							//②、该结算单对应的结算拆分的"已拆分结算造价"，即"已拆分金额合计"
							BigDecimal splitedAmount=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"),2);
							if(!amount.equals(splitedAmount)){
								/*String settSplitID=set.getString("fid");//找到某一结算单的拆分单据
								builder.clear();
								builder.appendSql("select fcostbillid from T_CON_SettlementCostSplitEntry where FParentID=? ");
								builder.addParam(settSplitID);
								IRowSet rowSet2=builder.executeQuery();
								while(rowSet.next()){
									//找到相应的付款拆分头
									builder.clear();
									builder.appendSql("select distinct(fparentid) from  T_FNC_PaymentSplitEntry  where  fcostbillid=? ");
									builder.addParam(rowSet2.getString("fcostbillid"));
									IRowSet rowSet3=builder.executeQuery();
									while(rowSet3.next()){
										//删除付款拆分
										filter=new FilterInfo();
										filter.appendFilterItem("costBillId", rowSet2.getString("fcostbillid"));
										PaymentSplitEntryFactory.getLocalInstance(ctx).delete(filter);//删除分录
										filter=new FilterInfo();
										filter.appendFilterItem("id",rowSet3.getString("fparentid"));
										PaymentSplitFactory.getLocalInstance(ctx).delete(filter);//删除头
									}
								}*/
								
								/**
								 *与周勇讨论，如果是提交状态下的结算单做付款申请单款项类型不会是"结算款"，那么在做付款拆分的时候
								 *就一定不会去引用结算拆分，所以在修改该结算单的结算金额的时候就不需要再去清除"付款拆分"  by Cassiel_peng
								 */
								filter=new FilterInfo();
								// 删除结算拆分
								filter.appendFilterItem("parent.settlementBill.id", settle.getId().toString());
								SettlementCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
								filter=new FilterInfo();
								filter.appendFilterItem("settlementBill.id", settle.getId().toString());
								SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
							}
						}
					}
					} catch (SQLException e) {
						e.printStackTrace();
				}
		}
		if(settle!=null&&settle.getId()!=null&&settle.getContractBill()!=null&&!settle.getContractBill().isIsCoseSplit()){//非成本类拆分
				try {
					//判断给结算单是否已经被拆分过，如果未被拆分过则后续操作都不再进行，提高效率。  by Cassiel_peng
					builder.clear();
					builder.appendSql("select fid from T_CON_SettNoCostSplit where FSettlementBillID=? ");
					builder.addParam(settle.getId().toString());
					IRowSet rowSet1=builder.executeQuery();
					while(rowSet1.next()){//已经被拆分过了
						builder.clear();
						builder.appendSql("select famount from T_CON_SettNoCostSplit where FSettlementBillID=? ");
						builder.addParam(settle.getId().toString());
						IRowSet rowSet=builder.executeQuery();
						while(rowSet.next()){
							//②、该结算单对应的结算拆分的"已拆分结算造价"，即"已拆分金额合计"
							BigDecimal splitedAmount=FDCHelper.toBigDecimal(rowSet.getBigDecimal("famount"),2);
							if(!amount.equals(splitedAmount)){
								filter=new FilterInfo();
								filter.appendFilterItem("parent.settlementBill.id", settle.getId().toString());
								SettNoCostSplitEntryFactory.getLocalInstance(ctx).delete(filter);
								filter=new FilterInfo();
								filter.appendFilterItem("settlementBill.id", settle.getId().toString());
								SettNoCostSplitFactory.getLocalInstance(ctx).delete(filter);
							}
						}
					}
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		
		if(settle.getContractBill()!=null){
			settle.setIsCostSplit(settle.getContractBill().isIsCoseSplit());
		}
		checkFinalSettleDup(ctx, (ContractSettlementBillInfo) model);
		
		String costCenterId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		boolean isContractChangeMustComplete = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE);
		if (isContractChangeMustComplete) {
			autoSettlementChangeBill(ctx, model);
		}

		return super._submit(ctx, model);
	}
	
    protected void _update(Context ctx , IObjectPK pk , IObjectValue model) throws
    BOSException , EASBizException
	{
	    super._update(ctx , pk , model);
	    
	    String[] pk1=new String[]{pk.toString()};
	    List contractIds;
		try {
			contractIds = getContractIds(ctx,pk1);
			reComContractSettlementBill(ctx,contractIds) ;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	 
	}

	/**
	 * 
	 * 描述：检查是否最终结算重复
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:jelon 创建时间：2006-9-19
	 *               <p>
	 */
	private void checkFinalSettleDup(Context ctx,
			ContractSettlementBillInfo billInfo) throws BOSException,
			EASBizException {
//		if (billInfo.getIsFinalSettle() != BooleanEnum.TRUE) {
//			return;
//		}

		FilterInfo filter = new FilterInfo();
		// 合同
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", billInfo
						.getContractBill().getId().toString()));
		// 非保存状态
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.SAVED,
						CompareType.NOTEQUALS));
		// 最终结算
		filter.getFilterItems().add(
				new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
		// 非当前结算单
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.FINALSETTLE_DUP);
		}
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		
		checkBillForAudit( ctx,billId,null);
		
		super._audit(ctx, billId);
		
		//合同可进行多次结算
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("orgUnit.id");
		selector.add("isFinalSettle");
		selector.add("contractBill.id");
		selector.add("curProject.id");
		selector.add("curProject.fullOrgUnit.id");
		selector.add("curProject.costCenter");
		selector.add("isCostSplit");
		selector.add("auditTime");
		selector.add("bookedDate");
		selector.add("period.number");
		selector.add("period.beginDate");
		selector.add("period.endDate");
		selector.add("splitState");
		ContractSettlementBillInfo billInfo = this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId),selector);

		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			String contractId = billInfo.getContractBill().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.FALSE));
			filter.setMaskString("#0 and ( #1 or #2)  and  #3");
			//是否存在其他未审核的结算单
			if (this.exists(ctx,filter)) {
				throw new ContractException(ContractException.AUDITFIRST);
			}
			//是否存在未审核的变更审批单     by Cassiel_peng
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.AUDITTING));//不知道这种单据状态是否需要考虑
			filter.setMaskString("#0 and ( #1 or #2 or #3)");
			if(ChangeSupplierEntryFactory.getLocalInstance(ctx).exists(filter)){
				throw new ContractException(ContractException.CHANGEBILLNOTAUDIT);
			}
		}
		
		
		//已修改到结算拆分前 sxhong 2008/1/4
//		changeSettle(ctx,billId.toString());
		
		if(BooleanEnum.FALSE.equals(billInfo.getIsFinalSettle())){
			FilterInfo filter = new FilterInfo();
			// 合同
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", billInfo.getContractBill().getId().toString()));
			// 最终结算
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			
			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.FINALSETTLE_DUP);
			}
		}
//		HashMap param = FDCUtils.getDefaultFDCParam(null,billInfo.getOrgUnit().getId().toString());
//		boolean canSetterMore = false;
//		if(param.get(FDCConstants.FDC_PARAM_MORESETTER)!=null){
//			canSetterMore = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
//		}
		
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			updateSettInfoForContrct(ctx, billId, true);
			
			//更新合同已结算
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_ContractSettlementBill set FIsSettled=1 where fcontractBillId=? ");
			builder.addParam(billInfo.getContractBill().getId().toString());
			builder.execute();
	
			// 自动审批拆分单(完全拆分状态的)
			CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId),
					"T_CON_SettlementCostSplit", "FSettlementBillID");
			
		}
		//结算单审批时，更新对应执行表相关信息
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateSettle(ContractExecInfosInfo.EXECINFO_AUDIT,billInfo.getContractBill().getId().toString());
		
		//鑫苑要求 根据当前时间设置对应期间
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
//		boolean isResetPeriod = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_RESETPERIOD);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){//去掉参数，启用月结统一按以下逻辑处理
			String prjId = billInfo.getCurProject().getId().toString();
			// 财务期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当付款申请单上的“业务日期”和“业务期间”大于“审批日期”（工程项目成本财务“当前期间”）时，付款单的“申请期间”和“申请日期”取付款申请单的“业务期间”和“业务日期”
			 * （2）当付款申请单上的“业务日期”和“业务期间”小于等于“审批日期”（工程项目成本财务“当前期间”）时，付款单的“申请期间”和“申请日期”取（工程项目成本财务“当前期间”）和审批日期，且将审批日期返写回付款申请单上的“业务日期”和“业务期间”。
			 *	
			 *	原理与拆分保存时相同，期间老出问题
			 */
			if(billPeriod!=null&&billPeriod.getNumber()>finPeriod.getNumber()){
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			}else if(finPeriod!=null){
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_ContractSettlementBill set FPeriodId = ?,FBookedDate = ? where fId=? ");
			builder.addParam(shouldPeriod.getId().toString());
			builder.addParam(bookedDate);
			builder.addParam(billId.toString());
			builder.execute();
		}
			
		// 只处理成本类：合同变更、合同结算的拆分自动引用合同拆分的成本科目、拆分比例
		if (billInfo != null && billInfo.isIsCostSplit()&&billInfo.getCurProject() != null && billInfo.getCurProject().getCostCenter() != null && billInfo.getCurProject().getCostCenter().getId() != null
				&& !CostSplitStateEnum.ALLSPLIT.equals(billInfo.getSplitState())) {
			boolean isImportConSplit = FDCUtils.getDefaultFDCParamByKey(ctx, billInfo.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
			if (isImportConSplit) {
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.clear();
				builder.appendSql("select fsplitstate from t_con_contractcostsplit where fcontractbillid=? and fisinvalid=0 ");
				builder.addParam(billInfo.getContractBill().getId().toString());
				IRowSet rs = builder.executeQuery();
				try {
					if (rs != null && rs.size() == 1) {
						rs.next();
						String splitState = rs.getString("fsplitstate");
						if(!CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
							throw new EASBizException(new NumericExceptionSubItem("100","启用了参数：合同变更、合同结算的拆分是否自动引用合同拆分的成本科目、拆分比例。存在未完全拆分的合同，操作取消！"));
						}
					} else {
						throw new EASBizException(new NumericExceptionSubItem("100","启用了参数：合同变更、合同结算的拆分是否自动引用合同拆分的成本科目、拆分比例。存在未拆分的合同，操作取消！"));
					}
					builder.clear();
					builder.appendSql("select split.fsplitstate from t_con_contractchangebill bill ");
					builder.appendSql("left outer join t_con_conchangesplit split on bill.fid=split.fcontractchangeID ");
					builder.appendSql("where bill.fcontractbillid=? ");
					builder.addParam(billInfo.getContractBill().getId().toString());
					rs = builder.executeQuery();
					if(rs!=null){
						while(rs.next()){
							String splitState = rs.getString("fsplitstate");
							if(!CostSplitStateEnum.ALLSPLIT_VALUE.equals(splitState)){
								throw new EASBizException(new NumericExceptionSubItem("100","启用了参数：合同变更、合同结算的拆分是否自动引用合同拆分的成本科目、拆分比例.存在未完全拆分的变更，操作取消！"));
							}
						}
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				SettlementCostSplitFactory.getLocalInstance(ctx).autoSplit4(billId);
			}
		}
		try {
			synContractProgAmt(ctx,billInfo,true);
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractSettleBill.id", billId.toString()));
		if(ContractPCSplitBillFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","存在合约规划拆分，不能进行反审批操作！"));
		}
		checkBillForUnAudit( ctx,billId,null);
		
		//合同可进行多次结算
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("orgUnit.id");
		selector.add("isFinalSettle");
		//contractBill
		selector.add("contractBill.id");
		selector.add("period.id");
		ContractSettlementBillInfo billInfo = this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId),selector);
		
		if(BooleanEnum.FALSE.equals(billInfo.getIsFinalSettle())){
			filter = new FilterInfo();
			// 合同
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", billInfo.getContractBill().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			// 最终结算
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
			//若合同状态为已审批 并且为最终结算单 则不允许反审批
			if (_exists(ctx, filter)) {
				throw new ContractException(ContractException.CANTUNAUDITSETTER);
			}

		}
		if(BooleanEnum.FALSE.equals(billInfo.getIsFinalSettle())){
			String contractId = billInfo.getContractBill().getId().toString();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
			filter.setMaskString("#0 and ( #1 or #2 or #3)  and  #4");
			//如果存在提交、审批中、审批状态的最终结算单，则非最终结算单不能反审批
			if (this.exists(ctx,filter)) {
				throw new ContractException(ContractException.CANTUNAUDITSETTER);
			}
		}
		//若有包含结算款的付款申请单 则最终结算单不能反审批
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("contractId");
			view.getSelector().add("paymentType.payType.id");
			view.setFilter(new FilterInfo());
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("contractId",billInfo.getContractBill().getId().toString()));
			view.getFilter().getFilterItems().add(
					new FilterItemInfo("paymentType.payType.id","Ga7RLQETEADgAAC/wKgOlOwp3Sw="));
			PayRequestBillCollection cons = PayRequestBillFactory.getLocalInstance(ctx)
				.getPayRequestBillCollection(view);
			for(int i = 0;i < cons.size();i++){
				PayRequestBillInfo info = cons.get(i);
				if(info.getId() != null){
					throw new ContractException(ContractException.HASSETTLEPAYREQUESTBILL);
				}
			}
			

		}
		//已拆分的结算单不允许反审批
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(new FilterItemInfo("settlementBill.id", billId.toString()));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		if (SettlementCostSplitFactory.getLocalInstance(ctx).exists(filterSett)
				|| SettNoCostSplitFactory.getLocalInstance(ctx).exists(filterSett)) {
			throw new ContractException(ContractException.SETTLESPLIEDCANNTUNAUDIT);
		}

		
		/**
		 * 结算后做了工程量确认单，结算单不可以反审批。
		 */
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			FilterInfo workLoadFilter = new FilterInfo();
			workLoadFilter.getFilterItems().add(
					new FilterItemInfo("contractBill.id",billInfo.getContractBill().
							getId().toString()));
			workLoadFilter.getFilterItems().add(
					new FilterItemInfo("hasSettled",Boolean.TRUE));
			if(WorkLoadConfirmBillFactory.getLocalInstance(ctx).
					exists(workLoadFilter)){
				throw new WorkLoadException(WorkLoadException.HAS_SETTLEDBILL);
			}
		}
		// 反审批结算单反写规划合约
		try {
			synContractProgAmt(ctx, billInfo, false);
		} catch (SQLException e) {
			logger.error(e);
		}
		super._unAudit(ctx, billId);
		//不允许拆分提交状态下的单据并且是最终结算单(不要求合同必须结算)
		boolean isCanSplitForSubmit = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_SPLITSUBMIT);
		if(!isCanSplitForSubmit&&BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			dealBackData(ctx, billInfo);
		}
		
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
//			SysUtil.abort();
			updateSettInfoForContrct(ctx, billId, false);
	
			// 自动反审批拆分单
			CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId),
					"T_CON_SettlementCostSplit", "FSettlementBillID");
			
			//更新合同下所有结算为未结算 by sxhong
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_CON_ContractSettlementBill set FIsSettled=0 where fcontractBillId=? ");
			builder.addParam(billInfo.getContractBill().getId().toString());
			builder.execute();
		}
		
		//自动删除本期月结数据
		if(billInfo.getPeriod()!=null){
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(billInfo.getContractBill().getId().toString(),
					billInfo.getPeriod().getId().toString());
		}
		
		//结算单审批时，更新对应执行表相关信息
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateSettle(ContractExecInfosInfo.EXECINFO_UNAUDIT,billInfo.getContractBill().getId().toString());
		try {
			synContractProgAmt(ctx,billInfo,true);
		} catch (SQLException e) {
			logger.error(e);
		}
	}

	/**
	 * 当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（签约金额+变更金额），控制余额=控制金额-签约金额，
	 * 当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void synContractProgAmt(Context ctx, ContractSettlementBillInfo model, boolean flag) throws EASBizException, BOSException, SQLException {
		BOSUuid contractBillId = model.getContractBill().getId();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("programmingContract.*");
		ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(
				new ObjectUuidPK(contractBillId.toString()), sic);
		ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql("select fid  from T_CON_CONTRACTBILL where fstate='4AUDITTED' and ");
		builder.appendSql(" fHasSettled =1 and  ");
		builder.appendParam("fid", contractBillId.toString());
		rowSet = builder.executeQuery();
		if (rowSet.next()) {
			// 合同签约金额
			BigDecimal conSignAmt = FDCHelper.ZERO;
			// 合同变更金额
			BigDecimal conChangeAmt = FDCHelper.ZERO;
			// 合同结算金额
			BigDecimal conSettleAmt = FDCHelper.ZERO;
			builder.clear();
			builder = new FDCSQLBuilder(ctx);
			rowSet = null;
			builder
					.appendSql("select con.famount conSignAmt,change.changeAmount conChangeAmt,settle.settleAmount conSettleAmt from t_con_contractbill con ");
			builder
					.appendSql(" left join (select FContractBillID,sum(case when fhassettled = 1 then FBalanceAmount else famount end ) changeAmount from t_con_contractChangeBill where fstate in  ");
			builder.appendSql(" ('4AUDITTED','7ANNOUNCE','8VISA') group by FContractBillID ) change on change.FContractBillID = con.fid ");
			builder
					.appendSql(" left join (select FContractBillID,sum(case when fstate in( '4AUDITTED','7ANNOUNCE','8VISA') then FCurSettlePrice else 0 end) settleAmount from");
			builder
					.appendSql(" T_CON_ContractSettlementBill where fisSettled = 1  group by FContractBillID)  settle on con.fid =  settle.FContractBillID where ");
			builder.appendParam("con.fid", contractBillId.toString());
			rowSet = builder.executeQuery();
			while (rowSet.next()) {
				conSignAmt = FDCHelper.toBigDecimal(rowSet.getString("conSignAmt"));
				conChangeAmt = FDCHelper.toBigDecimal(rowSet.getString("conChangeAmt"));
				conSettleAmt = FDCHelper.toBigDecimal(rowSet.getString("conSettleAmt"));
			}
			// 规划余额
			BigDecimal balanceAmt = pcInfo.getBalance();
			// 控制余额
			BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
					// //结算本币金额
					// BigDecimal settleAmount = model.getTotalSettlePrice();
			//框架合约签约金额
			BigDecimal settleAmountProg = pcInfo.getSettleAmount();
			// 差额
			
			BigDecimal otherBalanceAmount = FDCHelper.ZERO;
			BigDecimal otherControlBalanceAmount = FDCHelper.ZERO;
			BigDecimal otherSettleAmount = FDCHelper.ZERO;
			
			SelectorItemCollection sict = new SelectorItemCollection();
			sict.add("balance");
			sict.add("controlBalance");
			sict.add("signUpAmount");
			sict.add("changeAmount");
			sict.add("settleAmount");
			sict.add("srcId");
			sict.add("estimateAmount");
			sict.add("unAuditContractSettleEA");
			if(flag){
				pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, FDCHelper.add(conSignAmt, conChangeAmt)), conSettleAmt));
				pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSignAmt), conSettleAmt));
				pcInfo.setSettleAmount(FDCHelper.add(settleAmountProg, conSettleAmt));
				
				otherBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(balanceAmt, FDCHelper.add(conSignAmt, conChangeAmt)),
						conSettleAmt), balanceAmt);
				otherControlBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSignAmt), conSettleAmt),
						controlBalanceAmt);
				otherSettleAmount = FDCHelper.subtract(FDCHelper.add(settleAmountProg, conSettleAmt), settleAmountProg);
				
				//主要是补充合同，把合约规划的预估金额置为零，
//				BigDecimal  estimateAmount = pcInfo.getEstimateAmount();
//				pcInfo.setUnAuditContractSettleEA(estimateAmount);
				pcInfo.setEstimateAmount(FDCHelper.ZERO);
//				if(estimateAmount!=null){
//					pcInfo.setBalance(pcInfo.getBalance().add(estimateAmount));
//				}
				IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
				service.updatePartial(pcInfo, sict);
				//对应的预估变动单变为是否最新为false
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
//				filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(filter);
//				ContractEstimateChangeBillCollection  coll = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
//				if(coll.size()>0){
//					ContractEstimateChangeBillInfo contInfo = coll.get(0);
//					contInfo.setIsLastest(false);
//					contInfo.setIsRespite(true);
//					SelectorItemCollection sel = new SelectorItemCollection();
//					sel.add("isLastest");
//					ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
//				}
				
//				String progId = pcInfo.getId().toString();
//				while (progId != null) {
//					String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//					if (nextVersionProgId != null) {
//						pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//						pcInfo.setBalance(FDCHelper.add(pcInfo.getBalance(), otherBalanceAmount));
//						pcInfo.setControlBalance(FDCHelper.add(pcInfo.getControlBalance(), otherControlBalanceAmount));
//						pcInfo.setSettleAmount(FDCHelper.add(pcInfo.getSettleAmount(), otherSettleAmount));
//						service.updatePartial(pcInfo, sict);
//						progId = pcInfo.getId().toString();
//					} else {
//						progId = null;
//					}
//				}
			}else{
				pcInfo.setBalance(FDCHelper.subtract(FDCHelper.add(balanceAmt, conSettleAmt), FDCHelper.add(conSignAmt, conChangeAmt)));
				pcInfo.setControlBalance(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSettleAmt), conSignAmt));
				pcInfo.setSettleAmount(FDCHelper.subtract(settleAmountProg, conSettleAmt));
				
				otherBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(balanceAmt, conSettleAmt), FDCHelper.add(conSignAmt,
						conChangeAmt)), balanceAmt);
				otherControlBalanceAmount = FDCHelper.subtract(FDCHelper.subtract(FDCHelper.add(controlBalanceAmt, conSettleAmt), conSignAmt),
						controlBalanceAmt);
				otherSettleAmount = FDCHelper.subtract(FDCHelper.subtract(settleAmountProg, conSettleAmt), settleAmountProg);
				
//				BigDecimal  estimateAmount = FDCHelper.ZERO;
//				if(pcInfo.getUnAuditContractSettleEA()!=null){
//					estimateAmount=pcInfo.getUnAuditContractSettleEA();
//				}
//				if(!(pcInfo.getEstimateAmount()!=null&&pcInfo.getEstimateAmount().compareTo(FDCHelper.ZERO)>0)){
//					pcInfo.setEstimateAmount(estimateAmount);
//				}
//				pcInfo.setBalance(pcInfo.getBalance().subtract(estimateAmount));
				IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
				service.updatePartial(pcInfo, sict);
				
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
//				filter.getFilterItems().add(new FilterItemInfo("isRespite",Boolean.TRUE));
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(filter);
//				ContractEstimateChangeBillCollection  coll = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
//				if(coll.size()>0){
//					ContractEstimateChangeBillInfo contInfo = coll.get(0);
//					contInfo.setIsLastest(true);
//					contInfo.setIsRespite(false);
//					SelectorItemCollection sel = new SelectorItemCollection();
//					sel.add("isLastest");
//					sel.add("isRespite");
//					ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
//				}
//				String progId = pcInfo.getId().toString();
//				while (progId != null) {
//					String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//					if (nextVersionProgId != null) {
//						pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//						pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherBalanceAmount));
//						pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherControlBalanceAmount));
//						pcInfo.setSettleAmount(FDCHelper.subtract(pcInfo.getSettleAmount(), otherSettleAmount));
//						service.updatePartial(pcInfo, sict);
//						progId = pcInfo.getId().toString();
//					} else {
//						progId = null;
//					}
//				}
			}
		}
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
	
	private void dealBackData(Context ctx, ContractSettlementBillInfo billInfo)
			throws BOSException, EASBizException {
		//测试代码，记得干掉
//		if(true){
//			return;
//		}
		SelectorItemCollection selector = new SelectorItemCollection();;
		//合同最终结算时自动进行变更结算，但最终结算单反审批删除后，变更结算数据依然存在。
		//既然最终结算单审批时可以影响变更结算，那结算单分审批时，变更结算数据也应该回到原来状态 by cassiel 2010-10-08
			String contractBillId = billInfo.getContractBill().getId().toString();
			//变更指令单
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractBillId));
			selector = new SelectorItemCollection();
			selector.add("oriBalanceAmount");
			selector.add("balanceAmount");
			selector.add("hasSettled");
			selector.add("settleTime");
			view.setFilter(filter);
			view.setSelector(selector);
			ContractChangeBillCollection conChangeColl = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
			for(Iterator iter = conChangeColl.iterator();iter.hasNext();){
				ContractChangeBillInfo conChangeInfo = (ContractChangeBillInfo)iter.next();
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("select FOriBalanceAmount,FBalanceAmount,FHasSettled,FSettleTime,FSettleID,FSourceID from T_CON_ConChangeTemp where FSourceID=? ");
				builder.addParam(conChangeInfo.getId().toString());
				IRowSet rowSet = builder.executeQuery();
				try {
					if(rowSet.next()){
						BigDecimal oriBalanceAmt = rowSet.getBigDecimal("FOriBalanceAmount");
						BigDecimal BalanceAmt = rowSet.getBigDecimal("FBalanceAmount");
						boolean hasSettled = rowSet.getBoolean("FHasSettled");
						Date settleTime = rowSet.getDate("FSettleTime");
						
						conChangeInfo.setOriBalanceAmount(oriBalanceAmt);
						conChangeInfo.setBalanceAmount(BalanceAmt);
						conChangeInfo.setHasSettled(hasSettled);
						conChangeInfo.setSettleTime(settleTime);
						
						ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(conChangeInfo, selector);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//还原变更拆分单据
//			String companyId = FDCHelper.getCurCompanyId(ctx, info.getCurProject().getId().toString());
			String companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
			Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyId);
			boolean isFinacial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
			Set allTempSplitSet = new HashSet();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select FSplitID from T_CON_ConChangeSplitTemp ");
			IRowSet rowSet1 = builder.executeQuery();
			try {
				while(rowSet1.next()){
					allTempSplitSet.add(rowSet1.getString("FSplitID"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(allTempSplitSet != null&&allTempSplitSet.size()>0){
			if(isFinacial){//只需更新状态
					builder.clear();
					builder.appendSql("update T_CON_ConChangeSplit set FIsInValid = 1 where FContractBillID=? ");
					builder.addParam(contractBillId);
					builder.executeUpdate();
					builder.clear();
					builder.appendSql("update T_CON_ConChangeSplit set FIsInValid = 0 where FContractBillID = ? ");
					builder.addParam(contractBillId);
					builder.appendParam("and  fid  ",allTempSplitSet.toArray());
					builder.executeUpdate();
				}else{//必须从临时表里找到对应金额然后从变更拆分表里边找到需要还原的唯一的一条数据
					EntityViewInfo _view = new EntityViewInfo();
					SelectorItemCollection _selector = new SelectorItemCollection();
					_selector.add("amount");
					_selector.add("splitID");
					FilterInfo _filter = new FilterInfo();
					_filter.getFilterItems().add(new FilterItemInfo("splitID",allTempSplitSet,CompareType.INCLUDE));
					_view.setFilter(_filter);
					_view.setSelector(_selector);
					CoreBaseCollection splitTempColls = ConChangeSplitTempFactory.getLocalInstance(ctx).getCollection(_view);
					
					_view = new EntityViewInfo();
					_selector = new SelectorItemCollection();
					_selector.add("amount");
					_selector.add("entryID");
					_filter = new FilterInfo();
					_filter.getFilterItems().add(new FilterItemInfo("parent.splitID",allTempSplitSet,CompareType.INCLUDE));
					_view.setFilter(_filter);
					_view.setSelector(_selector);
					CoreBaseCollection splitEntryTempColls = ConChangeSplitEntryTempFactory.getLocalInstance(ctx).getCollection(_view);
					
					for(Iterator iter = splitTempColls.iterator();iter.hasNext();){
						ConChangeSplitTempInfo splitTempInfo = (ConChangeSplitTempInfo)iter.next();
						builder.clear();
						builder.appendSql("update T_CON_ConChangeSplit set famount=? where fid=? ");
						builder.addParam(splitTempInfo.getAmount());
						builder.addParam(splitTempInfo.getSplitID());
						builder.executeUpdate();
					}
					for(Iterator iter = splitEntryTempColls.iterator();iter.hasNext();){
						ConChangeSplitEntryTempInfo splitEntryTempInfo = (ConChangeSplitEntryTempInfo)iter.next();
						builder.clear();
						builder.appendSql("update T_CON_ConChangeSplitEntry set famount = ? where fid=? ");
						builder.addParam(splitEntryTempInfo.getAmount());
						builder.addParam(splitEntryTempInfo.getEntryID());
						builder.executeUpdate();
					}
				}
			}
		}
	/**
	 * 
	 * 描述：更新合同结算状态和结算金额
	 * 
	 * @param ctx
	 * @param billId
	 * @param isAudit
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-10-13
	 *               <p>
	 */
	private void updateSettInfoForContrct(Context ctx, BOSUuid billId,
			boolean isAudit) throws BOSException, EASBizException {
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("contractBill.id");
		sic.add("isFinalSettle");
		sic.add("settlePrice");
		sic.add("curProject.fullOrgUnit.id");
		ContractSettlementBillInfo billInfo = getContractSettlementBillInfo(
				ctx, new ObjectUuidPK(billId),sic);
		SelectorItemCollection selector = new SelectorItemCollection();

		ContractBillInfo contractBillInfo = billInfo.getContractBill();
		String contractID = billInfo.getContractBill().getId().toString();
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		// 当前结算单，是否最终结算
		if (billInfo.getIsFinalSettle().equals(BooleanEnum.TRUE)) {
			contractBillInfo.setHasSettled(isAudit);
			selector.add("hasSettled");
		}

		// 已审批结算单，金额合计
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("contractBill.id", contractBillInfo.getId()
//						.toString()));
//		filter.getFilterItems().add(
//				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
//		view.setFilter(filter);
//		view.getSelector().add("settlePrice");
//		ContractSettlementBillCollection coll = getContractSettlementBillCollection(
//				ctx, view);
//
//		BigDecimal amount = new BigDecimal(0);
//		for (Iterator iter2 = coll.iterator(); iter2.hasNext();) {
//			billInfo = (ContractSettlementBillInfo) iter2.next();
//			amount = amount.add(billInfo.getSettlePrice());
//		}
		
		//最终累计金额就是settlePrice
		BigDecimal amount = GlUtils.zero;
		if(isAudit){	
			 amount = billInfo.getSettlePrice();
		}
		
		contractBillInfo.setSettleAmt(amount);
		selector.add("settleAmt");

		IContractBill iContract = ContractBillFactory.getLocalInstance(ctx);
		iContract.updatePartial(contractBillInfo, selector);
		
		// 更新索赔单,设置已索赔状态
		auditCompensationBill(ctx, contractID, isAudit);
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("update T_Con_ContractChangeBill set FISConSetted=? where fcontractBillId=?");
		builder.addParam(Boolean.valueOf(isAudit));
		builder.addParam(contractBillInfo.getId().toString());
		builder.execute();
		//同步合同执行状态
//		ContractStateHelper.synToExecState(ctx, contractBillInfo.getId().toString());
		
		//审批与反审批时都要保证不小于工程量
//		BigDecimal latestPrice = FDCUtils.getContractLastAmt(ctx, contractID);
////		boolean isSeparate = FDCUtils.getDefaultFDCParamByKey(ctx, companyID, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
////		if(isSeparate){
////			BigDecimal workload = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoad(contractID);
////			if (latestPrice.compareTo(FDCHelper.toBigDecimal(workload, 2)) == -1) {
////				throw new EASBizException(new NumericExceptionSubItem("111",
////						"合同最新造价小于合同的工程确认单已审批累计金额，请先修改工程量确认单！"));
////			}
////		}
	}

	private void auditCompensationBill(Context ctx, String contractId,
			boolean isAudited) throws BOSException, EASBizException {
		FilterInfo filter;
		EntityViewInfo conView = new EntityViewInfo();
		filter = new FilterInfo();
		conView.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("contract.Id", contractId));
		CompensationBillCollection compensations = CompensationBillFactory
				.getLocalInstance(ctx).getCompensationBillCollection(conView);
		for (int i = 0; i < compensations.size(); i++) {
			CompensationBillInfo info = compensations.get(i);
			if (info.getDescription() != null) {
				info.setIsCompensated(isAudited);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add(new SelectorItemInfo("isCompensated"));
				CompensationBillFactory.getLocalInstance(ctx).updatePartial(
						info, sels);
			}
		}
	}
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		//清除关联违约、扣款、奖励
		//
		if(arrayPK.length<=0) return;
		String[] pk=new String[arrayPK.length];
		for(int i=0;i<pk.length;i++){
			pk[i]=arrayPK[i].toString();						
		}
		
		List contractIds= null;
		try{
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			contractIds= getContractIds(ctx,pk);
			if(contractIds==null){
				return ;
			}
			
			//违约
			builder.clear();
			builder.appendSql("update T_CON_Compensationbill set fiscompensated=0 where ");
			builder.appendParam("fcontractid", contractIds.toArray());
			builder.appendSql(" and fid not in (select FCompensationID as fid from T_CON_CompensationOfPayReqBill t1 ");
			builder.appendSql("inner join t_con_payrequestbill t2 on t1.fpayrequestbillid=t2.fid and ");
			builder.appendParam("t2.fcontractid", contractIds.toArray());
			builder.appendSql(")");
			builder.execute();
			
			//奖励
			builder.clear();
			builder.appendSql("update T_CON_Guerdonbill set fisGuerdoned=0 where ");
			builder.appendParam("fcontractid", contractIds.toArray());
			builder.appendSql(" and fid not in (select FGuerdonID as fid from t_con_guerdonofpayreqbill t1 ");
			builder.appendSql("inner join t_con_payrequestbill t2 on t1.fpayrequestbillid=t2.fid and ");
			builder.appendParam("t2.fcontractid", contractIds.toArray());
			builder.appendSql(")");
			builder.execute();
			
			//扣款单
			builder.clear();
			builder.appendSql("update t_fnc_deductbillentry set fhasApplied=0 where ");
			builder.appendParam("fcontractid", contractIds.toArray());
			builder.appendSql(" and fid not in (select fdeductbillentryid from t_con_deductofpayreqbillentry) ");
			builder.execute();
			
			
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		//由于结算单
		
		super._delete(ctx, arrayPK);
		
		reComContractSettlementBill(ctx,contractIds) ;
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		_delete(ctx, new IObjectPK[]{pk});
	}
	
	/**
	 * 变更结算
	 */
	private void changeSettle(Context ctx,String settleId) throws BOSException,EASBizException {
		if(FDCHelper.isEmpty(settleId)){
			return;
		}
/*		
 * 		结算金额＝预算价（测算值）＋结算差额，
 *		结算差额＝（合同结算价－合同签约价－合同所有指令单预算价总和）×（变更指令单预算价/合同所有指令单预算价总和）
*/
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("contractBill.id");
		selector.add("contractBill.amount");
		selector.add("settlePrice");
		ContractSettlementBillInfo settleInfo=ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(new ObjectUuidPK(settleId), selector);
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("contractBill.id", settleInfo.getContractBill().getId().toString());
		view.getSelector().add("number");
		view.getSelector().add("amount");
		view.getSelector().add("balanceAmount");
		view.getSelector().add("state");
		ContractChangeBillCollection changes=ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		BigDecimal total=FDCHelper.ZERO;
		for(Iterator iter=changes.iterator();iter.hasNext();){
			ContractChangeBillInfo change=(ContractChangeBillInfo)iter.next();
			if(change.getState()!=FDCBillStateEnum.VISA){
				throw new EASBizException(
						new NumericExceptionSubItem("120","变更单指令单：“"+change.getNumber()+"” 状态为：“"+change.getState() +"” 不能进行变更结算,必须为“已签证”状态")
				);
			}
			total=total.add(FDCHelper.toBigDecimal(change.getAmount()));
		}
		if(total.compareTo(FDCHelper.ZERO)==0){
			return;
		}
		BigDecimal tempAmt=FDCHelper.toBigDecimal(settleInfo.getSettlePrice()).subtract(FDCHelper.toBigDecimal(settleInfo.getContractBill().getAmount()));
		tempAmt=tempAmt.subtract(FDCHelper.toBigDecimal(total));
		BigDecimal settleDifAmt=FDCHelper.ZERO;
		BigDecimal balanceAmt=FDCHelper.ZERO;
		SelectorItemCollection updateSelector=new SelectorItemCollection();
		updateSelector.add("balanceAmount");
		updateSelector.add("hasSettled");
		updateSelector.add("settleTime");
		Date settleTime=new Date();
		for(Iterator iter=changes.iterator();iter.hasNext();){
			ContractChangeBillInfo change=(ContractChangeBillInfo)iter.next();
			settleDifAmt=tempAmt.multiply(FDCHelper.toBigDecimal(change.getAmount()));
			settleDifAmt=FDCHelper.divide(settleDifAmt, total, 2, BigDecimal.ROUND_HALF_UP);
//			settleDifAmt=settleDifAmt.divide(total, 2, BigDecimal.ROUND_HALF_UP);
			balanceAmt=settleDifAmt.add(FDCHelper.toBigDecimal(change.getAmount()));
			change.setBalanceAmount(FDCHelper.toBigDecimal(balanceAmt,2));
			change.setHasSettled(true);
			change.setSettleTime(settleTime);
			ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(change, updateSelector);
			ConChangeSplitFactory.getLocalInstance(ctx).autoSplit(new ObjectUuidPK(change.getId()));
		}
	}
	
	//单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		Map initMap = super._fetchInitData(ctx,paramMap);		
		
		//合同单据
		String contractBillId = (String)paramMap.get("contractBillId");
		if(contractBillId!=null && initMap.get(FDCConstants.FDC_INIT_CONTRACT)==null){
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("exRate");
			selectors.add("orgUnit.id");
			selectors.add("currency.number");
			selectors.add("currency.name");
			selectors.add("CU.id");
			selectors.add("grtRate");
			selectors.add("curProject.id");
			selectors.add("isCoseSplit");
	
			selectors.add("contractType.longNumber");
			selectors.add("respDept.number");
			
			ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
			getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selectors);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
		}
		
		return initMap;
	}
	
	   private SelectorItemCollection getSic(){
			// 此过滤为详细信息定义
	        SelectorItemCollection sic = new SelectorItemCollection();
	        sic.add(new SelectorItemInfo("id"));
	        sic.add(new SelectorItemInfo("number"));
	        sic.add(new SelectorItemInfo("period.id"));
	        sic.add(new SelectorItemInfo("period.beginDate"));	
	        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
	        sic.add(new SelectorItemInfo("curProject.id"));
	        sic.add(new SelectorItemInfo("curProject.displayName"));	
	        
	        return sic;
	    }
	    
		//提交时校验单据期间不能在工程项目的当前期间之前
	    private void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
			
			//不能落于当前成本期间之前
	    	ContractSettlementBillInfo contractSettleBill = (ContractSettlementBillInfo)model;
			
			//是否启用财务一体化
			String comId = null;
			if( contractSettleBill.getCurProject().getFullOrgUnit()==null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("fullOrgUnit.id");
				CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(contractSettleBill.getCurProject().getId().toString()),sic);
				
				comId = curProject.getFullOrgUnit().getId().toString();
			}else{
				comId = contractSettleBill.getCurProject().getFullOrgUnit().getId().toString();
			}
			
			boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
			//因为月结只检查了成本类的，所以此处不控制财务类的
			if(isInCore&&contractSettleBill.isIsCostSplit()){
				PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractSettleBill.getCurProject().getId().toString(),true);
				if(contractSettleBill.getPeriod()!=null && contractSettleBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
					//单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
					throw new ContractException(ContractException.CNTPERIODBEFORE);
				}
				
				
				if(contractSettleBill.getContractBill()!=null){
			    	// 此过滤为详细信息定义
			        SelectorItemCollection sic = new SelectorItemCollection();
			        sic.add(new SelectorItemInfo("period.beginDate"));	

			        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractSettleBill.getContractBill().getId().toString()),sic);
					//检查功能是否已经结束初始化

					if(contractSettleBill.getPeriod()!=null && 
							contractSettleBill.getPeriod().getBeginDate().before(contractBillInfo.getPeriod().getBeginDate())){
						//单据期间不能在合同的当前期间之前 CNTPERIODBEFORE
						throw new ContractException(ContractException.CNTPERIODBEFORECON);
					}
				}
			}
		}
		
		//审核校验
		private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
			if(true){
				return;//不需要校验，审批时已处理期间
			}
			/*ContractSettlementBillInfo model = (ContractSettlementBillInfo)billInfo;

	        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
	        	model= this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
	        }

			//检查功能是否已经结束初始化
			String comId = model.getCurProject().getFullOrgUnit().getId().toString();
				
			//是否启用财务一体化
			boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
			if(isInCore){
				String curProject = model.getCurProject().getId().toString();	
				//成本已经月结
				PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
				if(model.getPeriod()!=null && model.getPeriod().getBeginDate().after(costPeriod.getEndDate())){
					throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});
				}
				
//				PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
//				if(bookedPeriod.getBeginDate().after(finPeriod.getEndDate())){
//					throw new  ContractException(ContractException.AUD_FINNOTCLOSE,new Object[]{model.getNumber()});
//				}	
			}*/
		}
		//审核校验
		private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
			if(true){
				return;//
			}
			/*ContractSettlementBillInfo model = (ContractSettlementBillInfo)billInfo;

	        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
	        	model= this.getContractSettlementBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
	        }
			//检查功能是否已经结束初始化
			String comId = model.getCurProject().getFullOrgUnit().getId().toString();
				
			//是否启用财务一体化
			boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
			if(isInCore){
				String curProject = model.getCurProject().getId().toString();

				//单据期间在工程项目当前期间之前，不能反审核
				PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
				if(model.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
					throw new  ContractException(ContractException.CNTPERIODBEFORE);
				}	
				
//				if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//					throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{model.getCurProject().getDisplayName()});
//				}	
			}*/
		}
		
	private List getContractIds(Context ctx,String[] pk) throws BOSException, SQLException{
		List contractIds= null;
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcontractBillid from T_CON_ContractSettlementBill where ");
		builder.appendParam("fid", pk);
		IRowSet rowSet = builder.executeQuery();
		if(rowSet==null||rowSet.size()<1){
			return null;
		}
		contractIds=new ArrayList(rowSet.size());
		
		while(rowSet.next()){
			String contractId = rowSet.getString("fcontractBillid");
			contractIds.add(contractId);

		}
		
		return contractIds;
	}

	private void reComContractSettlementBill(Context ctx,List contractIds) throws BOSException, EASBizException	{
			if(contractIds!=null){
				//重算累计
				ContractSettlementBillCollection col = null;
				ContractSettlementBillInfo info = null;			
				
				PayRequestBillCollection payCol = null;
				PayRequestBillInfo payInfo=null;
				 BigDecimal B100 = new BigDecimal("100");
//				info.getContractBill()
				for(int i=0;i<contractIds.size();i++){
					String contractId = (String)contractIds.get(i);
						
					//重算结算单
					{
						EntityViewInfo view=new EntityViewInfo();
						FilterInfo filter=new FilterInfo();
						view.setFilter(filter);
						filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
						SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
						sorterItemInfo.setSortType(SortType.ASCEND);
						view.getSorter().add(sorterItemInfo);
						
						view.getSelector().add("isFinalSettle");
						view.getSelector().add("createTime");
						view.getSelector().add("originalAmount");
						view.getSelector().add("settlePrice");
						view.getSelector().add("curOriginalAmount");
						view.getSelector().add("curSettlePrice");
						view.getSelector().add("totalOriginalAmount");
						view.getSelector().add("totalSettlePrice");
						//保修金
						view.getSelector().add("qualityGuaranteRate");
						view.getSelector().add("qualityGuarante");
						
						col = this.getContractSettlementBillCollection(ctx,view);
						if(col.size()>0){
							for(int j=0;j<col.size();j++){
								info=col.get(j);
								if(info.getQualityGuaranteRate()==null){
									info.setQualityGuaranteRate(GlUtils.zero);
								}
								if(j==0){
									info.setTotalOriginalAmount(info.getCurOriginalAmount());
									info.setTotalSettlePrice(info.getCurSettlePrice());
		
									info.setOriginalAmount(info.getCurOriginalAmount());
									info.setSettlePrice(info.getCurSettlePrice());
									
									info.setQualityGuarante(
											FDCHelper.toBigDecimal(info.getQualityGuaranteRate().multiply(info.getTotalSettlePrice()).divide(B100,BigDecimal.ROUND_HALF_UP,2),2));
								}else{
									BigDecimal oriAmount = col.get(j-1).getTotalOriginalAmount();
									BigDecimal settlePrice = col.get(j-1).getTotalSettlePrice();
									
									if(BooleanEnum.TRUE.equals(info.getIsFinalSettle())){
										oriAmount = info.getCurOriginalAmount().add(oriAmount);
										settlePrice = info.getCurSettlePrice().add(settlePrice);
										info.setTotalOriginalAmount(oriAmount);
										info.setTotalSettlePrice(settlePrice);
		
										info.setOriginalAmount(oriAmount);
										info.setSettlePrice(settlePrice);
									}else{
										oriAmount = info.getCurOriginalAmount().add(oriAmount);
										settlePrice = info.getCurSettlePrice().add(settlePrice);
										
										info.setTotalOriginalAmount(oriAmount);
										info.setTotalSettlePrice(settlePrice);
		
										info.setOriginalAmount(info.getCurOriginalAmount());
										info.setSettlePrice(info.getCurSettlePrice());
									}
								}
								
								_updatePartial(ctx, info, view.getSelector());
								
							}
						}
					}
					//重算付款申请单
					{
						EntityViewInfo view=new EntityViewInfo();
						FilterInfo filter=new FilterInfo();
						view.setFilter(filter);
						filter.getFilterItems().add(new FilterItemInfo("contractId",contractId));
						SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
						sorterItemInfo.setSortType(SortType.ASCEND);
						view.getSorter().add(sorterItemInfo);

						view.getSelector().add("createTime");
						view.getSelector().add("totalSettlePrice");
					
						payCol = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
						
						if(payCol.size()>0){
							EntityViewInfo settView=new EntityViewInfo();
							
							settView.getSelector().add("curOriginalAmount");
							settView.getSelector().add("curSettlePrice");
							
							for(int j=0;j<payCol.size();j++){
								payInfo=payCol.get(j);
								Timestamp time = payInfo.getCreateTime();
								
								FilterInfo settFilter=new FilterInfo();
								settView.setFilter(settFilter);
								settFilter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
								settFilter.getFilterItems().add(new FilterItemInfo("createTime",time,CompareType.LESS_EQUALS));	
								
								col = this.getContractSettlementBillCollection(ctx,settView);
								BigDecimal settlePrice =  GlUtils.zero;
								if(col.size()>0){
									BigDecimal oriAmount = GlUtils.zero;								
									
									for(int kk=0;kk<col.size();kk++){
										info=col.get(kk);
										
										oriAmount = oriAmount.add(info.getCurOriginalAmount());
										settlePrice = settlePrice.add(info.getCurSettlePrice());
									}												
								}
									
								payInfo.setTotalSettlePrice(settlePrice);					
								
								PayRequestBillFactory.getLocalInstance(ctx).updatePartial(payInfo, view.getSelector());
							}
						}
						
					}
				}
			}
		}
	
	//如果编码重复重新取编码
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, EASBizException
	{
		handleIntermitNumberForReset(ctx, info);
	}

	protected void _split(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		
	}
	
	/**
	 * 描述：对未结算的变更指令单进行自动结算，将测算金额写到结算金额中，并更新结算状态
	 * @param ctx				上下文<p>
	 * @param model				合同结算单
	 * @throws BOSException
	 * @throws EASBizException
	 * @author zhiyuan_tang 2010/07/27
	 */
	protected void autoSettlementChangeBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//获取合同结算单
		ContractSettlementBillInfo contractSettlementBill = (ContractSettlementBillInfo)model;
		//获取结算单对应的合同
		ContractBillInfo contractBill =  contractSettlementBill.getContractBill();
		//根据合同获取所有的未结算的变更指令单
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractBill",contractBill.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.FALSE));
		//根据周鹏意见， 只有签证的才能结算 add by zhiqiao_yang at 2010-10-14
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.VISA));
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED, CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED, CompareType.NOTEQUALS));
//		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING, CompareType.NOTEQUALS));
		view.getSelector().add("*");
//		view.getSelector().add("number");
		ContractChangeBillCollection contractChangeCol = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("oriBalanceAmount");
		selector.add("balanceAmount");
		selector.add("hasSettled");
		selector.add("settleTime");
		ContractChangeBillInfo contractchageBill = null;
		for (int i = 0; i < contractChangeCol.size(); i++) {
			contractchageBill = contractChangeCol.get(i);
			contractchageBill.setOriBalanceAmount(contractchageBill.getOriginalAmount()); //结算原币金额
			contractchageBill.setBalanceAmount(contractchageBill.getAmount());	//结算本币金额
			contractchageBill.setHasSettled(true);	//结算状态
			contractchageBill.setSettleTime(Calendar.getInstance().getTime());
			ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(contractchageBill, selector);
		}
	}

	protected boolean _autoDelSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//审核的结算单不能自动删除拆分
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("state"));
        ContractSettlementBillInfo settlementBillInfo = 
        	ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillInfo(pk, sic);
		if(FDCBillStateEnum.AUDITTED.equals(settlementBillInfo.getState())) {
			return false;
		}
		
		//删除拆分
		CostSplitBillAutoAuditor.autoDelete(ctx, pk, "T_CON_SettlementCostSplit", "FSettlementBillID");
		//非成本拆分
		CostSplitBillAutoAuditor.autoDelete(ctx, pk, "T_CON_SettNoCostSplit", "FSettlementBillID");
		String settlementId = pk.toString();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.appendSql("update T_CON_ContractSettlementBill set fsplitstate='1NOSPLIT' where fid=?");
		builder.addParam(settlementId);
		builder.execute();
		return true;
		
	}

	protected boolean _isAllSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("splitState"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        ContractSettlementBillInfo settlementBillInfo = this.getContractSettlementBillInfo(ctx,pk,sic);		
		
		return CostSplitStateEnum.ALLSPLIT.equals(settlementBillInfo.getSplitState());
	}

	@Override
	protected void _costIndex(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		
	}
}