package com.kingdee.eas.fdc.basecrm.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupFactory;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo;
import com.kingdee.eas.fdc.basecrm.UseTypeEnum;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.DbUtil;

public class FDCCusBaseDataControllerBean extends AbstractFDCCusBaseDataControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataControllerBean");
    
    protected void _cancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	FDCCusBaseDataInfo info = (FDCCusBaseDataInfo) model;
    	info.setIsEnabled(false);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    protected void _cancelCancel(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException {
    	FDCCusBaseDataInfo info = (FDCCusBaseDataInfo) model;
    	info.setIsEnabled(true);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
    	sels.add("isEnabled");
    	_updatePartial(ctx, model, sels);
    }
    
    protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FDCCusBaseDataInfo info = (FDCCusBaseDataInfo) model;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", info.getGroup().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("name", info.getName()));
		if (_exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, info, IFWEntityStruct.dataBase_Name) + info.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
		}
	}
    
    protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	FDCCusBaseDataInfo info = (FDCCusBaseDataInfo) model;
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("group.id", info.getGroup().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("number", info.getNumber()));
    	if(_exists(ctx, filter)){
    		String number = this._getPropertyAlias(ctx, info, IFWEntityStruct.dataBase_Number) + info.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
    	}
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	FDCCusBaseDataInfo info = (FDCCusBaseDataInfo) model;
    	
    	if(info.isIsDefault()){
    		FDCCusBaseDataGroupInfo group = FDCCusBaseDataGroupFactory.getLocalInstance(ctx).getFDCCusBaseDataGroupInfo(new ObjectUuidPK(info.getGroup().getId()));
        	if(UseTypeEnum.DanXuan.equals(group.getUseType())){
        		String s = "update t_bdc_fdccusbasedata set FIsDefault=0 where fgroupid=? and fid!=?";
        		Object[] params = new Object[]{group.getId().toString(), info.getId()==null ? " " : info.getId().toString()};
        		DbUtil.execute(ctx, s, params);
        	}
    	}
    	
    	return super._submit(ctx, model);
    }
    
}