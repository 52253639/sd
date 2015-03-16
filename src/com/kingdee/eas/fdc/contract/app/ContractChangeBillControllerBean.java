package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeBillStateEnum;
import com.kingdee.eas.fdc.contract.ConChangeBillSettAfterSignEnum;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeEntryCollection;
import com.kingdee.eas.fdc.contract.ContractChangeEntryFactory;
import com.kingdee.eas.fdc.contract.ContractChangeEntryInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeTypeEnum;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 
 * 描述:变更签证单
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractChangeBillControllerBean extends
		AbstractContractChangeBillControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractChangeBillControllerBean");

	
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
			selectors.add("orgUnit.id");
			selectors.add("CU.id");
			selectors.add("grtRate");
			selectors.add("partB.number");
			selectors.add("partB.name");
			selectors.add("curProject.id");
			selectors.add("curProject.number");
			selectors.add("curProject.name");
			selectors.add("curProject.displayName");
	
			ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
			getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selectors);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
		}
		
		return initMap;
	}
	
	protected void _disPatch(Context ctx, IObjectPK[] idSet) throws BOSException, EASBizException {
		for (int i=0;i<idSet.length;i++) {
			IObjectPK pk = (IObjectPK) idSet[i];
			ContractChangeBillInfo billInfo = new ContractChangeBillInfo();
			billInfo.setId(BOSUuid.read(pk.toString()));
			billInfo.setState(FDCBillStateEnum.ANNOUNCE);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("state");
			_updatePartial(ctx, billInfo, selector);
			ContractChangeBillInfo bill = (ContractChangeBillInfo)super._getValue(ctx,pk);
			if(bill.getChangeAudit()!=null){
				ChangeAuditBillInfo info = ChangeAuditBillFactory.getLocalInstance(ctx).
				getChangeAuditBillInfo(new ObjectUuidPK(bill.getChangeAudit().getId()));	
				info.setChangeState(ChangeBillStateEnum.Announce);
				SelectorItemCollection sele = new SelectorItemCollection();
				sele.add("changeState");
				ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, sele);
			}
		}	
		
	}

	protected void _visa(Context ctx, Set idSet) throws BOSException, EASBizException {
	}
	
    protected IObjectPK _addnew(Context ctx , IObjectValue model) throws
    BOSException , EASBizException
	{
    	IObjectPK test = super._addnew(ctx,model);
    	ContractChangeBillInfo info = (ContractChangeBillInfo)super._getValue(ctx,test);
    	if(info.getChangeAudit()!=null){
	    	EntityViewInfo vit = new EntityViewInfo();
			FilterInfo fit = new FilterInfo();
			FilterItemCollection itt = fit.getFilterItems();	
			if(info.getId()!=null){
				itt.add(new FilterItemInfo("parent.id", info.getId().toString(),CompareType.EQUALS));
				vit.setFilter(fit);
				vit.getSelector().add("seq");
				vit.getSelector().add("number");
				SorterItemInfo sortName = new SorterItemInfo("number");
	            sortName.setSortType(SortType.ASCEND);
				vit.getSorter().add(sortName);
				ContractChangeEntryCollection coll = ContractChangeEntryFactory.getLocalInstance(ctx).getContractChangeEntryCollection(vit);
				for(int j=0;j<coll.size();j++){
					ContractChangeEntryInfo entry = coll.get(j);
					entry.setSeq(j+1);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("seq");
					ContractChangeEntryFactory.getLocalInstance(ctx).updatePartial(entry, selector);
				}
			}
    	}else{
    		if(info.getJobType()!=null)
    			info.setJobTypeName(info.getJobType().getName());
    		if(info.getSpecialtyType()!=null)
    			info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    		if(info.getChangeType()!=null)
    			info.setChangeTypeName(info.getChangeType().getName());
    	}
	    return test;
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		checkBillForAudit( ctx,billId,null);
		
		super._audit(ctx, billId);
	
		//自动审批拆分单(完全拆分状态的)
		CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConChangeSplit", "FContractChangeID");
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("ContractBill.id");
		selectors.add("curProject.costCenter");
		selectors.add("isCostSplit");
		selectors.add("amount");
		selectors.add("balanceAmount");
		selectors.add("orgUnit.id");
		ContractChangeBillInfo info = (ContractChangeBillInfo)
			this.getValue(ctx,new ObjectUuidPK(billId),selectors);
		
		// 成本类：合同变更、合同结算的拆分自动引用合同拆分的成本科目、拆分比例
		if (info != null && info.isIsCostSplit()&&info.getCurProject() != null && info.getCurProject().getCostCenter() != null && info.getCurProject().getCostCenter().getId() != null) {
			boolean isImportConSplit = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_IMPORTCONSPLIT);
			if (isImportConSplit) {
				ConChangeSplitFactory.getLocalInstance(ctx).autoSplit4(billId);
			}
		}
		
		//审批时根据变更单数据更新合同执行表相关信息
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateChange(ContractExecInfosInfo.EXECINFO_AUDIT,info.getContractBill().getId().toString());
		try {
			synContractProgAmt(ctx,info,true,info.getOrgUnit().getId().toString());
		} catch (SQLException e) {
			logger.error(e);
		}
	}
	
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractChangeBill.id", billId.toString()));
		if(ContractPCSplitBillFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","存在合约规划拆分，不能进行反审批操作！"));
		}
		checkBillForUnAudit( ctx,billId,null);
		
		super._unAudit(ctx, billId);
		
		//自动反审批拆分单
		CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConChangeSplit", "FContractChangeID");
		
		//自动删除本期月结数据
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("period.id");
		selectors.add("ContractBill.id");
		selectors.add("amount");
		selectors.add("balanceAmount");
		selectors.add("orgUnit.id");
		ContractChangeBillInfo info = (ContractChangeBillInfo)this.getValue(ctx,new ObjectUuidPK(billId),selectors);
		if(info.getPeriod()!=null){
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(info.getContractBill().getId().toString(),
					info.getPeriod().getId().toString());
		}
		//反审批时根据变更单数据更新合同执行表相关信息
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateChange(ContractExecInfosInfo.EXECINFO_UNAUDIT,info.getContractBill().getId().toString());
		try {
			synContractProgAmt(ctx,info,false,info.getOrgUnit().getId().toString());
		} catch (SQLException e) {
			logger.error(e);
		}
	}
	 /**
	  * 当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（签约金额+变更金额），控制余额=控制金额-签约金额，
	  * 当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额
      * @return
      * @throws BOSException 
      * @throws EASBizException 
      * @throws SQLException 
      */
    private void synContractProgAmt(Context ctx,ContractChangeBillInfo model,boolean flag,String orgId) throws EASBizException, BOSException, SQLException{
    	BOSUuid contractBillId = model.getContractBill().getId();
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("*");
    	sic.add("programmingContract.*");
    	ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractBillId.toString()), sic);
    	ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
    	if(pcInfo == null) return;
		// 规划余额
    	BigDecimal balanceAmt = pcInfo.getBalance();
    	// 控制余额
