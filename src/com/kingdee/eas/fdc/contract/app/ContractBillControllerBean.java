package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.ResultSet;
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
import com.kingdee.bos.ctrl.swing.StringUtils;
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
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.commonquery.SorterSignEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataCollection;
import com.kingdee.eas.fdc.contract.ContractBaseDataFactory;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeSettleBillFactory;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractEstimateChangeTypeEnum;
import com.kingdee.eas.fdc.contract.ContractEvaluationFactory;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractExecInfosFactory;
import com.kingdee.eas.fdc.contract.ContractExecInfosInfo;
import com.kingdee.eas.fdc.contract.ContractExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractPCSplitBillFactory;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractUtil;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.IContractEstimateChangeBill;
import com.kingdee.eas.fdc.contract.IContractSettlementBill;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.ContractOutPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.fdc.finance.CostClosePeriodFacadeFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fdc.invite.app.ContractBillAcceptanceLetterHelper;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.UuidException;

/**
 * 
 * 描述:合同单据
 * @author liupd  date:2006-10-13 <p>
 * @version EAS5.1.3
 */
public class ContractBillControllerBean extends
		AbstractContractBillControllerBean {
	
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.contract.app.ContractBillControllerBean");

	
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk,ContractBillInfo.getDefaultSelector());
    }
    protected void trimName(FDCBillInfo billInfo) {
		if(billInfo.getName() != null) {
			billInfo.setName(billInfo.getName().trim());
		}
	}
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		ContractBillInfo info = (ContractBillInfo)model;
		info.setSrcAmount(info.getAmount());
		super._save(ctx, pk, model);
	}
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		ContractBillInfo info = (ContractBillInfo)model;
		info.setSrcAmount(info.getAmount());
		super._submit(ctx, pk, model);
	}
	public IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	if(((ContractBillInfo)model).getBail()!=null){
    		ContractBailFactory.getLocalInstance(ctx).save(((ContractBillInfo)model).getBail());
    	}
    	
    	ContractBillInfo info = (ContractBillInfo)model;
    	
    	if(info.getId() != null&&_exists(ctx, new ObjectUuidPK(info.getId()))){
    		ContractBillInfo oldInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(info.getId()));
    		if(!oldInfo.getState().equals(FDCBillStateEnum.SAVED)){
    			if (!FDCUtils.isRunningWorkflow(ctx, info.getId().toString())
    					|| !FDCBillStateEnum.SUBMITTED.equals(oldInfo.getState())) //工作流中提交状态也可以修改保存 By Owen_wen 2011-03-28
    				throw new ContractException(ContractException.WITHMSG,new Object[]{"\n当前单据最新状态为"+oldInfo.getState()+",可能是被 其他用户所更改，不能保存！"});
    		}
    		info.setState(oldInfo.getState());
    	}
    	info.setCodingNumber(info.getNumber());
    	
    	/**
		 * 如果设置了不允许断号，但是在取号以后，由于名称重复而报错<br>
		 * 断号不会回滚，导致再次提交时，就断号了。<br>
		 * 所以在保存前先校验Name <br>
		 * add by emanon
		 */
		checkNameDup(ctx, info);
		info.setSrcAmount(info.getAmount());
		return super._save(ctx,info);
    }
    
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ContractBillInfo contractBillInfo = ((ContractBillInfo) model);
		//checkBillForSubmit( ctx, FDCBillInfo);
		if(contractBillInfo.getId()!=null){
			boolean canSubMit = _checkCanSubmit(ctx, contractBillInfo.getId().toString());
			if(!canSubMit){
				String msg = "单据状态已经在审核中或者已审核，不能再提交";
				throw new ContractException(ContractException.WITHMSG,new Object[]{msg});
			}
		}
		if(contractBillInfo.getBail()!=null){
			ContractBailFactory.getLocalInstance(ctx).submit(contractBillInfo.getBail());
		}
		contractBillInfo.setCodingNumber(contractBillInfo.getNumber());

		/**
		 * 如果设置了不允许断号，但是在取号以后，由于名称重复而报错<br>
		 * 断号不会回滚，导致再次提交时，就断号了。<br>
		 * 所以在保存前先校验Name <br>
		 * add by emanon
		 */
		checkNameDup(ctx, contractBillInfo);
		
		//补充合同提交后,更新关联的预估合同变更为isUsed为true wangxin 20120331
//		if(ContractPropertyEnum.SUPPLY.equals(contractBillInfo.getContractPropert())){
//			ContractBillEntryCollection coll = contractBillInfo.getEntrys();
//			IContractEstimateChangeBill  iFaced = ContractEstimateChangeBillFactory.getLocalInstance(ctx);
//			for(int i = 0 ; i < coll.size() ;i++){
//				ContractBillEntryInfo entryInfo = coll.get(i);
//				if(entryInfo.getRowKey()!=null &&"esId".equals(entryInfo.getRowKey())){
//					ContractEstimateChangeBillInfo esInfo = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(entryInfo.getContent()));
//					esInfo.setIsUsed(true);
//					SelectorItemCollection sel = new SelectorItemCollection();
//					sel.add("isUsed");
//					iFaced.updatePartial(esInfo, sel);
//					break;
//				}
//			}
//		}
		//扣减控制金额
//		ProgrammingContractInfo proInfo = contractBillInfo.getProgrammingContract();
//		if(proInfo!=null){
//			BigDecimal balance = proInfo.getBalance();
//			balance = balance.subtract(contractBillInfo.getAmount());
//			IProgrammingContract iFaced = ProgrammingContractFactory.getLocalInstance(ctx);
//			SelectorItemCollection sel = new SelectorItemCollection();
//			sel.add("balance");
//			proInfo.setBalance(balance);
//			iFaced.updatePartial(proInfo, sel);
//		}
		contractBillInfo.setSrcAmount(contractBillInfo.getAmount());
		return super._submit(ctx, contractBillInfo);
	}

	/**
	 * 设置制单人制单时的职位信息 处理提单R101021-327
	 * 
	 * 制单人对应的是用户ID 用户(t_pm_user)可以找到对应的职员（t_bd_person）
	 * 职员可以找到对应的职员职业信息（T_HR_PersonPosition），这张表就是用户的主要职位
	 * 职业信息（T_HR_PersonPosition） 主要职位可以找到 t_org_position(职位)
	 * 
	 * @author owen_wen 201-07-20
	 * @param contractBillInfo
	 */
	private void setCreatorPosition(Context ctx, ContractBillInfo contractBillInfo) {
		String creatorId = ctx.getCaller().toString();
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select p.fid from t_org_position p \n");
		builder.appendSql("inner join T_HR_PersonPosition pp on pp.FPrimaryPositionID = p.fid \n");
		builder.appendSql("inner join T_bd_person ps on ps.fid = pp.FPersonID \n");
		builder.appendSql("inner join t_pm_user u on u.FPersonId = ps.fid \n");
		builder.appendSql("where u.fid = ");
		builder.appendParam(creatorId);
		
		String positionId = "";
		try {
			IRowSet rs = builder.executeQuery(ctx);
			if (rs.size() > 0) {
				rs.next();
				positionId = rs.getString("fid");
			}
		} catch (BOSException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		
		// 如果制单人没有绑定职位信息，直接返回 Added By Owen_wen 2011-07-28
		if (StringUtils.isEmpty(positionId))
			return;
		
		PositionInfo positionInfo = new PositionInfo();
		positionInfo.setId(BOSUuid.read(positionId));
		contractBillInfo.setCreatorPosition(positionInfo);
	}

    
    private SelectorItemCollection getSic(){
		// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("splitState"));        
        sic.add(new SelectorItemInfo("isCoseSplit"));
        sic.add(new SelectorItemInfo("isAmtWithoutCost"));
        sic.add(new SelectorItemInfo("period.id"));
        sic.add(new SelectorItemInfo("period.beginDate"));	
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));	
        sic.add(new SelectorItemInfo("conChargeType.id"));
     // Dean_zhu 2011-1-5
		sic.add(new SelectorItemInfo("hasSettled"));
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("*"));
		sic.add("contractPropert");
        return sic;
    }
    
	private SelectorItemCollection getSettleSic() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("contractBill.*"));

		return sic;
	}

	private SelectorItemCollection getSeletor() {
		// 此过滤为详细信息定义
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("suppEntry.*"));

		return sic;
	}
    
	//提交时校验单据期间不能在工程项目的当前期间之前
	protected void checkBillForSubmit(Context ctx, IObjectValue model)throws BOSException, EASBizException {
		
		//不能落于当前成本期间之前
		ContractBillInfo contractBill = (ContractBillInfo)model;
        if(model==null || contractBill.getCurProject()==null ||contractBill.getCurProject().getFullOrgUnit()==null){
        	model= this.getContractBillInfo(ctx,new ObjectUuidPK(contractBill.toString()),getSic());
        }
		//是否启用财务一体化
		String comId = contractBill.getCurProject().getFullOrgUnit().getId().toString();
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		//因为月结只检查了成本类的，所以此处不控制财务类的
		if(isInCore&&contractBill.isIsCoseSplit()){
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,contractBill.getCurProject().getId().toString(),true);
			if(contractBill.getPeriod().getBeginDate().before(bookedPeriod.getBeginDate())){
				//单据期间不能在工程项目的当前期间之前 CNTPERIODBEFORE
				throw new ContractException(ContractException.CNTPERIODBEFORE);
			}
		}
	}
	
	//
	protected void checkBillForAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException {

        ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new ObjectUuidPK(billId.toString()),getSic());
		//检查功能是否已经结束初始化
		String comId = contractBillInfo.getCurProject().getFullOrgUnit().getId().toString();
			
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,contractBillInfo.getOrgUnit().getId().toString());		
		boolean splitBeforeAudit = false;
