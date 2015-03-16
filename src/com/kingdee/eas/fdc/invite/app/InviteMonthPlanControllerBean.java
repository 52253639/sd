package com.kingdee.eas.fdc.invite.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection;
import com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysInfo;
import com.kingdee.eas.fdc.invite.InviteMonthPlanFactory;
import com.kingdee.eas.fdc.invite.InviteMonthPlanInfo;
import com.kingdee.eas.fdc.invite.InviteProjectEntriesFactory;
import com.kingdee.util.NumericExceptionSubItem;

public class InviteMonthPlanControllerBean extends AbstractInviteMonthPlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InviteMonthPlanControllerBean");
    
    
   /* private void changeLatest( Context ctx, BOSUuid billId ) {
    	
    }
    
    public void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	ObjectUuidPK pk=new ObjectUuidPK(billId);
    	InviteMonthPlanInfo info=this.getInviteMonthPlanInfo(ctx, pk);
    	
    	if( info.getVersion().compareTo(new BigDecimal(2)) >= 0 ){//有历史版本,新建的修订版本未设置为最新，
    		EntityViewInfo view = new EntityViewInfo();
    		SelectorItemCollection sic = new SelectorItemCollection();
    		sic.add("isLatest");
    		
    		FilterInfo filter=new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
    		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion().subtract(new BigDecimal(1))));
    		filter.getFilterItems().add(new FilterItemInfo("planYear",info.getPlanYear()));
    		filter.getFilterItems().add(new FilterItemInfo("planMonth",info.getPlanMonth()));
    		
    		InviteMonthPlanCollection olderColl = InviteMonthPlanFactory.getLocalInstance(ctx).getInviteMonthPlanCollection(view);
    		if(olderColl!= null && olderColl.size()>0 ) {
    			olderColl.get(0).setIsLatest(false);    
    			_updatePartial(ctx, olderColl.get(0), sic);
    		}
    	}
    	
    	info.setIsLatest(true);
    	
    }*/
    
    public Map checkLeaf(Context ctx, BOSUuid billId) throws EASBizException, BOSException {
    	HashMap<String, Boolean> map = new HashMap<String, Boolean>();
    	ObjectUuidPK pk=new ObjectUuidPK(billId);
    	InviteMonthPlanInfo info=this.getInviteMonthPlanInfo(ctx, pk);
    	
    	InviteMonthPlanEntrysCollection entryCol = info.getEntry();
    	for(int i=0; i<entryCol.size(); i++) {
    		InviteMonthPlanEntrysInfo entry = entryCol.get(i);
    		
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("parent.id",entry.getProgrammingContractID().toString()));
    		if(ProgrammingContractFactory.getLocalInstance(ctx).exists(filter)){
    			 map.put(entry.getProgrammingContractID().toString(), Boolean.FALSE);
    		}else {
    			 map.put(entry.getProgrammingContractID().toString(), Boolean.TRUE);
    		}
    	}
    	return map;
    }
    
    @SuppressWarnings("unchecked")
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	ObjectUuidPK pk=new ObjectUuidPK(billId);
    	InviteMonthPlanInfo info=this.getInviteMonthPlanInfo(ctx, pk);
    	FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",info.getProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
//		filter.getFilterItems().add(new FilterItemInfo("planYear",info.getPlanYear()));
//		filter.getFilterItems().add(new FilterItemInfo("planMonth",info.getPlanMonth()));
		if(InviteMonthPlanFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","非最新版本不能进行反审批操作！"));
		}
		InviteMonthPlanEntrysCollection coll = info.getEntry();
		Iterator<InviteMonthPlanEntrysInfo> iter = coll.iterator();
		Set<String> idSet = new HashSet<String>(); 
		
		while(iter.hasNext()) {
			idSet.add( iter.next().getProgrammingContractID().toString() );
		}
		
		FilterInfo filter2=new FilterInfo();
		filter2.getFilterItems().add(new FilterItemInfo("programmingContract.id", idSet, CompareType.INCLUDE ));
		if(InviteProjectEntriesFactory.getLocalInstance(ctx).exists(filter2)) {
			throw new EASBizException(new NumericExceptionSubItem("100","招标立项已关联过月度采购计划，不允许反审批！"));
		}
		
		
		FDCBillInfo billInfo = new FDCBillInfo();

		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		_updatePartial(ctx, billInfo, selector);
    }
	protected boolean isUseName() {
		return true;
	}

	@Override
	protected Map _checkLeaf(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		return this.checkLeaf(ctx, billId);
	}
}