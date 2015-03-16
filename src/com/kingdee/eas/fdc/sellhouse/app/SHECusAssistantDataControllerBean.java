package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class SHECusAssistantDataControllerBean extends AbstractSHECusAssistantDataControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SHECusAssistantDataControllerBean");
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	SHECusAssistantDataInfo info = (SHECusAssistantDataInfo) model;
    	info.setIsEnabled(false);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	SHECusAssistantDataInfo info = (SHECusAssistantDataInfo) model;
    	info.setIsEnabled(true);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    protected void _checkNameDup(Context ctx , IObjectValue model) throws BOSException , EASBizException{
    	SHECusAssistantDataInfo assInfo = (SHECusAssistantDataInfo)model;
    	FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();// 当前组织
    	FilterInfo filter = new FilterInfo();
    	if(null != assInfo.getGroup()){
    		filter.getFilterItems().add(new FilterItemInfo("group",assInfo.getGroup().getId()));
    		filter.getFilterItems().add(new FilterItemInfo("name",assInfo.getName()));
    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", org.getId().toString()));
    	}
    	if(this._exists(ctx, filter)){
    		 throw new EASBizException(EASBizException.CHECKDUPLICATED ,
                     new Object[]
                     {assInfo.getName()});
    	}
    
    }
    protected void _checkNumberDup(Context ctx , IObjectValue model) throws BOSException , EASBizException{
    	SHECusAssistantDataInfo assInfo = (SHECusAssistantDataInfo)model;
	FilterInfo filter = new FilterInfo();
	FullOrgUnitInfo org = ContextUtil.getCurrentOrgUnit(ctx).castToFullOrgUnitInfo();// 当前组织
	if(null != assInfo.getGroup()){
		filter.getFilterItems().add(new FilterItemInfo("group",assInfo.getGroup().getId()));
		filter.getFilterItems().add(new FilterItemInfo("number",assInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", org.getId().toString()));
	}
	if(this._exists(ctx, filter)){
		   throw new EASBizException(EASBizException.CHECKDUPLICATED ,
                   new Object[]
                   {assInfo.getNumber()});
	}
    
    }
}