//		String costCenterId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
//			splitBeforeAudit = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
			//用下面一句代码，从param里边直接取出来的参数值不对劲，用上边一句代码重新取吧.
			splitBeforeAudit =  Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
		}
		//该合同已经还没有拆分，不能审核
		if(splitBeforeAudit && contractBillInfo.isIsCoseSplit() &&  
				!contractBillInfo.isIsAmtWithoutCost() && !CostSplitStateEnum.ALLSPLIT.equals(contractBillInfo.getSplitState())){
//			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
//			SysUtil.abort();
			throw new ContractException(ContractException.NOSPLITFORAUDIT);
		}
		
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = contractBillInfo.getCurProject().getId().toString();
			//没有结束初始化
			if(!ProjectPeriodStatusUtil._isClosed(ctx,curProject)){
				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.ISNOT_INIT,new Object[]{contractBillInfo.getCurProject().getDisplayName()});
			}			
			//不再当前期间，不能审核
		}
		//单据审批时预算控制
		//根据参数是否显示合同费用项目
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_CHARGETYPE);
        if (isShowCharge && contractBillInfo.getConChargeType() != null) {
           invokeAuditBudgetCtrl(ctx,contractBillInfo);
        }
	}
	
	protected void checkBillForUnAudit(Context ctx, BOSUuid billId)throws BOSException, EASBizException {
    	// 此过滤为详细信息定义
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.displayName"));
        sic.add(new SelectorItemInfo("period.beginDate"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
        sic.add(new SelectorItemInfo("conChargeType.id"));
        
        ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,new ObjectUuidPK(billId.toString()),sic);

        //检查是否已经拆分
        HashMap param = FDCUtils.getDefaultFDCParam(ctx,contractBillInfo.getOrgUnit().getId().toString());	
		boolean splitBeforeAudit = false; 
//		String costCenterId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
//			splitBeforeAudit = FDCUtils.getDefaultFDCParamByKey(ctx, costCenterId, FDCConstants.FDC_PARAM_SPLITBFAUDIT);
			//用下面一句代码，从param里边直接取出来的参数值不对劲，用上边一句代码重新取吧.
			splitBeforeAudit =  Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
		}
    	if(!splitBeforeAudit){
    		FilterInfo filterSett = new FilterInfo();
    		filterSett.getFilterItems().add(
    				new FilterItemInfo("contractBill.id", billId.toString()));
    		filterSett.getFilterItems().add(
    				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
    						CompareType.NOTEQUALS));
    		boolean hasSettleSplit = false;
    		if (ContractCostSplitFactory.getLocalInstance(ctx).exists(filterSett)
    				|| ConNoCostSplitFactory.getLocalInstance(ctx).exists(filterSett)) {
    			hasSettleSplit = true;
    		}
    		if (hasSettleSplit) {
    			throw new  ContractException(ContractException.HASSPLIT);
    		}
    	}
    	
    	//R110603-0148:如果存在变更审批单，则不允许反审批
    	if (ContractUtil.hasChangeAuditBill(ctx, contractBillInfo.getId())) {
    		throw new  ContractException(ContractException.HASCHANGEAUDITBILL);
    	}
    	
    	//R110603-0148如果存在变更指令单，则不允许反审批
    	if (ContractUtil.hasContractChangeBill(ctx, contractBillInfo.getId())) {
    		throw new  ContractException(ContractException.HASCONTRACTCHANGEBILL);
    	}
        
        //检查功能是否已经结束初始化
		String comId = contractBillInfo.getCurProject().getFullOrgUnit().getId().toString();
			
		//是否启用财务一体化
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		if(isInCore){
			String curProject = contractBillInfo.getCurProject().getId().toString();
			
//			if(ProjectPeriodStatusUtil._isEnd(ctx,curProject)){
//				throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{contractBillInfo.getCurProject().getDisplayName()});
//			}		
			
			//单据期间在工程项目当前期间之前，不能反审核
			PeriodInfo costPeriod = FDCUtils.getCurrentPeriod(ctx,curProject,true);
			if(contractBillInfo.getPeriod().getBeginDate().before(costPeriod.getBeginDate())){
				throw new  ContractException(ContractException.CNTPERIODBEFORE);
			}			
		}
		//2009-3-4 如果合同对应的核算项目基础资料已经被凭证引用，则不允许反审批合同
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractId", billId.toString()));
		boolean exist = ContractBaseDataFactory.getLocalInstance(ctx).exists(filter);
		if(exist){
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("number");
			view.getSelector().add("name");
			view.getSelector().add("state");
			view.getSelector().add("id");
			view.setFilter(filter);
			ContractBaseDataCollection contractBaseDataCollection = 
				ContractBaseDataFactory.getLocalInstance(ctx).getContractBaseDataCollection(view);
			if(contractBaseDataCollection.size() == 1) {
				ContractBaseDataInfo info = contractBaseDataCollection.get(0);
				String baseId = info.getId().toString();
				String sql = " select ar.fid from T_GL_VoucherAssistRecord ar  \r\n" +
						" inner join T_BD_AssistantHG hg  on ar.FAssGrpID = hg.fid   \r\n" +
						" where hg.FContractBaseDataID = ?     \r\n";
				IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{baseId});
				try {
					if(rs != null && rs.next()){
						throw new ContractException(ContractException.HASVOUCHER);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new BOSException(e);
				}
			}
		}
		//单据反审批时预算控制
		//根据参数是否显示合同费用项目
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_CHARGETYPE);
        if (isShowCharge && contractBillInfo.getConChargeType() != null) {
           invokeUnAuditBudgetCtrl(ctx,contractBillInfo);
        }
	}
	
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
	
		
		removeDetailEntries(ctx, pk);
		
		if(model.get("number")==null)
			this.handleIntermitNumber(ctx,(FDCBillInfo)model);
		super._update(ctx, pk, model);
		
		//同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, false, pk.toString());
	}

	/*
	 * 填充编码类型字段,用于编码规则
	 */
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
	
		super.setPropsForBill(ctx, fDCBillInfo);
		
		ContractBillInfo contractBill = (ContractBillInfo)fDCBillInfo;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", contractBill.getContractType().getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("secondType");
		
		// 合同类型只能对应一个合同编码类型，因此contractCodingTypeCollection只需要get(0)
		ContractCodingTypeCollection contractCodingTypeCollection = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(view);
		if(contractCodingTypeCollection != null && contractCodingTypeCollection.size() > 0) {
			
			// 如果是对"所有合同"生效，直接返回，编码仍保持原来的。
			if (ContractSecondTypeEnum.OLD_VALUE.equals(contractCodingTypeCollection.get(0).getSecondType().getValue()))
				return; 
			
			// 如果合同的“合同性质”与 合同编码类型的合同性质 相同，则合同的编码类型置为刚查出来的
			if (contractBill.getContractPropert().getValue().equals(contractCodingTypeCollection.get(0).getSecondType().getValue())){
				contractBill.setCodeType(contractCodingTypeCollection.get(0));
			}
		}
		else {
			contractBill.setCodeType(null);
		}
	}

	private void removeDetailEntries(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", pk.toString()));
		
		ContractBillEntryFactory.getLocalInstance(ctx).delete(filter);
	}
	protected boolean _contractBillStore(Context ctx, IObjectPK ctPK, String storeNumber) throws BOSException, EASBizException {
		boolean flag = false;
//		ContractBillInfo cbi = this.getContractBillInfo(ctx,ctPK);
//		 String sql =
//		        "select fid from T_CON_ContractBill where FNumber = ? and FOrgUnitID = ? ";
//		    Object[] params = new Object[]{storeNumber,cbi.getOrgUnit().getId().toString()};
//
//		    RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
//		    try {
//				if(rowset.next()){
//					if(!rowset.getString("FID").equalsIgnoreCase(ctPK.toString())){
//						throw (new ContractException(ContractException.NUMBER_DUP));
//					}else{
//						sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ?  where FID = ? ";
//					    params = new Object[]{storeNumber,ctPK.toString()};
//					    DbUtil.execute(ctx,sql,params);
//					    flag = true;
//					}
//				}else{
//					sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ?  where FID = ? ";
//				    params = new Object[]{storeNumber,ctPK.toString()};
//				    DbUtil.execute(ctx,sql,params);
//				    flag = true;
//				}
//			} catch (SQLException e) {			
//			}
//			
//			//如果归档成功,该合同关联的变更签证单状态改变为已审批
//			if(flag){
//				sql = "update T_CON_ContractChangeBill set  FState = '4AUDITTED' where FContractBillID = ? ";
//				params = new Object[]{ctPK.toString()};
//			    DbUtil.execute(ctx,sql,params);				
//			}
//	    
		return flag;
	}
	
	/**
	 * 如果是非独立结算的补充合同,金额累加到主合同
	 * @param ctx
	 * @param billId
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void supplyAdd(Context ctx, BOSUuid billId,boolean audit) throws BOSException, EASBizException{
		if(audit){
			//如果是非独立结算的补充合同,金额累加到
			String sql1 = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n" +
					",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt                             \r\n"+
					"from T_CON_ContractbillEntry entry 																							\r\n" +
					"inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
					"inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and parent.fcurprojectid=con.fcurprojectid												\r\n" +
					"where con.fid=? and entry.FRowkey='am' 																				\r\n";
			IRowSet rs1 = DbUtil.executeQuery(ctx,sql1,new Object[]{billId.toString()});
			try {
				if(rs1!=null && rs1.next()){
					//更新主合同金额
					String splitState = rs1.getString("splitState");
					String mainId = rs1.getString("mainId");
					//补充合同金额
					BigDecimal supAmount = rs1.getBigDecimal("amount");
					//主合同本位币金额
					BigDecimal mainAmount = rs1.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs1.getBigDecimal("mainLocalAmount");
					//主合同原币金额
					BigDecimal oriAmount = rs1.getBigDecimal("oriAmount");
					//主合同汇率
					BigDecimal mainRate = rs1.getBigDecimal("mainRate");
					//主合同保修比例
					BigDecimal grtRate = rs1.getBigDecimal("grtRate");
					//主合同保修金额
					BigDecimal grtAmount = rs1.getBigDecimal("grtAmount");
					//主合同印花税率
					BigDecimal stampRate = rs1.getBigDecimal("stampRate");
					//主合同印花税金额
					BigDecimal stampAmt = rs1.getBigDecimal("stampAmt");
					//更新主合同原币和本位币金额
					String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
					
			    	//原币金额
			    	if(supAmount==null){
			    		supAmount = FDCConstants.ZERO;
			    	}
			    	if(mainAmount==null){
			    		mainAmount = FDCConstants.ZERO;
			    	}
			    	if(oriAmount==null){
			    		oriAmount = FDCConstants.ZERO;
			    	}
			    	BigDecimal revAmount = FDCConstants.ZERO;
			    	if(mainRate.compareTo(FDCConstants.ZERO)==1){
			    		revAmount = supAmount.multiply(mainRate);
			    	}
			    	//更新保修金额
			    	BigDecimal newGrtAmount = FDCConstants.ZERO;
			    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
			    		newGrtAmount = (supAmount.add(oriAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	//更新印花税金额
			    	BigDecimal newStampAmt = FDCConstants.ZERO;
			    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
			    		newStampAmt = (supAmount.add(oriAmount)).multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
					DbUtil.execute(ctx,updateSql,new Object[]{supAmount.add(oriAmount),revAmount.add(mainAmount),newGrtAmount,newStampAmt,mainId});
							    		
					//判断主合同的金额 和 合同拆分的已拆分金额 ，是否相等。如果不等，把主合同和合同拆分的拆分状态改为部分拆分
//					String updateState1 = "update T_CON_Contractbill set FSplitState=? where Fid=?";
//					String updateState2 = "update T_CON_ContractCostSplit set FSplitState=? where Fid=?";
					EntityViewInfo viewInfo = new EntityViewInfo();
//					viewInfo.getSelector().add("amount");
//					viewInfo.getSelector().add("splitState");
					viewInfo.getSelector().add("*");
					viewInfo.getSelector().add("curProject.costCenter");
					viewInfo.getSelector().add("contractBill.isCoseSplit");
					FilterInfo filterInfo = new FilterInfo();
					viewInfo.setFilter(filterInfo);
					filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",mainId));
					
					//R110228-280：录入非单独结算的补充合同之后主合同显示为全部拆分(之前没有考虑合同不进入动态成本的情况)
					ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
					if (contractbill.isIsCoseSplit()) {
						
						//成本类：非自动按比例拆分时部分拆分(以前逻辑)，否则(启用参数)自动拆分并且状态为全部拆分 by hpw 2010-06-25
						ContractCostSplitCollection col = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);
						boolean isSplitBaseOnProp = false;
						if (col != null && col.size() > 0) {
							ContractCostSplitInfo info = col.get(0);
							if (info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null
									&& info.getCurProject().getCostCenter().getId() != null) {
								isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_SPLITBASEONPROP);
							}
						}
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ContractCostSplitInfo info = col.get(i);
							if(info!=null){
//								if(!isSplitBaseOnProp){
//									//如果补充合同+主合同金额大于主合同已拆分金额 则变更主合同拆分状态为部分拆分
//									//R110608-0383: 录入金额为0的主合同的拆分金额存在为0的情况
//									BigDecimal splitAmount = info.getBigDecimal("amount");
//									if (splitAmount == null) {
//										splitAmount = FDCHelper.ZERO;
//									}
//									if((mainAmount.add(revAmount)).compareTo(splitAmount)==1
//											&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
//										builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
//										builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
//										builder.addParam(mainId.toString());
//										builder.getSql();
//										builder.execute();
//										builder.clear();//不用再New
//										builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
//										builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
//										builder.addParam(mainId.toString());
//										builder.execute();
//										builder.clear();
//									}
//								}
							}
						}
						
						if(isSplitBaseOnProp){
							ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));
						}
						
					} else {
						//非成本类：更新状态为部分拆分
						ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ConNoCostSplitInfo info = col.get(i);
							if(info!=null){
								//如果补充合同+主合同金额大于主合同已拆分金额 则变更主合同拆分状态为部分拆分
								BigDecimal splitAmount = info.getBigDecimal("amount");
								if (splitAmount == null) {
									splitAmount = FDCHelper.ZERO;
								}
								if((mainAmount.add(revAmount)).compareTo(splitAmount)==1
										&& splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
									builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.getSql();
									builder.execute();
									builder.clear();//不用再New
									builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
									builder.addParam(CostSplitStateEnum.PARTSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.execute();
									builder.clear();
								}
							}
						}
					}
					
			    	//更新合同付款计划    					
					revAmount = revAmount.add(FDCUtils.getContractLastPrice (ctx,mainId,false));
					
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add(new SelectorItemInfo("payProportion"));
					FilterInfo filter = new FilterInfo();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));
					
					IContractPayPlan iContractPayPlan =  ContractPayPlanFactory.getLocalInstance(ctx);
					ContractPayPlanCollection payPlans =iContractPayPlan.getContractPayPlanCollection(view);
					for (int i = 0; i < payPlans.size(); i++) {
						ContractPayPlanInfo info = payPlans.get(i);
						
						info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2,
								BigDecimal.ROUND_HALF_UP));					
						iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()),info);
					}
				/*****更新主合同的合同执行情况表合同成本金额 -by neo****/
				ContractExecInfosFactory.getLocalInstance(ctx).
						updateSuppliedContract("audit", mainId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}else{
			//如果是非独立结算的补充合同,金额累加到
			String sql = " select entry.fcontent as amount,parent.fsplitState splitState,parent.fid mainId,parent.FAmount mainAmount,parent.ForiginalAmount oriAmount 			\r\n" +
					",parent.FexRate mainRate,parent.FgrtRate grtRate,parent.FGrtAmount grtAmount,parent.FStampTaxRate stampRate,parent.FStampTaxAmt stampAmt    \r\n"+
					" ,parent.FHasSettled    from T_CON_ContractbillEntry entry 																	\r\n" +
					"inner join T_CON_Contractbill con on con.fid=entry.fparentid  and con.fisAmtWithoutCost=1 and con.fcontractPropert='SUPPLY' 	\r\n" +
					"inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber 	and parent.fcurprojectid=con.fcurprojectid					\r\n" +
					"where   con.fid=? 	and  entry.FRowkey='am' 	 																							\r\n";
			IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{billId.toString()});
			try {
				if(rs!=null && rs.next()){
					//
					boolean hasSettle = rs.getBoolean("FHasSettled");
					if(hasSettle){
						throw new ContractException(ContractException.MAINCONHASSETTLED);
					}
					String splitState = rs.getString("splitState");	
					//如果主合同已完全拆分 则不允许反审批补充合同
					if(splitState!=null &&splitState.equals(CostSplitStateEnum.ALLSPLIT_VALUE)){
						throw new ContractException(ContractException.HASALLAPLIT);
					}
					//更新主合同金额
					String mainId = rs.getString("mainId");
					//补充合同金额
					BigDecimal supAmount = rs.getBigDecimal("amount");
					BigDecimal mainAmount = rs.getBigDecimal("mainAmount");
//					BigDecimal localAmount = rs.getBigDecimal("mainLocalAmount");
					//主合同原币金额
					BigDecimal oriAmount = rs.getBigDecimal("oriAmount");
					//主合同汇率
					BigDecimal mainRate = rs.getBigDecimal("mainRate");
					//主合同保修比例
					BigDecimal grtRate = rs.getBigDecimal("grtRate");
					//主合同保修金额
					BigDecimal grtAmount = rs.getBigDecimal("grtAmount");
					//主合同印花税率
					BigDecimal stampRate = rs.getBigDecimal("stampRate");
					//主合同印花税金额
					BigDecimal stampAmt = rs.getBigDecimal("stampAmt");
					
					String updateSql = "update T_CON_Contractbill set ForiginalAmount=?, FAmount=?,FGrtAmount=?,FStampTaxAmt=? where Fid=?";
//					String updateSql = "update T_CON_Contractbill set FAmount=?,FlocalAmount=? where Fid=?";
					//DbUtil.execute(ctx,updateSql,new Object[]{mainAmount.subtract(supAmount),localAmount.subtract(supAmount),mainId});
					
					//R101227-311合同反审批时报错， 先进行空指针处理
			    	//补充合同金额 本位币
					if(supAmount==null){
						supAmount = FDCConstants.ZERO;
					}
			    	BigDecimal revAmount = supAmount.multiply(mainRate);
			    	//如果主合同已拆分金额大于其自身金额 则补充合同不能反审批
			    	String splitSql = "select famount from T_CON_ContractCostSplit where fcontractBillId=?";
			    	IRowSet ir = DbUtil.executeQuery(ctx,splitSql,new Object[]{mainId.toString()});
			    	if(ir!=null && ir.next()){
			    		BigDecimal splitAmount = ir.getBigDecimal("famount");
			    		if (splitAmount == null) {
			    			splitAmount = FDCHelper.ZERO;
			    		}
			    		if(splitAmount.compareTo(mainAmount.subtract(revAmount))==1){
			    			throw new ContractException(ContractException.HASSPLIT);
			    		}
			    	}
					//如果付款申请单的累计申请金额已经大于revAmount
			    	if(mainAmount==null){
			    		mainAmount = FDCConstants.ZERO;
			    	}
			    	if(oriAmount==null){
			    		oriAmount = FDCConstants.ZERO;
			    	}
			    	BigDecimal temp = FDCConstants.ZERO;
			    	if(mainRate.compareTo(FDCConstants.ZERO)==1){
			    		temp = supAmount.multiply(mainRate);
			    	}
			    	//更新保修金额
			    	BigDecimal newGrtAmount = FDCConstants.ZERO;
			    	if(grtRate.compareTo(FDCConstants.ZERO)==1){
			    		newGrtAmount = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(grtRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	//更新印花税金额
			    	BigDecimal newStampAmt = FDCConstants.ZERO;
			    	if(stampRate.compareTo(FDCConstants.ZERO)==1){
			    		newStampAmt = (oriAmount.subtract(supAmount)).multiply(mainRate).multiply(stampRate).divide(FDCConstants.ONE_HUNDRED,4,2);
			    	}
			    	DbUtil.execute(ctx,updateSql,new Object[]{oriAmount.subtract(supAmount),mainAmount.subtract(temp),newGrtAmount,newStampAmt,mainId});
					EntityViewInfo viewInfo = new EntityViewInfo();
					viewInfo.getSelector().add("*");
					viewInfo.getSelector().add("curProject.costCenter");
					viewInfo.getSelector().add("contractBill.isCoseSplit");
					FilterInfo filterInfo = new FilterInfo();
					viewInfo.setFilter(filterInfo);
					filterInfo.getFilterItems().add(new FilterItemInfo("contractBill.id",mainId));
					
					//R110228-280：录入非单独结算的补充合同之后主合同显示为全部拆分(之前没有考虑合同不进入动态成本的情况)
					ContractBillInfo contractbill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(mainId));
					if (contractbill.isIsCoseSplit()) {
						
						//成本类：非自动按比例拆分时部分拆分，否则自动拆分并且状态为全部拆分 by hpw 2010-06-25
						ContractCostSplitCollection coll = ContractCostSplitFactory
						.getLocalInstance(ctx).getContractCostSplitCollection(viewInfo);
						boolean isSplitBaseOnProp = false;
						if (coll != null && coll.size() > 0) {
							ContractCostSplitInfo info = coll.get(0);
							if (info != null && info.getContractBill() != null && info.getContractBill().isIsCoseSplit() && info.getCurProject() != null && info.getCurProject().getCostCenter() != null
									&& info.getCurProject().getCostCenter().getId() != null) {
								isSplitBaseOnProp = FDCUtils.getDefaultFDCParamByKey(ctx, info.getCurProject().getCostCenter().getId().toString(), FDCConstants.FDC_PARAM_SPLITBASEONPROP);
							}
						}
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int j=0;j < coll.size();j++){
							ContractCostSplitInfo splitInfo = coll.get(j);
							if(splitInfo!=null){
								if(!isSplitBaseOnProp){
									//如果主合同原金额等于主合同已拆分金额 则变更主合同拆分状态为完全拆分
									BigDecimal splitAmount = splitInfo.getBigDecimal("amount");
									if (splitAmount == null) {
										splitAmount = FDCHelper.ZERO;
									}
									if((mainAmount.subtract(revAmount)).compareTo(splitAmount)==0 
											&& splitState.equals(CostSplitStateEnum.PARTSPLIT_VALUE)){
										builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
										builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
										builder.addParam(mainId.toString());
										builder.getSql();
										builder.execute();
										//								DbUtil.execute(ctx,updateState1,new Object[]{CostSplitStateEnum.PARTSPLIT_VALUE,mainId.toString()});
										//								FDCSQLBuilder builder2=new FDCSQLBuilder(ctx);
										builder.clear();
										builder.appendSql("update T_CON_ContractCostSplit set fsplitState=? where fcontractbillid=?");
										builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
										builder.addParam(mainId.toString());
										builder.execute();
										builder.clear();
									}
								}
							}
						}
						
						if(isSplitBaseOnProp){
							ContractCostSplitFactory.getLocalInstance(ctx).autoSplit4(BOSUuid.read(mainId));
						}
					} else {
						
						//非成本类：更新状态为部分拆分
						ConNoCostSplitCollection col = ConNoCostSplitFactory.getLocalInstance(ctx).getConNoCostSplitCollection(viewInfo);
						FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
						for(int i=0;i < col.size();i++){
							ConNoCostSplitInfo info = col.get(i);
							if(info!=null){
								//如果主合同原金额等于主合同已拆分金额 则变更主合同拆分状态为完全拆分
								BigDecimal splitAmount = info.getBigDecimal("amount");
								if (splitAmount == null) {
									splitAmount = FDCHelper.ZERO;
								}
								if((mainAmount.add(revAmount)).compareTo(splitAmount)==0
										&& splitState.equals(CostSplitStateEnum.PARTSPLIT_VALUE)){
									builder.appendSql("update T_CON_ContractBill set fsplitState=? where fid=?");
									builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.getSql();
									builder.execute();
									builder.clear();
									builder.appendSql("update T_CON_ConNoCostSplit set fsplitState=? where fcontractbillid=?");
									builder.addParam(CostSplitStateEnum.ALLSPLIT_VALUE);
									builder.addParam(mainId.toString());
									builder.execute();
									builder.clear();
								}
							}
						}
					}
					
			    	//更新合同付款计划    	
			    	revAmount = revAmount.add(FDCUtils.getContractLastPrice (ctx,mainId,false));
			    	
					EntityViewInfo view = new EntityViewInfo();
					view.getSelector().add(new SelectorItemInfo("payProportion"));
					FilterInfo filter = new FilterInfo();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("contractId.id", mainId));
					
					IContractPayPlan iContractPayPlan =  ContractPayPlanFactory.getLocalInstance(ctx);
					ContractPayPlanCollection payPlans =iContractPayPlan.getContractPayPlanCollection(view);
					for (int i = 0; i < payPlans.size(); i++) {
						ContractPayPlanInfo info = payPlans.get(i);
						
						info.setPayAmount(revAmount.multiply(info.getPayProportion()).divide(new BigDecimal(100), 2,
								BigDecimal.ROUND_HALF_UP));					
						iContractPayPlan.update(new ObjectUuidPK(info.getId().toString()),info);
					}
				/*****更新主合同的合同执行情况表合同成本金额 -by neo****/
				ContractExecInfosFactory.getLocalInstance(ctx).
						updateSuppliedContract("audit", mainId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		//ContractBillInfo contractBill = (ContractBillInfo)this.getValue(ctx,new ObjectUuidPK(billId.toString()),seletor);
	}
	
	private String getMainSignAmount(Context ctx, BOSUuid billId) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder
				.appendSql(" select con.famount amount from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder
				.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and ");
		builder.appendParam(" con.fid", billId.toString());
		rowSet = builder.executeQuery();
		while (rowSet.next()) {
			return rowSet.getBigDecimal("amount").toString();
		}
		return null;
	}
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		
		checkBillForAudit( ctx,billId);
		
		super._audit(ctx, billId);
		
		// 在金额累计之前取主合同的签约金额
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, true, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
		}	
		
		//如果是非独立结算的补充合同,金额累加到
		supplyAdd(ctx, billId,true);
	
		//同步到执行状态：已签署
		ContractStateHelper.synToExecState(ctx, billId.toString(), ContractExecStateEnum.SIGNED);
		
		//自动审批拆分单(完全拆分状态的)
		//成本拆分
		CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId), "T_CON_ContractCostSplit", "FContractBillID");
		//财务拆分
		CostSplitBillAutoAuditor.autoAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConNoCostSplit", "FContractBillID");
		//审批时根据合同数据增加合同执行表相关信息
		/******判断：当且仅当合同性质为补充合同且合同为不计成本金额时不记录合同执行情况表******/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isAmtWithoutCost",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("contractPropert",ContractPropertyEnum.SUPPLY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("id",billId));
		if(!ContractBillFactory.getLocalInstance(ctx).
				exists(filter)){
			ContractExecInfosFactory.getLocalInstance(ctx)
				.updateContract(ContractExecInfosInfo.EXECINFO_AUDIT,billId.toString());
		}
		
		updatePeriod(ctx, billId);
		
		
		
		//合同审批同时合同对应的预估合同变更单也相应的审批 20120222 wangxin 
	
