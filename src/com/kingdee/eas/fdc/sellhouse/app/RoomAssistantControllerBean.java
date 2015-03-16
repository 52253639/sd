package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.DataBaseException;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantCollection;

public class RoomAssistantControllerBean extends AbstractRoomAssistantControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomAssistantControllerBean");
    
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	//关联引用删除的检查
    	this.isReferenced(ctx, pk);
    	DataBaseCodeRuleHelper.recycleNumber(ctx, (DataBaseInfo) _getValue(ctx, pk));
    	super._delete(ctx, pk);
    }
    
    protected void _checkNameDup(Context ctx , IObjectValue model) throws
    BOSException , EASBizException
    {	RoomAssistantInfo roomAssInfo = (RoomAssistantInfo)model;
    if(null==roomAssInfo.getId()){
    	FilterInfo filter = new FilterInfo();
    	if(null!=roomAssInfo.getType()){
    		filter.getFilterItems().add(new FilterItemInfo("type",roomAssInfo.getType().getId()));
    		filter.getFilterItems().add(new FilterItemInfo("name",roomAssInfo.getName()));
    		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", roomAssInfo.getOrgUnit().getId()));
    	}
    	if(this._exists(ctx, filter)){
    		 throw new EASBizException(EASBizException.CHECKDUPLICATED ,
                     new Object[]
                     {roomAssInfo.getName()});
    	}
    }
    	
    
    }
    protected void _checkNumberDup(Context ctx , IObjectValue model) throws
    BOSException , EASBizException
    {RoomAssistantInfo roomAssInfo = (RoomAssistantInfo)model;
    if(null==roomAssInfo.getId()){
	FilterInfo filter = new FilterInfo();
	if(null!=roomAssInfo.getType()){
		filter.getFilterItems().add(new FilterItemInfo("type",roomAssInfo.getType().getId()));
		filter.getFilterItems().add(new FilterItemInfo("number",roomAssInfo.getNumber()));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", roomAssInfo.getOrgUnit().getId()));
	}
	if(this._exists(ctx, filter)){
		   throw new EASBizException(EASBizException.CHECKDUPLICATED ,
                   new Object[]
                   {roomAssInfo.getNumber()});
	}
    }
    }
    
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	RoomAssistantInfo roomAssistant = (RoomAssistantInfo)model;
    	if(roomAssistant.isIsDefault()){
    		if(null!=roomAssistant.getType()){
    			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    			builder.appendSql(" update t_she_roomassistant set FIsDefault = '0' where FTypeID ='"+roomAssistant.getType().getId().toString()+"'");
    			builder.executeUpdate(ctx);
    		}
    		
    	}
    	return super._submit(ctx, model);
	}
}