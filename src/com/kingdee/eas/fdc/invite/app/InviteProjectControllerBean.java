package com.kingdee.eas.fdc.invite.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.InviteBidEvaluationFactory;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryFactory;
import com.kingdee.eas.fdc.invite.InviteChangeFormFactory;
import com.kingdee.eas.fdc.invite.InviteClarifyFactory;
import com.kingdee.eas.fdc.invite.InviteDocumentsCollection;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysInfo;
import com.kingdee.eas.fdc.invite.InviteMonthPlanFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanInfo;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesCollection;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningFactory;
import com.kingdee.eas.fdc.invite.InviteTimeConsultFactory;
import com.kingdee.eas.fdc.invite.MaterialSampleFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.TenderAccepterResultFactory;
import com.kingdee.util.NumericExceptionSubItem;

public class InviteProjectControllerBean extends AbstractInviteProjectControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteProjectControllerBean");

    protected boolean checkDelete(Context ctx, String billId) throws BOSException, EASBizException {
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",billId));
    	if(InviteChangeFormEntryFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(SupplierQualifyFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(InviteFixHeadFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(InviteTenderPlanningFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(InviteDocumentsFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(SupplierInviteRecordFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(InviteClarifyFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(InviteTimeConsultFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(MaterialSampleFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(InviteBidEvaluationFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}else if(TenderAccepterResultFactory.getLocalInstance(ctx).exists(filter)) {
    		return false;
    	}
    	return true;
    }
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		if( !this.checkDelete(ctx, pk.toString()) ) {
			throw new EASBizException(new NumericExceptionSubItem("100","有关联此招标立项的单据，不能删除！"));
		}
		super._delete(ctx, pk);
	}
	public IObjectCollection getValidProgrammingContract(Context ctx, IObjectCollection purchasePlan) throws BOSException, EASBizException {
		return this._getValidProgrammingContract(ctx, purchasePlan);
	}
	protected IObjectCollection _getValidProgrammingContract(Context ctx, IObjectCollection purchasePlan) throws BOSException, EASBizException {
		ProgrammingContractCollection results = new ProgrammingContractCollection();
		if(purchasePlan instanceof InviteMonthPlanEntrysCollection) {
			InviteMonthPlanEntrysCollection planColl = (InviteMonthPlanEntrysCollection) purchasePlan;
			for( int i=0; i<planColl.size(); i++ ) {
				InviteMonthPlanEntrysInfo info = planColl.get(i);
				String pcID = info.getProgrammingContractID().toString();
				InviteMonthPlanInfo monthPlan = InviteMonthPlanFactory.getLocalInstance(ctx).getInviteMonthPlanInfo(new ObjectUuidPK(info.getParent().getId()));
				
				String prjID = monthPlan.getProject().getId().toString();
				
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("project.id",prjID));
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcID));
				view.setFilter(filter);
				InviteProjectEntriesCollection coll = InviteProjectEntriesFactory.getLocalInstance(
						ctx).getInviteProjectEntriesCollection(view);
				if(coll.size() > 0 ) {
					continue;
				}
				
//				boolean leaf = true;
        		FilterInfo filter2 = new FilterInfo();
            	filter2.getFilterItems().add(new FilterItemInfo("parent.id",pcID));
                if(ProgrammingContractFactory.getLocalInstance(ctx).exists(filter2)){//非叶子节点
                	throw new EASBizException(new NumericExceptionSubItem("100","只能选择最详细节点！"));
                }
				
                //这里超级无语。。。当某父节点对应的招标立项有招标文件审批日期或合同审批，其下所有节点不可选择。。
                EntityViewInfo view4Contract = new EntityViewInfo();
				FilterInfo filter4Contract = new FilterInfo();
				filter4Contract.getFilterItems().add(new FilterItemInfo("programmingContract.id",pcID));
				filter4Contract.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
				view4Contract.setFilter(filter4Contract);
				view4Contract.getSelector().add("auditTime");
				view4Contract.setFilter(filter4Contract);
                
                ContractBillCollection contracts=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view4Contract);
                if(contracts.size()>0){//有合同审批日期 
//                	continue;
                }
                
                boolean haveDate = false;
                ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(pcID));
                
                while(pcInfo.getParent() != null ) {
                	ProgrammingContractInfo parentPcInfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(
                			new ObjectUuidPK(pcInfo.getParent().getId()));
                	
                	//检查有无合同审批日期。。。
                	filter4Contract = new FilterInfo();
                	filter4Contract.getFilterItems().add(new FilterItemInfo("programmingContract.id",parentPcInfo.getId().toString()));
    				filter4Contract.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    				view4Contract.setFilter(filter4Contract);
                	contracts=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(view4Contract);
                    if(contracts.size()>0){//有合同审批日期 
//                    	haveDate=true;
//						break;
                    }
                	
                	view = new EntityViewInfo();
    				filter = new FilterInfo();
    				filter.getFilterItems().add(new FilterItemInfo("project.id",prjID));
    				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",parentPcInfo.getId().toString()));
    				view.setFilter(filter);
    				coll = InviteProjectEntriesFactory.getLocalInstance(ctx).getInviteProjectEntriesCollection(view);
                	
    				if( coll.size() > 0 ) {
    					String invigteProjectId = coll.get(0).getParent().getId().toString();
    					
    					FilterInfo isExist = new FilterInfo();
    					isExist.getFilterItems().add( new FilterItemInfo("inviteProject.id",invigteProjectId) );
    					isExist.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
    					view.setFilter(isExist);
    					
    					SelectorItemCollection sic = new SelectorItemCollection();
    					sic.add("inviteProject.id");
    					sic.add("auditTime");
    					sic.add("state");
    					view.setSelector(sic);
    					
    					SorterItemCollection sorter = new SorterItemCollection();
    					SorterItemInfo sorterInfo = new SorterItemInfo("auditTime");
    					sorterInfo.setSortType(SortType.DESCEND);
    					view.setSorter(sorter);
    					
    					InviteDocumentsCollection documtns=InviteDocumentsFactory.getLocalInstance(ctx).getInviteDocumentsCollection(view);
    					if(documtns.size() > 0) {
    						haveDate=true;
    						break;
    					}
    				}
    				
    				pcInfo = parentPcInfo;
                }
                
                if(haveDate) {
                	continue;
                }
                
				ProgrammingContractInfo result = ProgrammingContractFactory.getLocalInstance(
						ctx).getProgrammingContractInfo(new ObjectUuidPK(pcID));
				results.add(result);
			}
			
		}
		
		return results;
	}
}