//		 IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
//	   	SelectorItemCollection sel = new SelectorItemCollection();
//	   	sel.add("*");
//	   	sel.add("entrys.*");
//	   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId), sel);
//	   if(ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){//主合同 审批则相关联的预估合同变动单审批
//		   EntityViewInfo view1 = new EntityViewInfo();
//		   FilterInfo Filter1 = new FilterInfo();
//		   Filter1.getFilterItems().add(new FilterItemInfo("isFromContract",Boolean.TRUE));
//		   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",billId));
//		   view1.setFilter(Filter1);
//		   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//		   if(coll1.size()>0){
//			   ContractEstimateChangeBillInfo info1 = coll1.get(0);
//			   iFaced.audit(info1.getId());
//		   }
//	   }else if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
		   //金额怎么玩  分录关联的使用合约规划返还，新增的预估合同变不用管 20120401
//		   ContractBillEntryCollection entryColl = contractInfo.getEntrys();
//		   ContractBillEntryInfo entryInfo= null;
//		  for(int i = 0 ; i < entryColl.size(); i++){
//			  entryInfo = entryColl.get(i);
//			  if("esId".equalsIgnoreCase(entryInfo.getRowKey()))break;
//		  }
//		   SelectorItemCollection esSel = new SelectorItemCollection();
//		   esSel.add("*");
//		   esSel.add("contractbill.programmingContract.*");
//		   EntityViewInfo view = new EntityViewInfo();
//		   view.setSelector(esSel);
//		   FilterInfo newfilter = new FilterInfo();
//		   view.setFilter(newfilter);
		   //分录关联的合同为被使用了
