package com.kingdee.eas.fdc.market.app;

import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
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
import com.kingdee.eas.fdc.market.EnterpriseSchemeFactory;
import com.kingdee.eas.fdc.market.MeasurePlanTargetCollection;
import com.kingdee.eas.fdc.market.MeasurePlanTargetFactory;
import com.kingdee.eas.fdc.market.MeasurePlanTargetInfo;
import com.kingdee.eas.fdc.market.ValueBreakFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class MeasurePlanTargetControllerBean extends AbstractMeasurePlanTargetControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.MeasurePlanTargetControllerBean");

    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException {
    }
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		MeasurePlanTargetInfo billInfo = (MeasurePlanTargetInfo) getValue(ctx,new ObjectUuidPK(billId));
		
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.AUDITTED);
		billInfo.setIsNew(true);
		billInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		billInfo.setAuditTime(FDCCommonServerHelper.getServerTime());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("isNew");
		
		_updatePartial(ctx, billInfo, selector);
		
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("projectAgin",billInfo.getProjectAgin().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isNew",Boolean.TRUE));
		
		evInfo.setFilter(filter);
		MeasurePlanTargetCollection coll = getMeasurePlanTargetCollection(ctx,evInfo);
		SelectorItemCollection updateselector = new SelectorItemCollection();
		updateselector.add("isNew");
		for(int i=0;i<coll.size();i++){
			MeasurePlanTargetInfo updateInfo  = coll.get(i);
			updateInfo.setIsNew(false);
			_updatePartial(ctx, updateInfo, updateselector);
		}
	}
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,EASBizException {
		MeasurePlanTargetInfo billInfo = (MeasurePlanTargetInfo) getValue(ctx,new ObjectUuidPK(billId));
		
		boolean isUpdate=false;
		if(billInfo.isIsNew()){
			isUpdate=true;
		}
		billInfo.setId(billId);
		billInfo.setState(FDCBillStateEnum.SUBMITTED);
		billInfo.setAuditor(null);
		billInfo.setAuditTime(null);
		billInfo.setIsNew(false);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("isNew");
		
		_updatePartial(ctx, billInfo, selector);
		
		if(isUpdate){
			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",billId,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("projectAgin",billInfo.getProjectAgin().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			evInfo.setFilter(filter);
			MeasurePlanTargetCollection coll = getMeasurePlanTargetCollection(ctx,evInfo);
			CRMHelper.sortCollection(coll, "versions", false);
			
			if(coll.size()>0){
				SelectorItemCollection updateselector = new SelectorItemCollection();
				updateselector.add("isNew");
				MeasurePlanTargetInfo updateInfo  = coll.get(0);
				updateInfo.setIsNew(true);
				_updatePartial(ctx, updateInfo, updateselector);
			}
		}
	}
	protected void checkDelete(Context ctx, IObjectPK pk) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
	    filter.getFilterItems().add(new FilterItemInfo("totalAmount.id", pk.toString()));
	    if(ValueBreakFactory.getLocalInstance(ctx).exists(filter)){
	    	throw new EASBizException(new NumericExceptionSubItem("100","已关联货值年度分解，禁止删除！"));
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