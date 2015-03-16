package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.botp.BOTBillOperStateEnum;
import com.kingdee.eas.base.dap.DAPTransformResult;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewCollection;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.util.CostAccountWithAccountHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.fdc.finance.WorkLoadException;
import com.kingdee.eas.fdc.finance.WorkLoadPrjBillEntryInfo;
import com.kingdee.eas.fdc.finance.app.voucher.IFDCVoucherEntryCreator;
import com.kingdee.eas.fdc.finance.app.voucher.VoucherCreatorFactory;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.fdc.pm.ProjectFillBillCollection;
import com.kingdee.eas.fdc.pm.ProjectFillBillEntryInfo;
import com.kingdee.eas.fdc.pm.ProjectFillBillFactory;
import com.kingdee.eas.fdc.pm.ProjectFillBillInfo;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryFactory;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

public class WorkLoadConfirmBillControllerBean extends AbstractWorkLoadConfirmBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.WorkLoadConfirmBillControllerBean");

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcontractbillid,sum(FWorkLoad) as workLoad from T_FNC_WorkLoadConfirmBill");
		builder.appendSql(" where fid=? or (fcontractbillid =(select fcontractbillId from T_FNC_WorkLoadConfirmBill where fid=?) and fstate=?) ");
		builder.appendSql(" group by fcontractbillid ");
		builder.addParam(billId.toString());
		builder.addParam(billId.toString());
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		IRowSet rowSet=builder.executeQuery();
		String contractId = null;
		try {
			if (rowSet.next()) {
				contractId = new String("");
				contractId = rowSet.getString("fcontractbillId");
				BigDecimal workLoad = rowSet.getBigDecimal("workLoad");
				BigDecimal lastPrice = FDCUtils.getContractLastAmt(ctx,contractId);
				BigDecimal subtract = FDCHelper.subtract(workLoad, lastPrice);
				if (subtract != null && subtract.signum() > 0) {
					throw new EASBizException(new NumericExceptionSubItem("100","累计确认完工工程量超过合同最新造价，不能提交/审批！"));
				}
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		
		super._audit(ctx, billId);
		/*********同步工程量清单的数据到合同执行情况 -by neo***********/
		ContractExecInfosFactory.getLocalInstance(ctx).
			updatePayment(ContractExecInfosInfo.EXECINFO_PAY, contractId);
		
		updatePeriod(ctx, billId);
	}
	
	/**
	 * 根据当前项目成本期间更新暂缓单据的业务日期和确认期间
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, BOSUuid billId) throws EASBizException, BOSException {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isRespite");
		selectors.add("bookedDate");
		selectors.add("period.*");
		selectors.add("curProject.id");
		selectors.add("curProject.fullOrgUnit.id");
		WorkLoadConfirmBillInfo billInfo = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getWorkLoadConfirmBillInfo(new ObjectUuidPK(billId), selectors);
		if (!billInfo.isIsRespite()) {
			//只处理暂缓的单据
			return;
		}
		String companyID = billInfo.getCurProject().getFullOrgUnit().getId().toString();
		Map paramMap = FDCUtils.getDefaultFDCParam(ctx, companyID);
		boolean isInCore = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INCORPORATION);
		if(isInCore){
			//启用月结统一按以下逻辑处理
			String prjId = billInfo.getCurProject().getId().toString();
			//成本期间
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx, prjId, true);
			PeriodInfo billPeriod = billInfo.getPeriod();
			PeriodInfo shouldPeriod = null;//最终所在期间
			Date bookedDate = DateTimeUtils.truncateDate(billInfo.getBookedDate());
			
			if(finPeriod==null){
				throw new EASBizException(new NumericExceptionSubItem("100","单据所对应的组织没有当前时间的期间，请先设置！"));
			}
			/***************
			 * （1）当工程量确认单上的“业务日期”和“业务期间”大于工程项目成本财务“当前期间”时，“业务期间”不变
			 * （2）当工程量确认单上的“业务日期”和“业务期间”小于等于工程项目成本财务“当前期间”时，“业务期间”更新为工程项目成本财务“当前期间”
			 *	
			 *	原理与拆分保存时相同，期间老出问题
			 */
			if (billPeriod != null && billPeriod.getNumber() > finPeriod.getNumber()) {
				if (bookedDate.before(billPeriod.getBeginDate())) {
					bookedDate = billPeriod.getBeginDate();
				} else if (bookedDate.after(billPeriod.getEndDate())) {
					bookedDate = billPeriod.getEndDate();
				}
				shouldPeriod = billPeriod;
			} else if (finPeriod != null) {
				if (bookedDate.before(finPeriod.getBeginDate())) {
					bookedDate = finPeriod.getBeginDate();
				} else if (bookedDate.after(finPeriod.getEndDate())) {
					bookedDate = finPeriod.getEndDate();
				}
				shouldPeriod = finPeriod;
			}
			
			//更新单据的业务日期，业务期间，确认期间和暂缓状态
			FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
			builder.appendSql("update T_FNC_WorkLoadConfirmBill set FPeriodId = ?, FConfirmDate = ?, FBizDate = ?, FBookedDate = ?, FIsRespite = ? where fId=? ");
			builder.addParam(shouldPeriod.getId().toString());
			builder.addParam(bookedDate);
			builder.addParam(bookedDate);
			builder.addParam(bookedDate);
			builder.addParam(Boolean.FALSE);
			builder.addParam(billId.toString());
			builder.execute();
		}
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		checkWorkLoad(ctx,billId);
		checkHasSettled(ctx, billId);
		
		//检查是否有拆分数据,如果有拆分数据则不让反审批
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id",billId.toString()));
		if(PaymentSplitFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("111","工程量确认单已经拆分或作废,不能反审批!"));
		}
		
		super._unAudit(ctx, billId);
		/*********同步工程量清单的数据到合同执行情况 -by neo***********/
		String contractId = WorkLoadConfirmBillFactory.getLocalInstance(ctx).
		getWorkLoadConfirmBillInfo(new ObjectUuidPK(billId)).
		getContractBill().getId().toString();
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updatePayment(ContractExecInfosInfo.EXECINFO_UNPAY,contractId);
	}
	
	/**
	 * 检查是否已经有结算后添加的工程量确认单
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	
	protected void checkHasSettled (Context ctx, BOSUuid billId) throws EASBizException, BOSException {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("contractBill.hasSettled");
    	sic.add("hasSettled");
		WorkLoadConfirmBillInfo workLoadInfo = getWorkLoadConfirmBillInfo(ctx,new ObjectUuidPK(billId),sic);
		if(workLoadInfo.isHasSettled()) return;
		if(!workLoadInfo.getContractBill().isHasSettled()) return;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id",workLoadInfo.getContractBill().getId(),CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("hasSettled",Boolean.TRUE,CompareType.EQUALS));
		if(WorkLoadConfirmBillFactory.getLocalInstance(ctx).exists(filter))	{			
			throw new WorkLoadException(WorkLoadException.HAS_SETTLEDBILL);
		}
	}
	
	
	/**
	 * 检查累计完工工程量是否大于累计付款
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws EASBizException
	 */
    private void checkWorkLoad(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("contractBill.id");
    	sic.add("hasSettled");
    	WorkLoadConfirmBillInfo wlInfo = getWorkLoadConfirmBillInfo(ctx,new ObjectUuidPK(billId),sic);
    	String contractId = wlInfo.getContractBill().getId().toString();
    	
    	// R110318-019 如果结算单、结算工程量确认单、付款申请单都审批了，则循环判断导致3个单都无法反审批
		// 现在需要添加一个判断，当前是结算工程量确认单时，只需要合同下没有结算状态的付款申请单就可以反审批
		// 因为请款单有控制，金额不能大于工程量确认，且结算时只能新增结算款的请款单，所以反审批不会引起问题
		// edit by emanon
		if (wlInfo.isHasSettled()) {
			FilterInfo filterIsSt = new FilterInfo();
			filterIsSt.getFilterItems().add(
					new FilterItemInfo("contractId", contractId));
			filterIsSt.getFilterItems().add(
					new FilterItemInfo("paymentType.name", "%结算款%",
							CompareType.LIKE));
			if (PayRequestBillFactory.getLocalInstance(ctx).exists(filterIsSt)) {
				throw new EASBizException(new NumericExceptionSubItem("111",
						"对应完工工程量确认已请款，不能反审批，请先删除结算付款申请单！"));
			} else {
				// 没有结算状态的请款单时，不要再判断金额了
				return;
			}
		}
		// end
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.getSelector().add("projectPriceInContract");
    	view.getSelector().add("state");
    	filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
//    	filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", TypeInfo.progressID));
    	view.setFilter(filter);
    	PayRequestBillCollection payReqColl = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
    	//付款申请单累计付款
    	BigDecimal allProjectPriceInContract = FDCHelper.ZERO;
    	int size = 0;
    	if(payReqColl != null && payReqColl.size() > 0){
    		size = payReqColl.size();
    		for(int i=0;i<payReqColl.size();i++){
    			PayRequestBillInfo info = payReqColl.get(i);
    			allProjectPriceInContract = allProjectPriceInContract.add(
    					FDCHelper.toBigDecimal(info.getProjectPriceInContract(),2));
    		}
    	}
    	view = new EntityViewInfo();
    	filter = new FilterInfo();
    	view.getSelector().add("workLoad");
    	filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
    	filter.getFilterItems().add(new FilterItemInfo("id",billId.toString(),CompareType.NOTEQUALS));
    	view.setFilter(filter);
    	WorkLoadConfirmBillCollection wlColl = getWorkLoadConfirmBillCollection(ctx,view);
    	//累计工程量
    	BigDecimal allWorkLoad = FDCHelper.ZERO;
    	if (wlColl != null && wlColl.size() > 0) {
			for (int i = 0; i < wlColl.size(); i++) {
				WorkLoadConfirmBillInfo info = wlColl.get(i);
				allWorkLoad = FDCHelper.add(allWorkLoad, FDCHelper
						.toBigDecimal(info.getWorkLoad(), 2));
			}
		}
		/**
		 * 反审批时需要保证合同下已审批通过的累计完工工程量大于合同下所有状态的累计合同内工程款数，
		 * 否则提示：对应完工工程量确认已请款，不能反审批，请先删除对应的付款申请单！
		 */
		if (allProjectPriceInContract.compareTo(allWorkLoad) == 1) {
			if(size==0){
				return;
			}
			throw new EASBizException(new NumericExceptionSubItem("111",
					"对应完工工程量确认已请款，不能反审批，请先删除对应的付款申请单！"));
		}
    }
    /**
	 * 获得累计确认工程量
	 */
	protected BigDecimal _getWorkLoad(Context ctx, String contractId)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select sum(FWorkLoad) as workLoad from T_FNC_WorkLoadConfirmBill where ");
		builder.appendParam("FContractBillId", contractId);
		builder.appendSql(" and ");
		builder.appendParam("fstate", FDCBillStateEnum.AUDITTED_VALUE);
		IRowSet rs = builder.executeQuery();
		BigDecimal workLoad = FDCHelper.ZERO;
		try {
			while(rs.next()){
				workLoad = rs.getBigDecimal("workLoad");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return workLoad;
	}
    /**
     * 获得累计确认工程量
     */
	protected Map _getWorkLoad(Context ctx, Set contractIds)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select sum(FWorkLoad) as workLoad ,FContractBillId from T_FNC_WorkLoadConfirmBill where FContractBillId in (");
		builder.appendParam(contractIds.toArray());
		builder.appendSql(" ) group by FContractBillId");
		IRowSet rs = builder.executeQuery();
		Map workLoadMap = new HashMap();
		try {
			while(rs.next()){
				String contractBillId = rs.getString("FContractBillId");
				BigDecimal workLoad = rs.getBigDecimal("workLoad");
				if(!workLoadMap.containsKey(contractBillId)){
					workLoadMap.put(contractBillId, workLoad);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return workLoadMap;
	}
	/**
	 * 获取不包括自身的工程量累计
	 */
	protected BigDecimal _getWorkLoadWithoutId(Context ctx, String contractId,
			String workLoadId) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(" select sum(FWorkLoad) as workLoad from T_FNC_WorkLoadConfirmBill where ");
		builder.appendParam("FContractBillId", contractId);
		if(workLoadId != null){
			String[] workLoadIds = new String[]{workLoadId};
			builder.appendSql(" and fid not in (");
			builder.appendParam(workLoadIds);
			builder.appendSql(" )");
			
		}
		IRowSet rs = builder.executeQuery();
		BigDecimal workLoad = FDCHelper.ZERO;
		try {
			while(rs.next()){
				workLoad = rs.getBigDecimal("workLoad");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return workLoad;
	}
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
	}
	protected DAPTransformResult _generateVoucher(Context ctx,
			IObjectCollection sourceBillCollection, IObjectPK botMappingPK)
			throws BOSException, EASBizException {
		Set ids=new HashSet();
		for(Iterator iter=sourceBillCollection.iterator();iter.hasNext();){
			FDCBillInfo info=(FDCBillInfo)iter.next();
			ids.add(info.getId().toString());
		}
		checkBeforeCreateVoucherEntrys(ctx,ids);
		if(false){
			return null;
		}
		createVoucherEntrys(ctx, ids);
		//重新取一次sourceBillCollection以保证分录id带出
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
		view.getSelector().add("*");
		view.getSelector().add("costVoucherEntrys.*");
		view.getSelector().add("costVoucherEntrys.accountView.*");
		view.getSelector().add("costVoucherEntrys.currency.*");
		view.getSelector().add("costVoucherEntrys.supplier.*");
		view.getSelector().add("costVoucherEntrys.curProject.id");
		view.getSelector().add("payVoucherEntrys.*");
		view.getSelector().add("payVoucherEntrys.accountView.*");
		view.getSelector().add("payVoucherEntrys.currency.*");
		view.getSelector().add("payVoucherEntrys.supplier.*");
		view.getSelector().add("payVoucherEntrys.curProject.id");
		sourceBillCollection=WorkLoadConfirmBillFactory.getLocalInstance(ctx).getCoreBillBaseCollection(view);
		DAPTransformResult voucher = super._generateVoucher(ctx,sourceBillCollection,botMappingPK);
		return voucher;
	}
	
	
	private void createVoucherEntrys(Context ctx,Set ids) throws EASBizException, BOSException{
		Map _NotGet = null;
		Map _Flow   = null;
		Map _Proceeding = null;
		String companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.projectStatus.id");
		view.getSelector().add("costVoucherEntrys.id");
		view.getSelector().add("payVoucherEntrys.id");
		filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
		view.setFilter(filter);
		Set curProjectIds = new HashSet();
		IObjectCollection coll = WorkLoadConfirmBillFactory.getLocalInstance(ctx).getCoreBillBaseCollection(view);
		/***
		 * 对已经同步过分录的付款单做缓存
		 */
		Map hasSynch = new HashMap();
		for(Iterator it=coll.iterator();it.hasNext();){
			WorkLoadConfirmBillInfo info = (WorkLoadConfirmBillInfo)it.next();	
			curProjectIds.add(info.getCurProject().getId().toString());
			String projectStatusId = info.getCurProject().getProjectStatus().getId().toString();
			if(info.getCostVoucherEntrys().size()>0||info.getPayVoucherEntrys().size()>0)
				hasSynch.put(info.getId().toString(),Boolean.TRUE);
			/***
			 * 项目未获取
			 */
			if(ProjectStatusInfo.notGetID.equals(projectStatusId)){
				if (_NotGet == null)
					_NotGet = new HashMap();
				_NotGet.put(info.getId().toString(), info);
				
			}
			/***
			 * 项目流失
			 */
			else if(ProjectStatusInfo.flowID.equals(projectStatusId)){
				if (_Flow == null)
					_Flow = new HashMap();
				_Flow.put(info.getId().toString(), info);
			}
			/***
			 * 其他都和进行中一样
			 * 8. 全部竣工结算
			 * 		与项目进行中同
			 * 9. 工程项目关闭
			 * 		工程项目关闭后只能进行财务类的付款，凭证生成与项目进行中同
			 */
			else 
//				if(ProjectStatusInfo.proceedingID.equals(projectStatusId)
//					||ProjectStatusInfo.settleID.equals(projectStatusId)
//					||ProjectStatusInfo.closeID.equals(projectStatusId)
//					)
			{
				if(_Proceeding==null)
					_Proceeding = new HashMap();
				_Proceeding.put(info.getId().toString(),info);
			}
//			else{
//				// can't arrive
//				throw new ContractException(
//						ContractException.CANNOTCREATVOUCHERFORPAYMENT);
//			}
		}
		IFDCVoucherEntryCreator creator = null;
		Map param = new HashMap();
		param.put("curProjectIds",curProjectIds);
		param.put("paymentBillIds",ids);
		HashMap systemParams =  FDCUtils.getDefaultFDCParam(ctx,companyId);

		param.put("hasSynchBills",hasSynch);
		if(systemParams.get(FDCConstants.FDC_PARAM_FINACIAL)!=null&&Boolean.valueOf(systemParams.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue()){
			/***
			 * 复杂模式
			 */
			param.put("financial",FDCConstants.FDC_PARAM_FINACIAL);
			param.put("financialExtend",Boolean.FALSE);
		}
		else if(systemParams.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL)!=null&&Boolean.valueOf(systemParams.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL).toString()).booleanValue()){
			/***
			 * 简单模式
			 */
			param.put("financial",FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
			param.put("financialExtend",Boolean.valueOf(systemParams.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND).toString()));
		}
		else{
			//没有启用财务成本一体化
			throw new ContractException(
					ContractException.NOTOPENFINACIAL);
		}
		
		initAccountMap(ctx,param);
		param.put("isSeparate", Boolean.TRUE);
		param.put("isWorkLoadVoucher", Boolean.TRUE);
		if (_NotGet != null && _NotGet.size() > 0) {
			creator = VoucherCreatorFactory.getWorkLoadVoucherCreator(ctx, ProjectStatusInfo.notGetID);
			param.put("paymentBills", _NotGet);
			creator.createEntrys(param);
		}
		if (_Flow != null && _Flow.size() > 0) {
			creator = VoucherCreatorFactory.getWorkLoadVoucherCreator(ctx, ProjectStatusInfo.flowID);
			param.put("paymentBills", _Flow);
			creator.createEntrys(param);
		}
		if (_Proceeding != null && _Proceeding.size() > 0) {
			creator = VoucherCreatorFactory.getWorkLoadVoucherCreator(ctx, ProjectStatusInfo.proceedingID);
			param.put("paymentBills", _Proceeding);
			creator.createEntrys(param);
		}
	}
	/**
	 * 检查是否允许生成凭证
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkBeforeCreateVoucherEntrys(Context ctx, Set ids) throws BOSException, EASBizException {
		//审批状态，未拆分，作废了，非成本类等
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct bill.fid from T_FNC_WorkLoadConfirmBill bill inner join T_FNC_PaymentSplit split on split.fworkLoadBillId=bill.fid ");
		builder.appendSql(" where bill.fstate=? and split.fstate<>? and ");
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		builder.appendParam("bill.fid", ids.toArray());
		IRowSet rowSet=builder.executeQuery();
		if(rowSet.size()!=ids.size()){
			throw new EASBizException(new NumericExceptionSubItem("100","存在未审批状态或者未拆分的工程量确认单"));
		}
		
	}
	
	/***
	 * 一体化科目设置
	 * 获取扣款类型对应的科目
	 * 以及成本科目对应的会计科目【通过工程项目过滤】
	 * @throws EASBizException 
	 * 
	 */
	private static void initAccountMap(Context ctx,Map param) throws BOSException, EASBizException {
		Set curProjectIds = (Set)param.get("curProjectIds");
		
		
		//违约对应的科目设置
		EntityViewInfo view= new EntityViewInfo();
		FilterInfo filter   = new FilterInfo();
		view.getSelector().add("*");
		filter.getFilterItems().add(new FilterItemInfo("company.id",ContextUtil.getCurrentFIUnit(ctx).getId().toString()));
		view.setFilter(filter);
		BeforeAccountViewCollection beforeCol=BeforeAccountViewFactory.getLocalInstance(ctx).getBeforeAccountViewCollection(view);
		
		if(beforeCol==null || beforeCol.size()==0){
			//没有设置一体化科目设置
			throw new ContractException(
					ContractException.CANNOTFINDBEFOREACCOUNTVIEW);
		}
		BeforeAccountViewInfo beforeAccountViewInfo =beforeCol.get(0); 

		/***
		 * 一个付款拆分，可以拆分到多个工程项目下的成本科目
		 */
		Set paymentBillIds = (Set)param.get("paymentBillIds");
		Set costAccountIds = new HashSet();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(" select distinct acct.fid,acct.fcurProject from T_FNC_PaymentSplit split ");
		builder.appendSql(" inner join T_FNC_PaymentSplitEntry entry on entry.fparentid=split.fid ");
		builder.appendSql(" inner join T_FDC_CostAccount acct on entry.fcostaccountid=acct.fid ");
		builder.appendSql(" where ");
		builder.appendParam("split.fworkloadbillid", paymentBillIds.toArray());
		IRowSet rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				curProjectIds.add(rowSet.getString("fcurProject"));
				costAccountIds.add(rowSet.getString("fid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		
		//使用优化的方案  by sxhong 2009-07-09 16:48:39
		Map costAccountWithAccountMap=CostAccountWithAccountHelper.getCostAcctWithAcctMapByCostAccountIds(ctx, costAccountIds);
		param.put("deductAccountMap",new HashMap());
		param.put("beforeAccountViewInfo",beforeAccountViewInfo);
		param.put("costAccountWithAccountMap",costAccountWithAccountMap);
	}
	
	protected void _reverseSave(Context ctx, IObjectPK srcBillPK,
			IObjectValue srcBillVO, BOTBillOperStateEnum bOTBillOperStateEnum,
			IObjectValue bOTRelationInfo) throws BOSException, EASBizException {
		WorkLoadConfirmBillInfo info=(WorkLoadConfirmBillInfo)srcBillVO;

		BOTRelationInfo botRelation = (BOTRelationInfo) bOTRelationInfo;
		
		if (new VoucherInfo().getBOSType().toString().equals(
				botRelation.getDestEntityID())) {
			info.setId(BOSUuid.read(srcBillPK.toString()));
			if (BOTBillOperStateEnum.ADDNEW.equals(bOTBillOperStateEnum)) {
				info.setFiVouchered(true);
				
				// payBillInfo.setBillStatus(BillStatusEnum.VOUCHERED);
				VoucherInfo voucherInfo = new VoucherInfo();
				voucherInfo.setId(BOSUuid.read(botRelation.getDestObjectID()));
				info.setVoucher(voucherInfo);


			} else if (BOTBillOperStateEnum.DELETE.equals(bOTBillOperStateEnum)) {
				info.setFiVouchered(false);
				info.setVoucher(null);
				//如果拆分没有作废过的话，则允许删除拆分，重新生成凭证
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.appendSql("select 1 from T_fnc_paymentsplit where fworkloadbillid=? and fstate=?");
				builder.addParam(info.getId().toString());
				builder.addParam(FDCBillStateEnum.INVALID_VALUE);
				if(!builder.isExist()){
/*					builder.clear();
					builder.appendSql("delete from T_FNC_WorkLoadCostVoucherEntry where fparentid=?");
					builder.addParam(info.getId().toString());
					builder.execute();
					builder.clear();
					builder.appendSql("delete from T_FNC_WorkLoadPayVoucherEntry where fparentid=?");
					builder.addParam(info.getId().toString());
					builder.execute();*/
					//基类方法将更新字段，不需要在执行sql了 by sxhong 2009-07-31 14:30:09
					info.getCostVoucherEntrys().clear();
					info.getPayVoucherEntrys().clear();
					builder.clear();
					builder.appendSql("update T_FNC_PaymentSplit set fvoucherRefer=null,fvoucherReferId=null where fworkloadBillId=?");
					builder.addParam(info.getId().toString());
					builder.execute();
				}
			}
		}
		
		//基类方法将更新字段
		super._reverseSave(ctx, srcBillPK, srcBillVO, bOTBillOperStateEnum,
				bOTRelationInfo);
	}
	
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		//拆分的控制已移到反审批里面
		super._delete(ctx, arrayPK);
		Set fillBillIds = new HashSet();
		for(int i=0;i<arrayPK.length;i++){
			fillBillIds.add(arrayPK[i].toString());
		}
		fillBillIds.remove(null);
		if(fillBillIds.size() > 0){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("update T_FPM_ProjectFillBill set FWorkLoadBillId=null where  ");
			builder.appendParam("FWorkLoadBillId",fillBillIds.toArray());
			builder.executeUpdate();
		}
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		super._delete(ctx, new IObjectPK[]{pk});
//		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		builder.appendSql("update T_FPM_ProjectFillBill set FWorkLoadBillId=null where  ");
//		builder.appendParam("FWorkLoadBillId",pk.toString());
//		builder.executeUpdate();
	}
	
//	基类方法中取不到工程项目的成本中心，重写FDCBillControllerBean的方法
	protected void initProject(Context ctx, Map paramMap, Map initMap)
			throws EASBizException, BOSException {
//		super.initProject(ctx, paramMap, initMap);
		String projectId = (String) paramMap.get("projectId");
		CurProjectInfo curProjectInfo = null;
		if(paramMap.get("contractBillId")!=null) {
			//合同单据
			String contractBillId = (String)paramMap.get("contractBillId");
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.codingNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.landDeveloper.number");
			selector.add("curProject.landDeveloper.name");
			selector.add("curProject.costCenter.number");
			selector.add("curProject.costCenter.name");
			selector.add("curProject.costCenter.displayName");
			selector.add("curProject.costCenter.longNumber");
			selector.add("respDept.number");
			selector.add("respDept.name");
			selector.add("partB.number");
			selector.add("partB.name");
			//工程量查询时paramMap中参数contractBillId 为工程量确认单ID，需要做判断
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();
			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
				getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//工程项目
				projectId = (String) contractBill.getCurProject().getId().toString();
			}	
			
		}
		if(projectId != null) {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("landDeveloper.number");
			selector.add("landDeveloper.name");
			selector.add("name");
			selector.add("number");
			selector.add("longNumber");
			selector.add("codingNumber");
			selector.add("displayName");
			selector.add("parent.id");
			selector.add("fullOrgUnit.name");
			selector.add("costCenter.number");
			selector.add("costCenter.name");
			selector.add("costCenter.displayName");
			selector.add("costCenter.longNumber");
			selector.add("CU.name");
			selector.add("CU.number");
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),selector);
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
	}

//暂时写到这个类中，以后有时间迁移到ProjectFillBill中
	protected Map _getConPrjFillBill(Context ctx, Set prjFillBillIds)throws BOSException, EASBizException {
		Map map = new HashMap();
		Map accMap = new HashMap();
		Set wbsIds = new HashSet();
		if(prjFillBillIds != null && prjFillBillIds.size() > 0){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			filter.getFilterItems().add(new FilterItemInfo("id",prjFillBillIds,CompareType.INCLUDE));
			sic.add("*");
			sic.add("entries.qty");
			sic.add("entries.percent");
			sic.add("entries.description");
			sic.add("entries.task.id");
			sic.add("entries.task.wbs.id");
			sic.add("entries.task.name");
			sic.add("entries.task.number");
			view.setFilter(filter);
			view.setSelector(sic);
			ProjectFillBillCollection fillBillCol = ProjectFillBillFactory.getLocalInstance(ctx).getProjectFillBillCollection(view);
			for(int i = 0;i<fillBillCol.size();i++){
				ProjectFillBillInfo fillBillInfo = fillBillCol.get(i);
				for(int j = 0;j<fillBillInfo.getEntries().size();j++){
					ProjectFillBillEntryInfo entryInfo = fillBillInfo.getEntries().get(j);
					if(entryInfo.getTask() != null){
						wbsIds.add(entryInfo.getTask().getWbs().getId().toString());
					}
				}
			}
			map.put("fillBillCol", fillBillCol);
			accMap = ProjectFillBillFactory.getLocalInstance(ctx).getAccValues(wbsIds);
			map.put("accValues", accMap);
		}
		return map;
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		WorkLoadConfirmBillInfo billInfo = (WorkLoadConfirmBillInfo) model;
		Set fillBillIds = new HashSet();
		for(int i=0;i<billInfo.getPrjFillBillEntry().size();i++){
			WorkLoadPrjBillEntryInfo entryInfo = billInfo.getPrjFillBillEntry().get(i);
			if(entryInfo != null && entryInfo.getId() != null ){
				fillBillIds.add(entryInfo.getPrjFillBill().getId().toString());
			}
		}
		fillBillIds.remove(null);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(billInfo.getId() != null){
			builder.appendSql("delete from T_FNC_WorkLoadPrjBillEntry where FParentID=?");
			builder.addParam(billInfo.getId().toString());
		}
		IObjectPK pk = super._submit(ctx, billInfo);
		builder.clear();
		builder.appendSql("update T_FPM_ProjectFillBill set FWorkLoadBillId=null where FWorkLoadBillId=?");
		builder.addParam(pk.toString());
		builder.executeUpdate();
		if(fillBillIds.size() > 0){
			builder.clear();
			builder.appendSql("update T_FPM_ProjectFillBill set FWorkLoadBillId=? where  ");
			builder.addParam(pk.toString());
			builder.appendParam("FID",fillBillIds.toArray());
			builder.executeUpdate();
		}
		return pk;
	}

	protected Map _getRefWorkAmount(Context ctx, Map paramMap)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		String contractId = null;
		if(paramMap.get("contractId")!= null){
			contractId = (String) paramMap.get("contractId");
		}
		if(StringUtils.isEmpty(contractId)){
         	throw new EASBizException(new NumericExceptionSubItem("999", "参数传递有误!")) ;
         }
		/*获取当前合同与任务关联的信息*/
         StringBuffer str = new StringBuffer();
         str.append("select task.fid from t_sch_fdcscheduletask  task inner join t_sch_fdcschedule sch on task.fscheduleid = sch.fid where fwbsid in (select  fwbsid from T_SCH_ContractAndTaskRelEntry where FParentID=(select fid from T_SCH_ContractAndTaskRel where fcontractid ='");
         str.append(contractId);
         str.append("')) and sch.fislatestver = 1");
         logger.info(str.toString());
         /*获取已经填报过的分录*/
         Set set = new HashSet();
         FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
         builder.appendSql("select entry.fworkamountentryid from T_FNC_WorkLoadConBillRelTask entry inner join t_fnc_workloadconfirmbill bill on entry.fparentid = bill.fid where bill.fstate = '4AUDITTED' and entry.fcontractbillId=? ");
         builder.addParam(contractId);
         RowSet rs = builder.executeQuery();
         try {
			while(rs.next()){
			 	set.add(rs.getString(1));
			 }
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
         
         /*取出任务填报工程量的填报信息*/
         EntityViewInfo viewInfo = new EntityViewInfo();
         SelectorItemCollection sic = new SelectorItemCollection();
         sic.add(new SelectorItemInfo("*"));
         sic.add(new SelectorItemInfo("parent.id"));
         sic.add(new SelectorItemInfo("parent.state"));
         sic.add(new SelectorItemInfo("parent.bizDate"));
         sic.add(new SelectorItemInfo("task.name"));
         sic.add(new SelectorItemInfo("task.number"));
         sic.add(new SelectorItemInfo("parent.number"));
         viewInfo.setSelector(sic);
         FilterInfo filter = new FilterInfo();
         filter.getFilterItems().add(new FilterItemInfo("task.id",str.toString(),CompareType.INNER));
         filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED));
         if(set.size()>0){
         	filter.getFilterItems().add(new FilterItemInfo("id",set,CompareType.NOTINCLUDE));
         }
         viewInfo.setFilter(filter);
         CoreBaseCollection cols = WorkAmountEntryFactory.getLocalInstance(ctx).getCollection(viewInfo);
         map.put("entryCols", cols);

         Map completeMap = new HashMap();
		 builder = new FDCSQLBuilder(ctx);
		 builder.appendSql("select sum(FConfirmAmount) amount,sum(FPercent) perc ,ftaskid from T_SCH_WorkAmountEntry entry inner join T_SCH_WorkAmountBill bill on entry.fparentid = bill.fid where bill.fstate = ? and ftaskid in (");
		 builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		 builder.appendSql("select task.fid from t_sch_fdcscheduletask  task inner join t_sch_fdcschedule sch on task.fscheduleid = sch.fid where fwbsid in (select  fwbsid from T_SCH_ContractAndTaskRelEntry where FParentID=(select fid from T_SCH_ContractAndTaskRel where fcontractid = ?");
		 builder.addParam(contractId);
		 builder.appendSql(")) and sch.fislatestver = 1");
		 builder.appendSql(") group by ftaskid");
		
		 RowSet rs1 = builder.executeQuery();
		 try {
			while(rs1.next()){
				completeMap.put(rs1.getString("ftaskid"), new BigDecimal[]{rs1.getBigDecimal("amount"),rs1.getBigDecimal("perc")});
			 }
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}
		map.put("completeMap", completeMap);
		return map;
	}

    /***
     * 保存方法重写
     * @deprecated
     */
	protected void _save(Context ctx, IObjectValue model,
			List refWorkAmountList, List willRemoveList) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._save(ctx, model);
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String [] arr = convertListToArray(willRemoveList);
		if(arr.length>0){
			builder.appendSql("delete from T_FNC_WorkLoadConBillRelTask  where ");
			builder.appendParam("fid", arr);
			builder.execute();
		}
		
	}
	
	private String[] convertListToArray(List list){
		String [] arr= new String[list.size()];
		for(int i=0;i<list.size();i++){
			arr[i] = list.get(i).toString();
		}
		return arr;
	}
}