//		  ContractEstimateChangeBillInfo esInfo = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(entryInfo.getContent()),esSel);
//		  esInfo.setIsLastest(false);
//		  esInfo.setIsUsed(true);
//		  SelectorItemCollection sel1 = new SelectorItemCollection();
//		  sel1.add("isUsed");
//		  sel1.add("isLastest");
//		  iFaced.updatePartial(esInfo, sel1);
//		
//		  ProgrammingContractInfo programmingInfo =  esInfo.getContractbill().getProgrammingContract();
//		  BigDecimal balance = programmingInfo.getBalance();
//		  BigDecimal esAmount  = programmingInfo.getEstimateAmount();
//		  balance =  balance.add(esInfo.getEstimateAmount());
//		  esAmount = esAmount.subtract(esInfo.getEstimateAmount());
//		  programmingInfo.setBalance(balance);
//		  programmingInfo.setEstimateAmount(esAmount);
//		  SelectorItemCollection pSel = new SelectorItemCollection();
//		  pSel.add("balance");
//		  pSel.add("estimateAmount");
//		  ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programmingInfo, pSel);
		  
		  //补充合同 审批则审批分录相关联的预估合同变动单审批
//		   EntityViewInfo esview = new EntityViewInfo();
//		   FilterInfo esfilter = new FilterInfo();
//		   esfilter.getFilterItems().add(new FilterItemInfo("suppyContractId",billId.toString()));
//		   esview.setFilter(esfilter);
//		   ContractEstimateChangeBillCollection  esColl =  iFaced.getContractEstimateChangeBillCollection(esview);
//		   if(esColl.size() > 0 ){
//			   ContractEstimateChangeBillInfo esInfo1 =  esColl.get(0);
//			   iFaced.audit(esInfo1.getId());
//		   }
//	   }
//		try {
//			synContractProgAmt(ctx,billId,true);
//		} catch (SQLException e) {
//			e.printStackTrace();
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

	/**
	 * 1 .当合同未结算时(无最终结算或最终结算未审批)，规划余额=规划金额-（签约金额+变更金额），控制余额=控制金额-签约金额 2
	 * .当合同已结算时(最终结算已审批)，规划余额=规划金额-结算金额，控制余额=控制金额-结算金额 3
	 * .反写时点在合同单据审批通过时、变更审批单审批通过时、变更指令单结算时、合同结算审批通过时。 4.
	 * 合同修订审批后，规划余额=规划金额-（修订后的签约金额+变更金额），控制余额=控制金额-修订后的签约金额。
	 * 4、未签合同金额单未签的时候 = 规划金额，签了合同 = 0；
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
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rowSet = null;
		builder.appendSql(" select parent.fid conId,entry.fcontent as amount from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
		builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
		builder.appendSql(" where  entry.FRowkey='am' and ");
		builder.appendParam(" con.fid", billIdStr);
		rowSet = builder.executeQuery();
		
		BigDecimal supAmount = FDCHelper.ZERO;
		
		if (rowSet.next()) {
			billIdStr = rowSet.getString("conId").toString();
			supAmount=rowSet.getBigDecimal("amount");
		}
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx, new ObjectUuidPK(billIdStr), getSic());
		ContractBillInfo supplierContractBillInfo= this.getContractBillInfo(ctx, new ObjectUuidPK(billId), getSic());
		if (contractBillInfo.getProgrammingContract() == null)
			return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
		if (pcInfo == null)
			return;
		// 规划余额
		BigDecimal balanceAmt = pcInfo.getBalance();
		// 控制余额
		BigDecimal controlBalanceAmt = pcInfo.getControlBalance();
		// 合同签约金额
		BigDecimal signAmount = contractBillInfo.getAmount();
		// if(mainSignAmount != null){
		// if(flag){
		// signAmount = FDCHelper.subtract(signAmount, mainSignAmount);
		// }else{
		// signAmount = FDCHelper.add(signAmount, mainSignAmount);
		// }
		// }
		// 框架合约签约金额
		BigDecimal signUpAmount = pcInfo.getSignUpAmount();
		// 差额
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("controlBalance");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("srcId");
		sict.add("estimateAmount");
		sict.add("budgetAmount");
		sict.add("billid");
		sict.add("unAuditContractEA");
		if (flag) {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, signAmount));
				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, signAmount));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, signAmount));
//				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.subtract(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.subtract(controlBalanceAmt, relateAmt));
				pcInfo.setSignUpAmount(FDCHelper.add(signUpAmount, relateAmt));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.subtract(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.subtract(controlBalanceAmt, relateAmt));
//				otherSignUpAmount = FDCHelper.subtract(FDCHelper.add(signUpAmount, relateAmt), signUpAmount);
			}
			if(ContractPropertyEnum.SUPPLY.equals(supplierContractBillInfo.getContractPropert())){
				//流程里面报错放到前台？
//				if(supAmount.compareTo(pcInfo.getEstimateAmount())>0){
//					throw new EASBizException(new NumericExceptionSubItem("100","补充合同原币金额大于主合同合约规划预估金额！"));
//				}
				//主要是补充合同，把合约规划的预估金额置为零，
//				BigDecimal  estimateAmount = pcInfo.getEstimateAmount();
//				pcInfo.setUnAuditContractEA(supAmount);
				pcInfo.setEstimateAmount(FDCHelper.subtract(pcInfo.getEstimateAmount(), supAmount));
				pcInfo.setBalance(FDCHelper.add(pcInfo.getBalance(), supAmount));
				
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
//					sel.add("isRespite");
//					ContractEstimateChangeBillFactory.getLocalInstance(ctx).updatePartial(contInfo, sel);
//				}
				ContractEstimateChangeBillFactory.getLocalInstance(ctx).sub(pcInfo, ContractEstimateChangeTypeEnum.SUPPLY,supAmount,true,contractBillInfo.getOrgUnit().getId().toString());
			}else{
				pcInfo.setBudgetAmount(FDCHelper.ZERO);
				pcInfo.setBillId(billIdStr);
			}
			
			service.updatePartial(pcInfo, sict);
			
//			// 更新其他的合约规划版本金额
//			String progId = pcInfo.getId().toString();
//			while (progId != null) {
//				String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//				if (nextVersionProgId != null) {
//					pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//					pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), otherSignUpAmount));
//					pcInfo.setControlBalance(FDCHelper.subtract(pcInfo.getControlBalance(), otherSignUpAmount));
//					pcInfo.setSignUpAmount(FDCHelper.add(pcInfo.getSignUpAmount(), otherSignUpAmount));
//					service.updatePartial(pcInfo, sict);
//					progId = pcInfo.getId().toString();
//				} else {
//					progId = null;
//				}
//			}
		} else {
			if (relateAmt == null) {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, signAmount));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, signAmount));
				pcInfo.setSignUpAmount(FDCHelper.subtract(signUpAmount, signAmount));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, signAmount));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, signAmount));
//				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, signAmount), signUpAmount);
			} else {
				pcInfo.setBalance(FDCHelper.add(balanceAmt, relateAmt));
				pcInfo.setControlBalance(FDCHelper.add(controlBalanceAmt, relateAmt));
				pcInfo.setSignUpAmount(FDCHelper.subtract(signUpAmount, relateAmt));
				// 维护差额
				// otherBalance = FDCHelper.subtract(balanceAmt,
				// FDCHelper.add(balanceAmt, relateAmt));
				// otherControlBalance = FDCHelper.subtract(controlBalanceAmt,
				// FDCHelper.add(controlBalanceAmt, relateAmt));
//				otherSignUpAmount = FDCHelper.subtract(FDCHelper.subtract(signUpAmount, relateAmt), signUpAmount);
			}
			if(ContractPropertyEnum.SUPPLY.equals(supplierContractBillInfo.getContractPropert())){
//				BigDecimal  estimateAmount = FDCHelper.ZERO;
//				if(pcInfo.getUnAuditContractEA()!=null){
//					estimateAmount=pcInfo.getUnAuditContractEA();
//				}
				//主要是补充合同，把合约规划的预估金额置为零，
//				if(!(pcInfo.getEstimateAmount()!=null&&pcInfo.getEstimateAmount().compareTo(FDCHelper.ZERO)>0)){
					pcInfo.setEstimateAmount(FDCHelper.add(pcInfo.getEstimateAmount(), supAmount));
//				}
				pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), supAmount));
				
				//对应的预估变动单变为是否最新为false
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
			}else{
				pcInfo.setBudgetAmount(pcInfo.getAmount());
				pcInfo.setBillId(null);
			}
			service.updatePartial(pcInfo, sict);
			
//			// 更新其他的合约规划版本金额
//			String progId = pcInfo.getId().toString();
//			while (progId != null) {
//				String nextVersionProgId = getNextVersionProg(ctx, progId, builder, rowSet);
//				if (nextVersionProgId != null) {
//					pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(nextVersionProgId), sict);
//					pcInfo.setBalance(FDCHelper.add(pcInfo.getBalance(), otherSignUpAmount));
//					pcInfo.setControlBalance(FDCHelper.add(pcInfo.getControlBalance(), otherSignUpAmount));
//					pcInfo.setSignUpAmount(FDCHelper.subtract(pcInfo.getSignUpAmount(), otherSignUpAmount));
//					service.updatePartial(pcInfo, sict);
//					progId = pcInfo.getId().toString();
//				} else {
//					progId = null;
//				}
//			}
		}
	}

	/**
	 * 
	 * @param billId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private BigDecimal getChangeAmountByContract(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

		StringBuffer sql = new StringBuffer();

		sql.append(" Select CON.FID CON, ISNULL(SUM(CAB.FAmount),0) AMOUNT  ");
		sql.append(" from T_CON_ChangeAuditBill CAB ");
		sql.append(" right join T_CON_ChangeSupplierEntry CSE ON CSE.FParentID = CAB.FID  ");
		sql.append(" inner join T_CON_ContractBill CON ON  CSE.FContractBillID = CON.FID  ");
		sql.append(" WHERE  1 = 1 AND  ");
		sql.append(" CON.FID = '").append(billId.toString()).append("' AND	 ");
		sql.append(" CON.FState IN ('4AUDITTED')  ");
		sql.append(" GROUP BY CON.FID, CON.FAmount ");

		IRowSet rowSet = DbUtil.executeQuery(ctx, sql.toString());
		BigDecimal conTotal = new BigDecimal(0);
		try {
			if (rowSet.next()) {
				String conId = rowSet.getString("CON");
				conTotal = rowSet.getBigDecimal("AMOUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conTotal;
	}

	private BigDecimal getContractSettlementBillByContract(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

		IContractSettlementBill settleSerivce = ContractSettlementBillFactory.getLocalInstance(ctx);
		// 你找到该合同下是否有最终结算的单据，有，就说明该合同是最终结算了
		// 合同已结算则一定有最终结算的结算单
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(this.getSettleSic());

		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("contractBill", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", Integer.valueOf("1")));

		view.setFilter(filter);

		ContractSettlementBillCollection settleColl = settleSerivce.getContractSettlementBillCollection(view);
		BigDecimal amt = new BigDecimal(0);

		if (settleColl != null) {
			if (settleColl.size() == 1) {
				ContractSettlementBillInfo billInfo = settleColl.get(0);
				amt = billInfo.getAmount();
			}
		}
		return amt;
	}
	
	/**
	 * 根据当前项目成本期间更新暂缓单据的业务日期和订立期间
	 * @param ctx
	 * @param billId
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void updatePeriod(Context ctx, BOSUuid billId) throws EASBizException, BOSException {
		
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("isRespite");
		selectors.add("curProject.id");
		selectors.add("curProject.fullOrgUnit.id");
		selectors.add("bookedDate");
		selectors.add("period.*");
		ContractBillInfo billInfo = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId), selectors);
		
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
			
			//更新付款申请单的业务日期，申请期间和暂缓状态
			
			selectors = new SelectorItemCollection();
			selectors.add("period");
			selectors.add("bookedDate");
			selectors.add("isRespite");
			billInfo.setBookedDate(bookedDate);
			billInfo.setPeriod(shouldPeriod);
			billInfo.setIsRespite(false);
			_updatePartial(ctx, billInfo, selectors);
		}
	}
	
	//单据审批时 预算控制
	private void invokeAuditBudgetCtrl(Context ctx,ContractBillInfo conBill) throws BOSException ,EASBizException{
		boolean useWorkflow = FDCUtils.isRunningWorkflow(ctx, conBill.getId().toString());
        if (!useWorkflow) {
            IBgControlFacade bgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
            IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getLocalInstance(ctx);

            boolean isPass = false;
            isPass = iBgBudget.getAllowAccessNoWF(FDCConstants.ContractBill);
            if (isPass) {
                // 5.1.1暂时屏蔽
                bgControlFacade.bgAuditAllowAccess(conBill.getId(), FDCConstants.ContractBill, null);
            } else {
                isPass = bgControlFacade.bgAudit(conBill.getId().toString(),  FDCConstants.ContractBill, null);
            }
            // 根据isPass判断是否抛异常
            if (!isPass) {
                throw new ContractException(ContractException.BEFOREBGBAL);
            }
        }
	}
	//单据反审批时 预算控制
	private void invokeUnAuditBudgetCtrl(Context ctx,ContractBillInfo conBill) throws BOSException ,EASBizException{
		
		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(ctx);
		iBgCtrl.cancelRequestBudget(conBill.getId().toString());
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", billId.toString()));
		if(ContractPCSplitBillFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","存在合约规划拆分，不能进行反审批操作！"));
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contract.id", billId.toString()));
		if(ContractOutPayPlanFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","存在计划外付款申请，不能进行反审批操作！"));
		}
		if(ContractEvaluationFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","存在合同执行情况评估表，不能进行反审批操作！"));
		}
		checkBillForUnAudit(ctx, billId);
		
		filter = new FilterInfo();
		// 合同
		filter.getFilterItems().add(new FilterItemInfo("id", billId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("isArchived", BooleanEnum.TRUE));
		
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.HASACHIVED);
		}	
		super._unAudit(ctx, billId);
		// 在金额累计之前取补充合同的补充金额
		// 在金额累计之前取主合同的签约金额
		String mainSignAmount = null;
		try {
			mainSignAmount = getMainSignAmount(ctx, billId);
			synUpdateBillByRelation(ctx, billId, false, mainSignAmount);
		} catch (SQLException e1) {
			logger.error(e1);
		}
		//如果是非独立结算的补充合同，主合同金额减少
		supplyAdd(ctx, billId,false);
		//自动反审批拆分单
		//成本拆分
		CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId), "T_CON_ContractCostSplit", "FContractBillID");
		//财务拆分
		CostSplitBillAutoAuditor.autoUnAudit(ctx, new ObjectUuidPK(billId), "T_CON_ConNoCostSplit", "FContractBillID");
		
		//自动删除本期月结数据
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("period.id");

		ContractBillInfo info = (ContractBillInfo)this.getValue(ctx,new ObjectUuidPK(billId),selectors);
		if(info.getPeriod()!=null){
			CostClosePeriodFacadeFactory.getLocalInstance(ctx).delete(info.getId().toString(),
					info.getPeriod().getId().toString());
		}
		//反审批合同时删除合同执行表对应相关信息
		ContractExecInfosFactory.getLocalInstance(ctx)
			.updateContract(ContractExecInfosInfo.EXECINFO_UNAUDIT,billId.toString());
		
//		IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
//		  
//			SelectorItemCollection sel = new SelectorItemCollection();
//		   	sel.add("*");
//		   	sel.add("entrys.*");
//		   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(billId), sel);
//		    EntityViewInfo view1 = new EntityViewInfo();
//			   FilterInfo Filter1 = new FilterInfo();
//		   if(ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){//主合同 ，补充合同反审批时都返还金额后都删除最新的预估合同变更单
//			   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",billId));
//		   }else if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
//			   contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo("select id where number = '"+contractInfo.getMainContractNumber()+"'");
//			   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",contractInfo.getId().toString()));
//			  
//		   }
//		   Filter1.getFilterItems().add(new FilterItemInfo("isUsed",Boolean.FALSE));
//		   Filter1.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
//		   view1.setFilter(Filter1);
//		   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//		   if(coll1.size()>0){
//			   ContractEstimateChangeBillInfo info1 = coll1.get(0);
//			   iFaced.unAudit(info1.getId());
//			   iFaced.delete(new ObjectUuidPK(info1.getId()));
//		   }
//		   if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
			   //把补充合同使用的预估合同变动变为最新的，未使用的。余额 与 预估合同变动金额 更新
//			   ContractBillEntryCollection entryColl = contractInfo.getEntrys();
//			   ContractBillEntryInfo entryInfo= null;
//			  for(int i = 0 ; i < entryColl.size(); i++){
//				  entryInfo = entryColl.get(i);
//				  if("esId".equalsIgnoreCase(entryInfo.getRowKey()))break;
//			  }
//			   SelectorItemCollection esSel = new SelectorItemCollection();
//			   esSel.add("*");
//			   esSel.add("contractbill.programmingContract.*");
//			   EntityViewInfo view = new EntityViewInfo();
//			   view.setSelector(esSel);
//			   FilterInfo newfilter = new FilterInfo();
//			   view.setFilter(newfilter);
			   //分录关联的合同为被使用了
//			  ContractEstimateChangeBillInfo esInfo = iFaced.getContractEstimateChangeBillInfo(new ObjectUuidPK(entryInfo.getContent()),esSel);
//			  esInfo.setIsLastest(true);
//			  esInfo.setIsUsed(false);
//			  SelectorItemCollection sel1 = new SelectorItemCollection();
//			  sel1.add("isUsed");
//			  sel1.add("isLastest");
//			  iFaced.updatePartial(esInfo, sel1);
//			
//			  ProgrammingContractInfo programmingInfo =  esInfo.getContractbill().getProgrammingContract();
//			  BigDecimal balance = programmingInfo.getBalance();
//			  BigDecimal esAmount  = programmingInfo.getEstimateAmount();
//			  balance =  balance.subtract(esInfo.getEstimateAmount());
//			  esAmount = esAmount.add(esInfo.getEstimateAmount());
//			  programmingInfo.setBalance(balance);
//			  programmingInfo.setEstimateAmount(esAmount);
//			  SelectorItemCollection pSel = new SelectorItemCollection();
//			  pSel.add("balance");
//			  pSel.add("estimateAmount");
//			  ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(programmingInfo, pSel);
//		   }
//		try {
//			synContractProgAmt(ctx,billId,false);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	

	protected boolean _contractBillStore(Context ctx, IObjectValue cbInfo, String storeNumber) throws BOSException, EASBizException {
		boolean flag = false;
		ContractBillInfo cbi = (ContractBillInfo)cbInfo;
		///////////////////////////////////////////////////////////
		//使用编码规则取号
		String orgId = cbi.getOrgUnit().getId().toString();
		String bindingProperty = "codeType.number";
		// 无当前组织，或者当前组织没定义编码规则，用集团的
		if (orgId == null || orgId.trim().length() == 0) {			
			orgId = OrgConstants.DEF_CU_ID;
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		/* 是否启用编码规则 */
		if(cbi.getArchivedNumber() == null){//如果已经有过归档 则使用以前的归档合同编号 
			if (cbi.getCodeType()!=null&&iCodingRuleManager.isExist(cbi, orgId, bindingProperty)) {
				flag = true;						
				if (iCodingRuleManager.isUseIntermitNumber(cbi, orgId, bindingProperty)) {		// 编码规则中不允许断号					
					// 编码规则中启用了“断号支持”功能，此时只是读取当前最新编码，真正的抢号在保存时
					storeNumber = iCodingRuleManager.getNumber(cbi, orgId, bindingProperty, "");										
				} else if (iCodingRuleManager.isAddView(cbi, orgId, bindingProperty)){				
					// 判断是否修改了编码,是否改大了顺序号
					if (iCodingRuleManager.isModifiable(cbi, orgId, bindingProperty)) {					
						iCodingRuleManager.checkModifiedNumber(cbi, orgId,storeNumber, bindingProperty);
					}				
				} else {
					// 什么都没选,新增不显示,允许断号,在此设置number
//					if(cbi.getNumber()==null||cbi.getNumber().equals("")){
						storeNumber = iCodingRuleManager.getNumber(cbi,orgId, bindingProperty, "");						
//					}
				}	
			}
			//如果第一次归档 则把归档编号保存 进新加字段 FArchivedNumber
			String updateNumberSql = "update T_CON_ContractBill set FArchivedNumber=?  where FID = ? ";
			Object[] params = new Object[]{storeNumber,cbi.getId().toString()};
			DbUtil.execute(ctx,updateNumberSql,params);
		}	
					
		//更新合同归档状态和归档编码
		String updateNumberSql = "update T_CON_ContractBill set FArchivingNumber=fnumber  where FID = ? ";
		Object[] params = new Object[]{cbi.getId().toString()};
		DbUtil.execute(ctx,updateNumberSql,params);
		
		//更新合同归档状态和归档编码
		String sql = "select fid from T_CON_ContractBill where FNumber = ? and FOrgUnitID = ? ";
		 
		params = new Object[]{storeNumber,cbi.getOrgUnit().getId().toString()};
		RowSet rowset = DbUtil.executeQuery(ctx, sql, params);
		 
		boolean hasCodeType =  cbi.getCodeType()!=null;
		if(hasCodeType){
			sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ? ,FCodeTypeID=? where FID = ? ";
			params = new Object[]{storeNumber,cbi.getCodeType().getId().toString(),cbi.getId().toString()};
		}else{
			sql = "update T_CON_ContractBill set  FIsArchived = 1 ,FNumber = ? where FID = ? ";
			params = new Object[]{storeNumber,cbi.getId().toString()};
		}
			
		try {
			if(rowset.next()){
				if(!rowset.getString("FID").equalsIgnoreCase(cbi.getId().toString())){
					throw (new ContractException(ContractException.NUMBER_DUP));
					
				}else{
					DbUtil.execute(ctx,sql,params);
					flag = true;
				}
			}else{
			    DbUtil.execute(ctx,sql,params);
			    flag = true;
			}
				
//				编码变化，同步到合同基本资料，用于合同做核算项目
			ContractBaseDataHelper.synToContractBaseData(ctx, false, cbi.getId().toString());

		} catch (SQLException e) {		
			throw new BOSException(e);
		}
			
			//如果归档成功,该合同关联的变更签证单状态改变为已审批
			if(flag){
				sql = "update T_CON_ContractChangeBill set  FState = '4AUDITTED' where FContractBillID = ? ";
				params = new Object[]{cbi.getId().toString()};
			    DbUtil.execute(ctx,sql,params);	
			    //如果归档成功，对应付款申请单上的合同号也应该做相应的调整 by cassiel_peng 2010-01-12
			    String _sql="update T_CON_PayRequestBill set FContractNo = ? where FContractId = ? ";
			    params = new Object[]{storeNumber,cbi.getId().toString()};
			    DbUtil.execute(ctx, _sql,params);
			}
	    
		return flag;
	}
	
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("codeType.number"));
        sic.add(new SelectorItemInfo("curProject.id"));
        sic.add(new SelectorItemInfo("curProject.fullOrgUnit.id"));
        
		//合同编码规则，必须合同编码作为应用属性
		String bindingProperty = "codeType.number";
		ContractBillInfo info = (ContractBillInfo)_getValue(ctx, pk,sic);
		CostCenterOrgUnitInfo currentCostUnit = ContextUtil.getCurrentCostUnit(ctx);
		String curOrgId = currentCostUnit.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
        if ((info).getCodeType()!=null && iCodingRuleManager.isExist(info, curOrgId,bindingProperty)
        		&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId,bindingProperty)) {
            iCodingRuleManager.recycleNumber(info, curOrgId, bindingProperty,"",info.getNumber());
        }else {
        	curOrgId = (info).getCurProject().getFullOrgUnit().getId().toString();
        	
            if ((info).getCodeType()!=null && iCodingRuleManager.isExist(info, curOrgId,bindingProperty)
            		&& iCodingRuleManager.isUseIntermitNumber(info, curOrgId,bindingProperty)) {
                iCodingRuleManager.recycleNumber(info, curOrgId, bindingProperty,"",info.getNumber());
            }
        	
        }
		
		//		EntityViewInfo evi = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("id",pk.toString()));