//    	BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
    	//变更单预算本币金额
    	BigDecimal changeAmount = model.getAmount();
    	if(model.getBalanceAmount() != null){
    		changeAmount = model.getBalanceAmount();
    	}
    	//框架合约签约金额
    	BigDecimal changeAmountProg = pcInfo.getChangeAmount();
    	//差额
//    	BigDecimal otherChangeAmount = FDCHelper.ZERO;
		if(flag){
			pcInfo.setBalance(FDCHelper.subtract(balanceAmt, changeAmount));
			pcInfo.setChangeAmount(FDCHelper.add(changeAmountProg, changeAmount));
			
			ContractEstimateChangeBillFactory.getLocalInstance(ctx).sub(pcInfo, ContractEstimateChangeTypeEnum.CHANGE, changeAmount, true,orgId);
//			otherChangeAmount = FDCHelper.subtract(FDCHelper.add(changeAmountProg, changeAmount), changeAmountProg);
		}else{
			pcInfo.setBalance(FDCHelper.add(balanceAmt, changeAmount));
			pcInfo.setChangeAmount(FDCHelper.subtract(changeAmountProg, changeAmount));
//			otherChangeAmount = FDCHelper.subtract(FDCHelper.subtract(changeAmountProg, changeAmount), changeAmountProg);
		}
    	SelectorItemCollection sict = new SelectorItemCollection();
    	sict.add("balance");
    	sict.add("controlBalance");
    	sict.add("signUpAmount");
    	sict.add("changeAmount");
    	sict.add("settleAmount");
    	sict.add("srcId");
    	IProgrammingContract service = ProgrammingContractFactory
		.getLocalInstance(ctx);
    	service.updatePartial(pcInfo, sict);
    	//更新其他的合约规划版本金额
