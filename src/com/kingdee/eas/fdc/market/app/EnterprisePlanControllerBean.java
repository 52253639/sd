package com.kingdee.eas.fdc.market.app;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.market.EnterprisePlanCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanInfo;
import com.kingdee.eas.fdc.market.EnterpriseSchemeEntryFactory;
import com.kingdee.eas.fdc.market.EnterpriseSchemeFactory;
import com.kingdee.eas.fdc.market.EnterpriseSchemeInfo;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanFactory;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanInfo;
import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
import com.kingdee.eas.fdc.market.MeasurePlanTargetInfo;
import com.kingdee.eas.fdc.market.ThemeEnum;
import com.kingdee.eas.fdc.market.ValueBreakCollection;
import com.kingdee.eas.fdc.market.ValueBreakInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class EnterprisePlanControllerBean extends AbstractEnterprisePlanControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.EnterprisePlanControllerBean");
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {

	}
    
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		EnterprisePlanInfo billInfo = (EnterprisePlanInfo) getValue(ctx,new ObjectUuidPK(billId));
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setUseing(true);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(new Date());
//		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("useing");
		
		_updatePartial(ctx, billInfo, selector);
//		
//		SelectorItemCollection getselector = new SelectorItemCollection();
//		getselector.add("themeState");
//		getselector.add("theme");
//		getselector.add("enDescribe");
//		
//		SelectorItemCollection updateEntrySel = new SelectorItemCollection();
//		updateEntrySel.add("entryState");
//		
//		Set delId=new HashSet();
//		for(int i=0;i<billInfo.getEntry().size();i++){
//			if(billInfo.getEntry().get(i).getEntryState().equals(ThemeEnum.Underway)){
//				EnterpriseSellPlanInfo sellPlan=EnterpriseSellPlanFactory.getLocalInstance(ctx).getEnterpriseSellPlanInfo(new ObjectUuidPK(billInfo.getEntry().get(i).getPalnTheme().getId()),getselector);
//				if(sellPlan.getThemeState().equals(ThemeEnum.Canceled)){
//					delId.add(sellPlan.getId());
//					
//					FilterInfo filter = new FilterInfo();
//					filter.getFilterItems().add(new FilterItemInfo("parent.id",billId));
//					filter.getFilterItems().add(new FilterItemInfo("palnTheme.theme",sellPlan.getTheme()));
//					filter.getFilterItems().add(new FilterItemInfo("palnTheme.enDescribe",sellPlan.getEnDescribe()));
//					filter.getFilterItems().add(new FilterItemInfo("id",billInfo.getEntry().get(i).getId(),CompareType.NOTEQUALS));
//					filter.getFilterItems().add(new FilterItemInfo("palnTheme.end",Boolean.FALSE));
//					
//					if(!EnterprisePlanEntryFactory.getLocalInstance(ctx).exists(filter)){
//						EntityViewInfo updateEnv = new EntityViewInfo();
//						FilterInfo updateFilter = new FilterInfo();
//						updateFilter.getFilterItems().add(new FilterItemInfo("parent.id",billId));
//						updateFilter.getFilterItems().add(new FilterItemInfo("palnTheme.theme",sellPlan.getTheme()));
//						updateFilter.getFilterItems().add(new FilterItemInfo("palnTheme.enDescribe",sellPlan.getEnDescribe()));
//						updateEnv.setFilter(updateFilter);
//						
//						EnterprisePlanEntryCollection coll = EnterprisePlanEntryFactory.getLocalInstance(ctx).getEnterprisePlanEntryCollection(updateEnv);
//						for(int j=0;j<coll.size();j++){
//							EntityViewInfo usenv = new EntityViewInfo();
//							FilterInfo usfilter = new FilterInfo();
//							usfilter.getFilterItems().add(new FilterItemInfo("palnTheme.id",coll.get(j).getPalnTheme().getId()));
//							usenv.setFilter(usfilter);
//							
//							EnterprisePlanEntryCollection col=EnterprisePlanEntryFactory.getLocalInstance(ctx).getEnterprisePlanEntryCollection(usenv);
//						
//							for(int k=0;k<col.size();k++){
//								col.get(k).setEntryState(ThemeEnum.Finish);	
//								EnterprisePlanEntryFactory.getLocalInstance(ctx).updatePartial(col.get(k), updateEntrySel);
//							}
//						}
//					}
//				}
//			}
//		}
//		if(delId.size()>0){
//			FilterInfo delfilter = new FilterInfo();
//			delfilter.getFilterItems().add(new FilterItemInfo("palnTheme.id",delId,CompareType.INCLUDE));
//			
//			EnterprisePlanEntryFactory.getLocalInstance(ctx).delete(delfilter);
//			
//			FilterInfo delSchemefilter = new FilterInfo();
//			delSchemefilter.getFilterItems().add(new FilterItemInfo("sellPlanID",delId,CompareType.INCLUDE));
//			
//			EnterpriseSchemeEntryFactory.getLocalInstance(ctx).delete(delSchemefilter);
//		}
//		
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",billInfo.getSellProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("useing",Boolean.TRUE));
		
		evInfo.setFilter(filter);
		EnterprisePlanCollection coll = getEnterprisePlanCollection(ctx,evInfo);
		SelectorItemCollection updateselector = new SelectorItemCollection();
		updateselector.add("useing");
		for(int i=0;i<coll.size();i++){
			EnterprisePlanInfo updateInfo  = coll.get(0);
			updateInfo.setUseing(false);
			_updatePartial(ctx, updateInfo, updateselector);
		}
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		EnterprisePlanInfo billInfo = (EnterprisePlanInfo) getValue(ctx,new ObjectUuidPK(billId));
		
		boolean isUpdate=false;
		if(billInfo.isUseing()){
			isUpdate=true;
		}
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		billInfo.setUseing(false);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("useing");
		
		_updatePartial(ctx, billInfo, selector);
		
		if(isUpdate){
			EntityViewInfo viewInfo = new EntityViewInfo();
		    FilterInfo filter = new FilterInfo();
		    filter.getFilterItems().add(new FilterItemInfo("sellProject.id", billInfo.getSellProject().getId()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("id", billId,CompareType.NOTEQUALS));
			viewInfo.setFilter(filter);
			
			EnterprisePlanCollection coll =getEnterprisePlanCollection(ctx, viewInfo);
			CRMHelper.sortCollection(coll, new String[]{"planYear","planMonth","version"}, false);
			
			if(coll.size()>0){
				SelectorItemCollection updateselector = new SelectorItemCollection();
				updateselector.add("useing");
				EnterprisePlanInfo updateInfo  = coll.get(0);
				updateInfo.setUseing(true);
				_updatePartial(ctx, updateInfo, updateselector);
			}
		}
	}
	
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
		EnterprisePlanInfo info=(EnterprisePlanInfo)model;
		for(int i=0;i<info.getEntry().size();i++){
			EnterprisePlanEntryInfo entry=info.getEntry().get(i);
			if(entry.getState().equals(ThemeEnum.Canceled)){
				for(int j=0;j<entry.getSellPlanEntry().size();j++){
					entry.getSellPlanEntry().get(j).setState(ThemeEnum.Canceled);
				}
			}
		}
		super._submit(ctx, pk, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		EnterprisePlanInfo info=(EnterprisePlanInfo)model;
		for(int i=0;i<info.getEntry().size();i++){
			EnterprisePlanEntryInfo entry=info.getEntry().get(i);
			if(entry.getState().equals(ThemeEnum.Canceled)){
				for(int j=0;j<entry.getSellPlanEntry().size();j++){
					entry.getSellPlanEntry().get(j).setState(ThemeEnum.Canceled);
				}
			}
		}
		return super._submit(ctx, model);
	}

	protected void checkDelete(Context ctx, IObjectPK pk) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("enterprisePlan.id", pk.toString()));
	    if(EnterpriseSchemeFactory.getLocalInstance(ctx).exists(filter)){
	    	throw new EASBizException(new NumericExceptionSubItem("100","已关联企划实施，禁止删除！"));
	    }
	}
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		checkDelete(ctx,pk);
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			checkDelete(ctx,arrayPK[i]);
		}
		super._delete(ctx, arrayPK);
	}
}