//		evi.setFilter(filter);
//		evi.getSelector().add(new SelectorItemInfo("id"));
//		evi.getSelector().add(new SelectorItemInfo("number"));
//		evi.getSelector().add(new SelectorItemInfo("codeType.id"));
//		evi.getSelector().add(new SelectorItemInfo("codeType.number"));
//		ContractBillCollection cbc = this.getContractBillCollection(ctx,evi);
//		ContractBillInfo cbi = null;
//		if(cbc.size()!=0){
//			cbi = cbc.get(0);
//		}
//		
//		String orgId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
//		String bindingProperty = "codeType.number";
//		// 无当前组织，或者当前组织没定义编码规则，用集团的
//		if (orgId == null || orgId.trim().length() == 0) {			
//			orgId = OrgConstants.DEF_CU_ID;
//		}
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
//		if(!cbi.isIsArchived()){
//			//设置编码类型
//			EntityViewInfo evi = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			ContractTypeInfo cti = cbi.getContractType();
//			if(cti!=null){
//				cti = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo(new ObjectUuidPK(cti.getId().toString()));
//				if(cti.getLevel()!=1){
//					//取一级合同类别
//					String number = cti.getLongNumber();
//					if(number.indexOf("!") != -1) {
//						number = number.substring(0,number.indexOf("!"));
//					}
//					
//					cti = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo("select id where longNumber = '" + number + "'");
//				}
//			}
//			filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//所有合同,因为现在尚不知合同性质
//			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));//合同属于新增状态
//			evi.setFilter(filter);
//			ContractCodingTypeCollection cctc = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(evi);
//			if(cctc.size()!=0){
//				((ContractBillInfo)cbi).setCodeType(cctc.get(0));
				////
				// 有ID，且启用了编码规则
