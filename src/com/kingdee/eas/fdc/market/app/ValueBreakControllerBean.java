package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.fdc.market.MeasurePlanTargetCollection;
import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
import com.kingdee.eas.fdc.market.MeasurePlanTargetInfo;
import com.kingdee.eas.fdc.market.ValueBreakInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.market.ValueBreakCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class ValueBreakControllerBean extends AbstractValueBreakControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.ValueBreakControllerBean");
    
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException {
    	
    }
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		ValueBreakInfo billInfo = (ValueBreakInfo) getValue(ctx,new ObjectUuidPK(billId));
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setIsNewVersoin(true);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(FDCCommonServerHelper.getServerTime());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("isNewVersoin");
		
		_updatePartial(ctx, billInfo, selector);
		
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id",billInfo.getSellProject().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isNewVersoin",Boolean.TRUE));
		
		evInfo.setFilter(filter);
		ValueBreakCollection coll = getValueBreakCollection(ctx,evInfo);
		SelectorItemCollection updateselector = new SelectorItemCollection();
		updateselector.add("isNewVersoin");
		for(int i=0;i<coll.size();i++){
			ValueBreakInfo updateInfo  = coll.get(0);
			updateInfo.setIsNewVersoin(false);
			_updatePartial(ctx, updateInfo, updateselector);
		}
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		ValueBreakInfo billInfo = (ValueBreakInfo) getValue(ctx,new ObjectUuidPK(billId));
		
		boolean isUpdate=false;
		if(billInfo.isIsNewVersoin()){
			isUpdate=true;
		}
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		billInfo.setIsNewVersoin(false);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("isNewVersoin");
		
		_updatePartial(ctx, billInfo, selector);
		
		if(isUpdate){
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id",billInfo.getSellProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			evInfo.setFilter(filter);
			ValueBreakCollection coll = getValueBreakCollection(ctx,evInfo);
			CRMHelper.sortCollection(coll, "version", false);
			
			if(coll.size()>0){
				SelectorItemCollection updateselector = new SelectorItemCollection();
				updateselector.add("isNewVersoin");
				ValueBreakInfo updateInfo  = coll.get(0);
				updateInfo.setIsNewVersoin(true);
				_updatePartial(ctx, updateInfo, updateselector);
			}
		}
	}
}