//		String progId = pcInfo.getId().toString();
//		while (progId != null) {
//			String nextVersionProgId = getNextVersionProg(ctx, progId);
//			if (nextVersionProgId != null) {
//				pcInfo = ProgrammingContractFactory.getLocalInstance(ctx)
//						.getProgrammingContractInfo(
//								new ObjectUuidPK(nextVersionProgId), sict);
//				pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(),
//						otherChangeAmount));
//				pcInfo.setChangeAmount(FDCHelper.add(pcInfo.getChangeAmount(),
//						otherChangeAmount));
//				service.updatePartial(pcInfo, sict);
//				progId = pcInfo.getId().toString();
//			} else {
//				progId = null;
//			}
//		}
    }
    
	private String getNextVersionProg(Context ctx, String nextProgId) throws BOSException, SQLException {
		String tempId = null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql(" select fid from t_con_programmingContract where  ");
		builder.appendParam("FSrcId", nextProgId);
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			tempId = rowSet.getString("fid");
		}
		return tempId;
	}
    
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	ContractChangeBillInfo info = (ContractChangeBillInfo)model;
    	if(info.getChangeType()!=null)
    		info.setChangeTypeName(info.getChangeType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	if(info.getContractBill()!=null){
    		// 变更单的合同状态
    		info.setIsConSetted(info.getContractBill().isHasSettled());
    		info.setIsCostSplit(info.getContractBill().isIsCoseSplit());
    	}
    	super._save(ctx, pk, info);
    }
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	ContractChangeBillInfo info = (ContractChangeBillInfo)model;
    	if(info.getChangeType()!=null)
    		info.setChangeTypeName(info.getChangeType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	if(info.getContractBill()!=null){
    		// 变更单的合同状态
    		info.setIsConSetted(info.getContractBill().isHasSettled());
    		info.setIsCostSplit(info.getContractBill().isIsCoseSplit());
    	}
    	return super._save(ctx, info);
    }
    
    protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	ContractChangeBillInfo info = (ContractChangeBillInfo)model;
    	if(info.getChangeType()!=null)
    		info.setChangeTypeName(info.getChangeType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	if(info.getContractBill()!=null){
    		//变更单的合同状态
    		info.setIsConSetted(info.getContractBill().isHasSettled());
    		info.setIsCostSplit(info.getContractBill().isIsCoseSplit());
    	}
    	super._submit(ctx, pk, info);
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model)
	throws BOSException, EASBizException {
    	ContractChangeBillInfo info = (ContractChangeBillInfo)model;
    	
		//提交前检查
		checkBillForSubmit( ctx,info);
		
    	if(info.getChangeType()!=null)
    		info.setChangeTypeName(info.getChangeType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
    	if(info.getContractBill()!=null){
    		// 变更单的合同状态
    		info.setIsConSetted(info.getContractBill().isHasSettled());
    		info.setIsCostSplit(info.getContractBill().isIsCoseSplit());
    	}
    	return super._submit(ctx, info);
    }
    
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
	throws BOSException, EASBizException {
    	ContractChangeBillInfo info = (ContractChangeBillInfo)model;
		if(info.getChangeType()!=null)
    		info.setChangeTypeName(info.getChangeType().getName());
    	if(info.getJobType()!=null)
    		info.setJobTypeName(info.getJobType().getName());
    	if(info.getSpecialtyType()!=null)
    		info.setSpecialtyTypeName(info.getSpecialtyType().getName());
		super._update(ctx, pk, info);
	}

    /**
     * 批量签证
     */
	protected boolean _visa(Context ctx, Set idSet, IObjectCollection cols) throws BOSException, EASBizException {

		//先保存签证数据
		ContractChangeBillCollection coll =(ContractChangeBillCollection) cols;		
		Iterator itr = coll.iterator();
		while (itr.hasNext()) {
			ContractChangeBillInfo billInfo = (ContractChangeBillInfo)itr.next();			
			this.save(ctx,billInfo);
		}
		
		//签证
		IObjectPK[] pks = new ObjectUuidPK[idSet.size()];
		_visa( ctx, pks );
		
		return true;
	}
	
	/**
	 * 
	 * 描述：检查名称重复
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd 创建时间：2006-8-24
	 *               <p>
	 */
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
			throws BOSException, EASBizException {
		//万科需求：此单名称可以重复
	}
	
	protected boolean isUseName() {
		return false;
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
    	ContractChangeBillInfo contractChangeBill = (ContractChangeBillInfo)model;
		
		//是否启用财务一体化		
		String comId = null;
		if( contractChangeBill.getCurProject().getFullOrgUnit()==null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(contractChangeBill.getCurProject().getId().toString()),sic);
			
			comId = curProject.getFullOrgUnit().getId().toString();
		}else{
			comId = contractChangeBill.getCurProject().getFullOrgUnit().getId().toString();
		}
		
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		
		if(isInCore&&contractChangeBill.isIsCostSplit()){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractChangeBill.getCurProject().getId().toString(),true);
			if(contractChangeBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
			
			if(contractChangeBill.getContractBill()!=null){
		    	// 此过滤为详细信息定义
		        SelectorItemCollection sic = new SelectorItemCollection();
		        sic.add(new SelectorItemInfo("period.beginDate"));	

		        ContractBillInfo contractBillInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractChangeBill.getContractBill().getId().toString()),sic);
				//检查功能是否已经结束初始化

				if(contractChangeBill.getPeriod()!=null && 
						contractChangeBill.getPeriod().getBeginDate().before(contractBillInfo.getPeriod().getBeginDate())){
					//单据期间不能在合同的当前期间之前 CNTPERIODBEFORE
					throw new ContractException(ContractException.CNTPERIODBEFORECON);
				}
			}
		}
	}
	
	/**
	 * 审核校验
	 */
	private void checkBillForAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
    
		ContractChangeBillInfo model = (ContractChangeBillInfo)billInfo;

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getContractChangeBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
        }

        //检查功能是否已经结束初始化
		String comId = model.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = model.getCurProject().getId().toString();	
			//成本已经月结
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(model.getPeriod().getBeginDate().after(bookedPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_AFTERPERIOD,new Object[]{model.getNumber()});//model
			}
			
			PeriodInfo finPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,false);
			if(bookedPeriod.getBeginDate().after(finPeriod.getEndDate())){
				throw new  ContractException(ContractException.AUD_FINNOTCLOSE,new Object[]{model.getNumber()});
			}	
		}
	}
	/**
	 * 反审核校验
	 */
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,FDCBillInfo billInfo )throws BOSException, EASBizException {
		ContractChangeBillInfo model = (ContractChangeBillInfo)billInfo;

        if(model==null || model.getCurProject()==null ||model.getCurProject().getFullOrgUnit()==null){
        	model= this.getContractChangeBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
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
		}
	}

	/**
	 * 	变更结算
	 */
	protected void _settle(Context ctx, IObjectPK pk, IObjectValue changeBill) throws BOSException, EASBizException {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("balanceAmount");
		selector.add("hasSettled");
		selector.add("settleTime");
		
		this._updatePartial(ctx,changeBill,selector);
		
	}
	
	/**
	 * 反下发
	 */
	protected void _unDispatch(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
		ContractChangeBillInfo billInfo = new ContractChangeBillInfo();
		billInfo.setId(BOSUuid.read(pk.toString()));
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		_updatePartial(ctx, billInfo, selector);
		
		//IObjectPK ipk = new ObjectUuidPK(BOSUuid.read(pk));
		selector.add("changeAudit.id");
		ContractChangeBillInfo bill = (ContractChangeBillInfo)super.getValue(ctx,pk,selector);
		if(bill.getChangeAudit()!=null){
			ChangeAuditBillInfo info = ChangeAuditBillFactory.getLocalInstance(ctx).
			getChangeAuditBillInfo(new ObjectUuidPK(bill.getChangeAudit().getId()));	
			
			info.setChangeState(ChangeBillStateEnum.Audit);
			selector = new SelectorItemCollection();
			selector.add("changeState");
			ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
		}
		
	}

	/**
	 * 反签证
	 */
	protected void _unVisa(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		ContractChangeBillInfo billInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("impleCondition");
		selector.add("disThisTime");	
		selector.add("changeAudit.id");
		selector.add("entrys.discription");
		selector.add("entrys.isAllExe");
		selector.add("entrys.isPartExe");
		selector.add("entrys.isNoExe");
		
		billInfo = (ContractChangeBillInfo)super._getValue(ctx,pk,selector);
		
		billInfo.setId(BOSUuid.read(pk.toString()));
		boolean isDispatch = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_ALLOWDISPATCH);
		if(isDispatch){
			billInfo.setState(FDCBillStateEnum.ANNOUNCE);
		}else{//中渝模式无下发
			billInfo.setState(FDCBillStateEnum.AUDITTED);
		}
		billInfo.setImpleCondition(null);
		billInfo.setImplement(0);
		billInfo.setDisThisTime(null);
		if(billInfo.getEntrys()!=null &&billInfo.getEntrys().size()>0){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("discription");
			sic.add("isAllExe");
			sic.add("isPartExe");
			sic.add("isNoExe");
			for (int i=0,count=billInfo.getEntrys().size();i<count;i++) {
				ContractChangeEntryInfo entryInfo = billInfo.getEntrys().get(i);
				entryInfo.setIsAllExe(false);
				entryInfo.setIsPartExe(false);
				entryInfo.setIsNoExe(false);
				entryInfo.setDiscription(null);
				
				ContractChangeEntryFactory.getLocalInstance(ctx).updatePartial(entryInfo,sic);
			}
			
		}

		_updatePartial(ctx, billInfo, selector);
		
		if(billInfo.getChangeAudit()!=null){
			ChangeAuditBillInfo info = ChangeAuditBillFactory.getLocalInstance(ctx).
			getChangeAuditBillInfo(new ObjectUuidPK(billInfo.getChangeAudit().getId()));
			
			if(isDispatch){
				info.setChangeState(ChangeBillStateEnum.Announce);
			}else{//中渝模式无下发
				info.setChangeState(ChangeBillStateEnum.Audit);
			}
			
			selector = new SelectorItemCollection();
			selector.add("changeState");
			ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, selector);
		}
		
	}

	protected void _visa(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for (int i=0;i<pks.length;i++) {
			IObjectPK pk = (IObjectPK) pks[i];
			ContractChangeBillInfo billInfo = new ContractChangeBillInfo();
			billInfo.setId(BOSUuid.read(pk.toString()));
			billInfo.setState(FDCBillStateEnum.VISA);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("state");
			_updatePartial(ctx, billInfo, selector);
			ContractChangeBillInfo bill = (ContractChangeBillInfo)super._getValue(ctx,pk);
			if(bill.getChangeAudit()!=null){
				ChangeAuditBillInfo info = ChangeAuditBillFactory.getLocalInstance(ctx).
				getChangeAuditBillInfo(new ObjectUuidPK(bill.getChangeAudit().getId()));
				info.setChangeState(ChangeBillStateEnum.Visa);
				SelectorItemCollection sele = new SelectorItemCollection();
				sele.add("changeState");
				ChangeAuditBillFactory.getLocalInstance(ctx).updatePartial(info, sele);
			}
		}	
	}

	protected boolean _visa(Context ctx, IObjectPK[] idSet, IObjectCollection cols) throws BOSException, EASBizException {
		//先保存签证数据，如执行情况、本次执行说明、未执行说明等信息
		ContractChangeBillCollection coll =(ContractChangeBillCollection) cols;		
		Iterator itr = coll.iterator();
		while (itr.hasNext()) {
			ContractChangeBillInfo billInfo = (ContractChangeBillInfo)itr.next();			
			this.save(ctx,billInfo);
		}
		
		//签证		
		_visa( ctx, idSet );
		return true;
	}
	/**
	 * 在工作流中设置变更指令单的状态为"结算未审批"   by Cassiel_peng 2009-8-19 
	 */
	protected void _submitForWF(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		
		ContractChangeBillInfo billInfo = (ContractChangeBillInfo)model;
		billInfo.setForSettAfterSign(ConChangeBillSettAfterSignEnum.UnAuditAfterSign);
		billInfo.setSettAuditAmt(billInfo.getOriBalanceAmount());
		billInfo.setSettAuditExRate(billInfo.getExRate());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("forSettAfterSign");
		selector.add("settAuditAmt");
		selector.add("settAuditExRate");
		selector.add("hasSettled");
		ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
	}
	
	/**
	 * 在工作流中设置变更指令单的状态为"结算已审批"   by Cassiel_peng 2009-8-19
	 */
	protected void _setSettAuditedForWF(Context ctx, BOSUuid pk)
			throws BOSException, EASBizException {
		ContractChangeBillInfo billInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("id");
		selector.add("state");
		selector.add("oriBalanceAmount");
		selector.add("balanceAmount");
		selector.add("exRate");
		selector.add("forSettAfterSign");
		selector.add("settAuditAmt");
		selector.add("settAuditExRate");
		
		selector.add("contractBill.id");
		selector.add("contractBill.isCoseSplit");
		billInfo=(ContractChangeBillInfo)super.getValue(ctx, new ObjectUuidPK(pk),selector);
		//在工作流第一个审批节点触发的时候变更指令单的原币金额就已经存到FSettAuditAmt字段中,待审批全都通过之后才反写到 FOrgBlanceAmount字段中
		billInfo.setOriBalanceAmount(billInfo.getSettAuditAmt());
		billInfo.setExRate(billInfo.getSettAuditExRate());
		billInfo.setBalanceAmount(FDCHelper.multiply(billInfo.getSettAuditAmt(), billInfo.getSettAuditExRate()));
		//并且要修改单据的状态
		billInfo.setForSettAfterSign(ConChangeBillSettAfterSignEnum.AuditedAfterSign);
		billInfo.setHasSettled(true);
		billInfo.setSettleTime(DateTimeUtils.truncateDate(new Date()));
//		ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
		//全部审批通过,变更结算时根据变更单数据更新合同执行表相关信息并且根据结算金额重新拆分
		ConChangeSplitFactory.getLocalInstance(ctx).changeSettle(billInfo);
		
	}

	/**
	 * 在工作流中设置变更指令单的状态为"结算审批中"   by Cassiel_peng 2009-8-19
	 */
	protected void _setSettAuttingForWF(Context ctx, BOSUuid pk)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		ContractChangeBillInfo billInfo = null;
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("forSettAfterSign");
		billInfo=(ContractChangeBillInfo)super.getValue(ctx, new ObjectUuidPK(pk),selector);
		billInfo.setForSettAfterSign(ConChangeBillSettAfterSignEnum.AudittingAfterSign);
		ContractChangeBillFactory.getLocalInstance(ctx).updatePartial(billInfo, selector);
	}

	protected void _confirmExecute(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}

	protected void _costChangeSplit(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}

	protected void _nonCostChangeSplit(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}

	protected void _changeSettle(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}
	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		super._delete(ctx, arrayPK);
		//删除后，下发单位分录指令单ID置空
		if(arrayPK==null||arrayPK.length==0){
			return;
		}
		String[] pk = new String[arrayPK.length];
		for(int i=0;i<arrayPK.length;i++){
			pk[i]= arrayPK[i].toString();
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("update T_CON_ChangeSupplierEntry set FContractChangeID=null where ");
		builder.appendParam("FContractChangeID", pk);
		builder.executeUpdate();
	}
}