//		boolean isExists = false;
//		try {
//			isExists = iCodingRuleManager.isExist(cbi, orgId, bindingProperty);
//		}
//		catch (Exception e) {
//			System.out.println("FDC Contract generate number exception, exception not threw");
//		}
//				if (orgId != null && orgId.trim().length() > 0
//						&& isExists) {			
//					// 如果使用了"不允许断号"
//					if (iCodingRuleManager.isUseIntermitNumber(cbi, orgId, bindingProperty)) {
//						iCodingRuleManager.recycleNumber(cbi, orgId, bindingProperty, "", cbi.getNumber());			
//					}
//				}
		
//			}	
//		}else{//已归档
			//设置编码类型
//			EntityViewInfo evi = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			ContractTypeInfo cti = cbi.getContractType();
//			if(cti!=null){
//				cti = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo(new ObjectUuidPK(cti.getId().toString()));
//				if(cti.getLevel()!=1){
//					//取一级合同类别
//					String number = cti.getLongNumber();
//					if(number.indexOf("!") != -1) {
//						number = number.substring(0,number.indexOf("!"));
//					}
//					
//					cti = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo("select id where longNumber = '" + number + "'");
//				}
//			}
//			filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//所有合同,因为现在尚不知合同性质
//			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.STORE_VALUE));//合同属于归档状态
//			evi.setFilter(filter);
//			ContractCodingTypeCollection cctc = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(evi);
//			if(cctc.size()!=0){
//				((ContractBillInfo)cbi).setCodeType(cctc.get(0));
				////
//				// 有ID，且启用了编码规则
//				if (orgId != null && orgId.trim().length() > 0
//						&& iCodingRuleManager.isExist(cbi, orgId, bindingProperty)) {			
//					// 如果使用了"不允许断号"
//					if (iCodingRuleManager.isUseIntermitNumber(cbi, orgId, bindingProperty)) {
//						iCodingRuleManager.recycleNumber(cbi, orgId, bindingProperty, "", cbi.getNumber());			
//					}
//				}
//			
////			}	
//		
//		}
	}
	
	protected void handleIntermitNumber(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
	
		FilterInfo filter = null;
		int i=0;
		do {
			handleIntermitNumber1(ctx, info);
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", info.getNumber()));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
			filter.getFilterItems()
					.add(new FilterItemInfo("orgUnit.id", info.getOrgUnit().getId()));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (_exists(ctx, filter) && i<100);
	}
	
	protected void handleIntermitNumber1(Context ctx, FDCBillInfo info) throws BOSException, CodingRuleException, EASBizException
	{
		ContractBillInfo cbi = (ContractBillInfo)info;
		String orgId = cbi.getOrgUnit()!=null? cbi.getOrgUnit().getId().toString():
			ContextUtil.getCurrentCostUnit(ctx).getId().toString();
		String bindingProperty = "codeType.number";
		// 无当前组织，或者当前组织没定义编码规则，用集团的
		if (orgId == null || orgId.trim().length() == 0) {			
			orgId = OrgConstants.DEF_CU_ID;
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		//
		if(!cbi.isIsArchived()){
			//设置编码类型
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ContractTypeInfo cti = cbi.getContractType();
			if(cbi.getContractType()!=null){
				if(cti.getLevel()!=1){
					//取一级合同类别
					String number = cbi.getContractType().getLongNumber();
					if(number.indexOf("!") != -1) {
						number = number.substring(0,number.indexOf("!"));
					}
					
					cti = ContractTypeFactory.getLocalInstance(ctx).getContractTypeInfo("select id where longNumber = '" + number + "'");
				}
			}
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//所有合同,因为现在尚不知合同性质
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));//合同属于新增状态
			evi.setFilter(filter);
			ContractCodingTypeCollection cctc = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(evi);
			
			ContractCodingTypeInfo codingType = null;
			ContractCodingTypeInfo codingTypeAll = null;
			if(cctc.size()>0)
				codingType = cctc.get(0);
			
			filter = new FilterInfo();
			
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
			//新建的话，所有合同
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));
			//新增状态
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));
			evi.setFilter(filter);
			cctc = ContractCodingTypeFactory.getLocalInstance(ctx).getContractCodingTypeCollection(evi);
			if(cctc.size()>0)
				codingTypeAll = cctc.get(0); 
			
			/************
			 * 对于合同的编码规则，可以按照合同类型设置编码规则，还包括一个"全部"的编码类型
			 * 如果同时设置了全部，和单个类型的编码规则，会有冲突
			 * 所以在这里加入了下面这段代码
			 * 当合同的编码类型为某一个具体的类型时，上面的代码先取这个具体的类型对应的编码规则，如果没有取到[说明是没有设置编码规则，或没有启用]
			 * 则再取一遍"全部"类型对应的编码规则
			 * 即: 当明细的合同类型，没有设置编码规则的时候，取全部类型对应的编码规则[当在合同修改界面，修改合同类型时,会走此处]
			 */
			/****
			 * 成本中心，一级类型有对应的编码规则
			 */
			if(codingType!=null){
				cbi.setCodeType(codingType);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
			/***
			 * 成本中心，全部类型对应有编码规则
			 */
			if(codingTypeAll!=null){
				cbi.setCodeType(codingTypeAll);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
			orgId = cbi.getCurProject().getFullOrgUnit().getId().toString();
			/****
			 * 财务组织，一级类型有对应的编码规则
			 */
			if(codingType!=null){
				cbi.setCodeType(codingType);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
			/***
			 * 财务组织，全部类型对应有编码规则
			 */
			if(codingTypeAll!=null){
				cbi.setCodeType(codingTypeAll);
				if(setNumber(ctx,info, cbi, orgId, bindingProperty, iCodingRuleManager))
					return;
			}
		}
	}

	private boolean setNumber(Context ctx,FDCBillInfo info, ContractBillInfo cbi, String orgId, String bindingProperty, ICodingRuleManager iCodingRuleManager) throws BOSException, EASBizException {
		if (orgId != null && orgId.trim().length() > 0
				&& iCodingRuleManager.isExist(info, orgId, bindingProperty)) {			
			// 如果使用了"不允许断号"
			if (iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty)) {
				String numberTemp = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
				info.setNumber(numberTemp);				
			} else if (iCodingRuleManager.isAddView(info, orgId, bindingProperty)){				
				// 判断是否修改了编码,是否改大了顺序号
				if (iCodingRuleManager.isModifiable(info, orgId, bindingProperty)) {					
					iCodingRuleManager.checkModifiedNumber(info, orgId,info.getNumber(), bindingProperty);
				}				
			} else {
				// 什么都没选,新增不显示,允许断号,业务传空number值,在此设置number
//				if(info.getNumber()==null||info.getNumber().equals("")){
					String numberTemp = iCodingRuleManager.getNumber(info,orgId, bindingProperty, "");
					info.setNumber(numberTemp);
//				}
			}
			return true;
		}
		return false;
	}
	
	protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		setCreatorPosition(ctx, (ContractBillInfo) model);
		IObjectPK objectPK = super._addnew(ctx, model);
		
//		同步到合同基本资料，用于合同做核算项目
		ContractBaseDataHelper.synToContractBaseData(ctx, false, objectPK.toString());
		
    	/**添加反写ContractBaseDataID的代码 -by neo**/
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(
				"update t_con_contractbill set fcontractbasedataid =" +
				"(select fid from t_CON_contractbasedata where fcontractid = t_con_contractbill.fid) " +
				"where");
		builder.appendParam("fid", objectPK.toString());
		builder.executeUpdate(ctx);
		return objectPK;
		/**添加反写ContractBaseDataID的代码**/
	}

	protected void _updatePartial(Context ctx, IObjectValue model, SelectorItemCollection selector) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		super._updatePartial(ctx, model, selector);
		
//		同步到合同基本资料，用于合同做核算项目
		String id = ((FDCBillInfo)model).getId().toString();
		ContractBaseDataHelper.synToContractBaseData(ctx, false, id);

	}
	
	/**
	 * 检查合同是否关联框架合约
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
		builder.appendSql("select fprogrammingContract from T_CON_CONTRACTBILL where 1=1 and ");
		builder.appendParam("fid", pk.toString());
		IRowSet rowSet = builder.executeQuery();
		while (rowSet.next()) {
			if (rowSet.getString("fprogrammingContract") != null) {
				return rowSet.getString("fprogrammingContract").toString();
			}
		}
		return null;
	}

	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(Context ctx, String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder(ctx);
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			e.printStackTrace();
		}
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
			updateProgrammingContract(ctx, checkReaPre, 0);
		}
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
		buildSQL.appendSql(" select count(1) count from T_CON_ContractBill ");
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
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {

		//检查是否被变更审批单引用
		this._isReferenced(ctx,pk);
		//主合同删除则删除所有的合同预估变动单
		//补充合同删除则删除分录关联的预估合同变动单 20120331
//	 	SelectorItemCollection sel = new SelectorItemCollection();
//	   	sel.add("*");
//	   	sel.add("entrys.*");
//	    IContractEstimateChangeBill iFaced =  ContractEstimateChangeBillFactory.getLocalInstance(ctx);
//	   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
//	   if(ContractPropertyEnum.DIRECT.equals(contractInfo.getContractPropert())){//主合同 删除则删除相关联的预估合同变动单,合约规划的预估金额清零
//		   EntityViewInfo view1 = new EntityViewInfo();
//		   FilterInfo Filter1 = new FilterInfo();
//		   Filter1.getFilterItems().add(new FilterItemInfo("contractbill.id",pk.toString()));
//		   view1.setFilter(Filter1);
//		   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//		   for(int i = 0 ; i < coll1.size() ; i++){
//			   ContractEstimateChangeBillInfo info1 = coll1.get(i);
//			   iFaced.delete(new ObjectUuidPK(info1.getId()));
//		   }
//	   }else if(ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){//补充合同 删除则删除预估合同变动单,合约规划的预估金额清零
//		   ContractBillEntryCollection entryColl = contractInfo.getEntrys();
//		   ContractBillEntryInfo entryInfo= null;
//		  for(int i = 0 ; i < entryColl.size(); i++){
//			  entryInfo = entryColl.get(i);
//			  if("esId".equalsIgnoreCase(entryInfo.getRowKey())){
//				  EntityViewInfo view1 = new EntityViewInfo();
//				   FilterInfo Filter1 = new FilterInfo();
//				   Filter1.getFilterItems().add(new FilterItemInfo("suppyContractId",entryInfo.getContent()));
//				   view1.setFilter(Filter1);
//				   ContractEstimateChangeBillCollection coll1 = iFaced.getContractEstimateChangeBillCollection(view1);
//				   for(int j = 0 ; j < coll1.size() ; j++){
//					   ContractEstimateChangeBillInfo info1 = coll1.get(j);
//					   iFaced.delete(new ObjectUuidPK(info1.getId()));
//				   }
//				  
//				  break;
//			  }
//		  }
//			   
//	   }
		
//		//关联生成合同状态变化及时更新中标通知书和招标预拆分
//		ContractBillAcceptanceLetterHelper.acceptanceLetterDeleteHelper(ctx, pk);
		try {
			SelectorItemCollection sel = new SelectorItemCollection();
		   	sel.add("contractPropert");
			ContractBillInfo contractInfo=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
			if(!ContractPropertyEnum.SUPPLY.equals(contractInfo.getContractPropert())){
				updateOldProg(ctx,pk);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		
/*		//同步删除合同基本资料
		String sql = "delete t_con_contractbasedata where fcontractid = ?";
		
		DbUtil.execute(ctx, sql, new Object[]{pk.toString()});*/
		//ormap 删除的时候会检查引用关系
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("contractId", pk.toString());
		ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		
//		//删除 返还合同金额至合约规划
//		SelectorItemCollection sel = new SelectorItemCollection();
//	   	sel.add("*");
//	   	sel.add("programmingContract.*");
//	   	ContractBillInfo contractInfo =  ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
//		ProgrammingContractInfo proInfo = contractInfo.getProgrammingContract();
//		if(proInfo!=null){
//			BigDecimal balance = proInfo.getBalance();
//			balance = balance.add(contractInfo.getAmount());
//			IProgrammingContract iFaced = ProgrammingContractFactory.getLocalInstance(ctx);
//			SelectorItemCollection sel1 = new SelectorItemCollection();
//			sel1.add("balance");
//			proInfo.setBalance(balance);
//			iFaced.updatePartial(proInfo, sel1);
//		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "SELECT FID FROM T_CON_CONTRACTCONTENT WHERE FCONTRACTID = '"+pk.toString()+"'";
		builder.appendSql(sql);
		IRowSet rowSet = builder.executeQuery();
		Set idSet = new HashSet();
		try {
			while(rowSet.next()){
				idSet.add(rowSet.getString("FID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(idSet.size()>0){
			FilterInfo delfilter = new FilterInfo();
			delfilter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
			ContractContentFactory.getLocalInstance(ctx).delete(delfilter);
		}
		
		SelectorItemCollection sel = new SelectorItemCollection();
	   	sel.add("agreementID");
		ContractBillInfo contractInfo=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk, sel);
		if(contractInfo.getAgreementID()!=null){
			deleteAttachment(ctx,contractInfo.getAgreementID());
		}
		deleteAttachment(ctx,pk.toString());
		super._delete(ctx, pk);	
	}
	protected void deleteAttachment(Context ctx,String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getLocalInstance(ctx).delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
				}
			}
		}
	}
	
	
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		if(arrayPK != null && arrayPK.length > 0) {
			Set idSet=new HashSet(arrayPK.length);
			for (int i = 0; i < arrayPK.length; i++) {
				idSet.add(arrayPK[i].toString());
				
				delete(ctx, arrayPK[i]);
			}
			
/*			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("delete t_con_contractbasedata where ");
			builder.appendParam("fcontractid", ids);
			
			builder.executeUpdate(ctx);*/
			
			//ormap 删除的时候会检查引用关系
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractId",idSet,CompareType.INCLUDE));
			ContractBaseDataFactory.getLocalInstance(ctx).delete(filter);
		}
	}

	//选择不计成本的金额为否,金额录在分录上,显示的时候要从分录上取
	protected Map _getAmtByAmtWithoutCost(Context ctx, Map idMap) throws BOSException, EASBizException {

		if(idMap==null){
			return null;
		}
		
		Map amountMap = new HashMap();
		String sql = "select FContent from T_CON_ContractBillEntry where fparentid = ? and FRowkey = ?";
		Set set = idMap.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String id = (String)it.next();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql(sql);
			builder.addParams(new Object[] { id,  "am" });
			ResultSet resultSet = builder.executeQuery();
			try {
				if (resultSet.next()) {
					String cont = resultSet.getString("FContent");
					if (cont != null && cont.length() > 0) {
						BigDecimal amt = new BigDecimal(cont);
						amountMap.put(id,amt)	;		
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		
		return amountMap;
	}
	
	//单据序时簿编辑界面初始数据粗粒度方法
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		Map initMap = super._fetchInitData(ctx,paramMap);
		return initMap;
	}
	
	//初始化工程项目
	protected void  initProject(Context ctx, Map paramMap,Map initMap) throws EASBizException, BOSException{
		String projectId = (String) paramMap.get("projectId");
		CurProjectInfo curProjectInfo = null;
		if(projectId == null && paramMap.get("contractBillId")!=null) {
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
			selector.add("curProject.fullOrgUnit.code");
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("curProject.CU.code");
			selector.add("curProject.landDeveloper.number");
			selector.add("curProject.landDeveloper.name");
			selector.add("curProject.costCenter.number");
			selector.add("curProject.costCenter.name");
			selector.add("curProject.costCenter.code");
			selector.add("curProject.costCenter.displayName");
			selector.add("curProject.costCenter.longNumber");
			selector.add("curProject.isWholeAgeStage");
			selector.add("respDept.number");
			selector.add("respDept.name");
			selector.add("partB.number");
			selector.add("partB.name");
			
			ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
			getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);

			//工程项目
			projectId = (String) contractBill.getCurProject().getId().toString();
			
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
			selector.add("CU.code");
			selector.add("isWholeAgeStage");
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),selector);
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
		
//		super.initProject(ctx, paramMap,initMap);
	}

	protected boolean _contractBillAntiStore(Context ctx, List idList) throws BOSException, EASBizException {
	
		String sql = "update T_CON_ContractBill set  FIsArchived = 0 where FID = ? ";
		Object[] params = null;
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			params = new Object[]{id};
			
			DbUtil.execute(ctx,sql,params);
			    
		}
		
		return true;
	}

	protected boolean _isContractSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("splitState"));
        sic.add(new SelectorItemInfo("orgUnit.id"));
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx,pk,sic);
		
		HashMap param = FDCUtils.getDefaultFDCParam(ctx,contractBillInfo.getOrgUnit().getId().toString());		
		boolean splitBeforeAudit = false;
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
			splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
			
			if(!splitBeforeAudit){
				return true;
			}
		}		
		
		return CostSplitStateEnum.ALLSPLIT.equals(contractBillInfo.getSplitState());
	}

	//工作流拆分驱动
	protected void _split(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		
	}

	//自动删除拆分
	protected boolean _autoDelSplit(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//审核的合同不能自动删除拆分
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("state"));
		ContractBillInfo contractBillInfo = 
			ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk,sic);
		if(FDCBillStateEnum.AUDITTED.equals(contractBillInfo.getState())){
			//throw new ContractException(ContractException.HASAUDIT);
			return false;
		}
		
		//成本拆分
		CostSplitBillAutoAuditor.autoDelete(ctx,pk, "T_CON_ContractCostSplit", "FContractBillID");
		//财务拆分
		CostSplitBillAutoAuditor.autoDelete(ctx,pk, "T_CON_ConNoCostSplit", "FContractBillID");
		String contractid=pk.toString();
		com.kingdee.eas.fdc.basedata.FDCSQLBuilder builder=new com.kingdee.eas.fdc.basedata.FDCSQLBuilder(ctx);
/*		builder.appendSql("delete from T_Con_ContractCostSplitEntry where fparentid in (select fid from T_Con_ContractCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();
		builder.appendSql("delete from T_Con_ContractCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();
		builder.appendSql("delete from T_CON_ConNoCostSplitEntry where fparentid in (select fid from T_CON_ConNoCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();
		builder.appendSql("delete from T_CON_ConNoCostSplit where fcontractbillid=?)");
		builder.addParam(contractid);
		builder.execute();
		builder.clear();*/
		builder.appendSql("update T_Con_ContractBill set fsplitstate='1NOSPLIT' where fid=?");
		builder.addParam(contractid);
		builder.execute();
		return true;
	}

	//获取合同类型编码
	protected String _getContractTypeNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		//
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("contractType.number"));
        sic.add(new SelectorItemInfo("contractType.longNumber"));
		ContractBillInfo contractBillInfo = 
			ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(pk,sic);
		
		return contractBillInfo.getContractType().getLongNumber().replace('!','.');
	}
	
	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.contract.app.AbstractContractBillControllerBean#_getOtherInfo(com.kingdee.bos.Context, java.util.Set)
	 */
	protected Map _getOtherInfo(Context ctx, Set contractIds)
			throws BOSException, EASBizException {
		if(contractIds==null||contractIds.size()==0){
			return new HashMap();
		}
		TimeTools.getInstance().msValuePrintln("start");
		Map auditMap = FDCBillWFFacadeFactory.getLocalInstance(ctx).getWFBillLastAuditorAndTime(contractIds);
		TimeTools.getInstance().msValuePrintln("end auditMap");

		/**
		 * 填充正文列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有正文附件就进行标记，否则不标记
		 */
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcontractid from t_con_contractcontent where ");
		builder.appendParam("fcontractid", contractIds.toArray());
		IRowSet rowSet=builder.executeQuery();
		Map contentMap=new HashMap();
		Map attachMap=new HashMap();
		try {
			while (rowSet.next()) {
				contentMap.put(rowSet.getString("fcontractid"), Boolean.TRUE);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		/**
		 * 由于肖飙彪在保存正文的时候想要整个房地产系统各的业务单据都可用便没有直接使用之前系统已有的contract这个字段为正文的业务单据父体而是新加了
		 * 字段parent，所以保存的时候依据不同，之后查看修改的时候可能就会产生很多问题。对于ID不同步的问题我是在启用或者禁用参数"房地产业务系统正文管理启用审批笔迹留痕功能"
		 * 解决的，也就是说如果没有手动启用或者是禁用参数的话我们的ID还是存在不同步的问题，而最直接的影响就是在合同录入序时簿和合同查询序时簿上的"正文"一列的信息显示不准确
		 * 由于目前咱们这个房地产系统就只有合同模块用了该功能，所以FParent其实就是合同的ID(FContractId)，那解决该问题就只以FParent为依据再找一遍咯！烦。。 by Cassiel_peng 2009-9-6
		 */
		builder.clear();
		builder.appendSql("select FParent from t_con_contractcontent where ");
		builder.appendParam("FParent", contractIds.toArray());
		IRowSet rowSet1=builder.executeQuery();
		try {
			while (rowSet1.next()) {
				contentMap.put(rowSet1.getString("FParent"), Boolean.TRUE);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
					
		TimeTools.getInstance().msValuePrintln("end contentMap");
		/**
		 * 填充附件列,从列表中获取合同的ID（表中的ID就是合同本身的ID）
		 * 然后通过设置过滤信息后使用远程调用，查看合同正文附件集合中是否有合同的ID.
		 * 如果该合同有业务相关附件就进行标记，否则不标记
		 */
		builder.clear();
		builder.appendSql("select fboid from t_bas_boattchasso where ");
		builder.appendParam("fboid", contractIds.toArray());
		rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				attachMap.put(rowSet.getString("fboid"),Boolean.TRUE);
			}	
		}catch(SQLException e){
			throw new BOSException(e);
		}
		TimeTools.getInstance().msValuePrintln("end attachMap");
		Map retMap=new HashMap();
		retMap.put("contentMap", contentMap);
		retMap.put("attachMap", attachMap);
		retMap.put("auditMap", auditMap);
		return retMap;
	}
	
	// 仅仅在同一个明细“工程项目”下才做合同名称唯一性检查，跨项目分期、登录跨组织的合同都不用检查合同名称唯一性。
	// R101129-229 Added by Owen_wen
	//修改前：由于设定了工程项目与成本中心对应关系，合同单据billInfo会通过getOrgUnit()归到某个组织下，该组织下的合同单据是不允许重名的
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {
		ContractBillInfo contractBill = (ContractBillInfo) billInfo;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("name", contractBill.getName()));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", contractBill.getCurProject().getId()));
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", contractBill.getId().toString(), CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NAME_DUP);
		}
	}

	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		String conId = pk.toString();
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		ContractBillInfo billInfo = ContractBillFactory.getLocalInstance(ctx).
		getContractBillInfo(pk,selector);
		
		//如果存在补充合同，也作废
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	    builder.appendSql("select sup.fid from t_con_contractbill sup ");
	    builder.appendSql("inner join t_con_contractbillentry supe on supe.fparentid=sup.fid ");
	    builder.appendSql("inner join t_con_contractbill main on main.fid=supe.fcontent ");
	    builder.appendSql("where main.fid=?");
	    builder.addParam(conId);
	    IRowSet rs = builder.executeQuery();
	    try{
	    	while(rs.next()&&rs.size()==1){
	    		String supId = rs.getString("fid");
	    		
	    		ContractBillInfo supInfo= ContractBillFactory.getLocalInstance(ctx)
						.getContractBillInfo(new ObjectUuidPK(BOSUuid.read(supId)), selector);
	    		if(supInfo.getState()==FDCBillStateEnum.SUBMITTED || supInfo.getState()==FDCBillStateEnum.AUDITTING){
		    		// 清空成本拆分
		    		FilterInfo filter = new FilterInfo();
		    		filter.appendFilterItem("contractBill.id", supId);
		    		// 删除付款拆分
		    		PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// 删除结算拆分
		    		SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// 删除变更拆分
		    		ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
		    		// 删除合同拆分
		    		ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
	    		}
	    		super._cancel(ctx,  new ObjectUuidPK(BOSUuid.read(supId)));
	    		
	    	}
	    }catch(Exception e){
	    	throw new BOSException(e);
	    }

	    if(billInfo.getState()==FDCBillStateEnum.SUBMITTED || billInfo.getState()==FDCBillStateEnum.AUDITTING){
    		// 清空成本拆分
    		FilterInfo filter = new FilterInfo();
    		filter.appendFilterItem("contractBill.id", conId);
    		// 删除付款拆分
    		PaymentSplitFactory.getLocalInstance(ctx).delete(filter);
    		// 删除结算拆分
    		SettlementCostSplitFactory.getLocalInstance(ctx).delete(filter);
    		// 删除变更拆分
    		ConChangeSplitFactory.getLocalInstance(ctx).delete(filter);
    		// 删除合同拆分
    		ContractCostSplitFactory.getLocalInstance(ctx).delete(filter);
		}
		
		super._cancel(ctx, pk);
	}
	protected void _synUpdateProgramming(Context ctx, String billId,IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("amount");
		ContractBillInfo contractBillInfo = this.getContractBillInfo(ctx, new ObjectUuidPK(billId), sel);
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcBalance = pcInfo.getBalance();
		BigDecimal pcSignUpAmount=pcInfo.getSignUpAmount();
		BigDecimal wtAmount=pcInfo.getSignUpAmount();
		BigDecimal amount = contractBillInfo.getAmount();
		
		pcInfo.setBalance(FDCHelper.subtract(pcBalance, amount));
		pcInfo.setBudgetAmount(FDCHelper.ZERO);
		pcInfo.setSignUpAmount(FDCHelper.add(pcSignUpAmount, amount));
		pcInfo.setBillId(billId);
		
		SelectorItemCollection ecSel = new SelectorItemCollection();
		ecSel.add("estimateAmount");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if(pcInfo.getSrcId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("isLastest",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getSrcId()));
		    view.setFilter(filter);
		    view.setSelector(ecSel);
		    ContractEstimateChangeBillCollection ecCol = ContractEstimateChangeBillFactory.getLocalInstance(ctx).getContractEstimateChangeBillCollection(view);
		    if(ecCol.size()>0){
		    	ContractEstimateChangeBillInfo ecBill = ecCol.get(0);
			    BigDecimal estimateAmount = ecBill.getEstimateAmount();
//			    pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), estimateAmount));
			    pcInfo.setEstimateAmount(estimateAmount);
		    }
		}
		SelectorItemCollection ccbsel = new SelectorItemCollection();
		ccbsel.add("amount");
		ccbsel.add("exRate");
		ccbsel.add("state");
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",billId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.ANNOUNCE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.VISA_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3)");
		view.setFilter(filter);
		view.setSelector(ccbsel);
		ContractChangeBillCollection col=ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(view);
		for(int i=0;i<col.size();i++){
			BigDecimal changeAmount = FDCHelper.ZERO;
			
			SelectorItemCollection cssel = new SelectorItemCollection();
			cssel.add("allowAmount");
			EntityViewInfo csview=new EntityViewInfo();
			FilterInfo csfilter=new FilterInfo();
			csfilter.getFilterItems().add(new FilterItemInfo("conChangeBill.id",col.get(i).getId().toString()));
			csfilter.getFilterItems().add(new FilterItemInfo("contractBill.id",billId));
			csfilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			csview.setFilter(csfilter);
			csview.setSelector(cssel);
			
			ContractChangeSettleBillCollection settlecol=ContractChangeSettleBillFactory.getLocalInstance(ctx).getContractChangeSettleBillCollection(csview);
			if(settlecol.size()>0){
	        	changeAmount=settlecol.get(0).getAllowAmount().multiply(col.get(i).getExRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
				changeAmount=col.get(i).getAmount();
			}
			pcInfo.setBalance(FDCHelper.subtract(pcInfo.getBalance(), changeAmount));
			pcInfo.setChangeAmount(FDCHelper.add(pcInfo.getChangeAmount(), changeAmount));
			pcInfo.setEstimateAmount(FDCHelper.subtract(pcInfo.getEstimateAmount(), changeAmount));
		}
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			IRowSet rowSet = null;
			builder.appendSql(" select entry.fcontent as amount,con.fid as id from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
			builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
			builder.appendSql(" where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
			builder.appendParam(" parent.fid", billId);
			rowSet = builder.executeQuery();
			
			while (rowSet.next()) {
				String supplyId=rowSet.getString("id");
				pcInfo.setEstimateAmount(FDCHelper.subtract(pcInfo.getEstimateAmount(), rowSet.getBigDecimal("amount")));
				SelectorItemCollection supplySel=new SelectorItemCollection();
				supplySel.add("programmingContract");
				ContractBillInfo supply=new ContractBillInfo();
				supply.setId(BOSUuid.read(supplyId));
				supply.setProgrammingContract(pcInfo);
				ContractBillFactory.getLocalInstance(ctx).updatePartial(supply, supplySel);
				
				pcInfo.setIsCiting(true);
			}
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SelectorItemCollection seesel = new SelectorItemCollection();
		seesel.add("curSettlePrice");
		view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",billId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		view.setSelector(seesel);
		ContractSettlementBillCollection seecol=ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(view);
		for(int i=0;i<seecol.size();i++){
			pcInfo.setBalance(FDCHelper.subtract(pcInfo.getAmount(),FDCHelper.add(wtAmount, seecol.get(i).getCurSettlePrice())));
			if(pcInfo.getSettleAmount()!=null){
				pcInfo.setSettleAmount(FDCHelper.add(pcInfo.getSettleAmount(), seecol.get(i).getCurSettlePrice()));
			}else{
				pcInfo.setSettleAmount(seecol.get(0).getCurSettlePrice());
			}
			pcInfo.setEstimateAmount(FDCHelper.ZERO);
		}
		pcInfo.setIsCiting(true);
		
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("budgetAmount");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("estimateAmount");
		sict.add("isCiting");
		sict.add("isWTCiting");
		sict.add("billId");
	    service.updatePartial(pcInfo, sict);
	}
	protected void _synReUpdateProgramming(Context ctx, String billId, IObjectValue programming) throws BOSException, EASBizException {
		if(programming==null) return;
		IProgrammingContract service = ProgrammingContractFactory.getLocalInstance(ctx);
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo)programming;
		
		BigDecimal pcAmount=pcInfo.getAmount();
		
		SelectorItemCollection wtSel = new SelectorItemCollection();
		wtSel.add("amount");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		view.setSelector(wtSel);
		ContractWithoutTextCollection col=ContractWithoutTextFactory.getLocalInstance(ctx).getContractWithoutTextCollection(view);
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<col.size();i++){
			amount =FDCHelper.add(amount,col.get(i).getAmount()) ;
		}
		pcInfo.setBalance(FDCHelper.subtract(pcAmount, amount));
		if(pcInfo.getBalance().compareTo(pcAmount)==0){
			pcInfo.setBudgetAmount(pcInfo.getAmount());
		}
		pcInfo.setSignUpAmount(amount);
		
		pcInfo.setChangeAmount(FDCHelper.ZERO);
		pcInfo.setSettleAmount(FDCHelper.ZERO);
		pcInfo.setEstimateAmount(FDCHelper.ZERO);
		
		pcInfo.setIsCiting(false);
		pcInfo.setBillId(null);
		
		SelectorItemCollection sict = new SelectorItemCollection();
		sict.add("balance");
		sict.add("budgetAmount");
		sict.add("signUpAmount");
		sict.add("changeAmount");
		sict.add("settleAmount");
		sict.add("estimateAmount");
		sict.add("isCiting");
		sict.add("billId");
		service.updatePartial(pcInfo, sict);
		
		try {
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			IRowSet rowSet = null;
			builder.appendSql(" select entry.fcontent as amount,con.fid as id from t_con_contractbillentry entry inner join t_con_contractbill con on con.fid = entry.fparentid   and con.fisAmtWithoutCost=1 and");
			builder.appendSql(" con.fcontractPropert='SUPPLY'  inner join T_Con_contractBill parent on parent.fnumber = con.fmainContractNumber  and  parent.fcurprojectid=con.fcurprojectid   ");
			builder.appendSql(" where  entry.FRowkey='am' and con.fstate='4AUDITTED' and ");
			builder.appendParam(" parent.fid", billId);
			rowSet = builder.executeQuery();
			
			while (rowSet.next()) {
				String supplyId=rowSet.getString("id");
				SelectorItemCollection supplySel=new SelectorItemCollection();
				supplySel.add("programmingContract");
				ContractBillInfo supply=new ContractBillInfo();
				supply.setId(BOSUuid.read(supplyId));
				supply.setProgrammingContract(null);
				ContractBillFactory.getLocalInstance(ctx).updatePartial(supply, supplySel);
			}
		} catch (UuidException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void _costIndex(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		
	}
}