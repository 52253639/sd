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
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAssistantTypeCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class RoomAssistantTypeControllerBean extends AbstractRoomAssistantTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomAssistantTypeControllerBean");
    public void checkNumberDup(Context ctx, DataBaseInfo dataBaseInfo) throws BOSException, EASBizException
    {
    
    }
    protected void _checkNameDup(Context ctx , IObjectValue model) throws
    BOSException , EASBizException
    {	RoomAssistantInfo roomAssInfo = (RoomAssistantInfo)model;
    if(null==roomAssInfo.getId()){
    	FilterInfo filter = new FilterInfo();
    	if(null!=roomAssInfo.getType()){
    		filter.getFilterItems().add(new FilterItemInfo("name",roomAssInfo.getName()));
    	}
    	if(this._exists(ctx, filter)){
    		 throw new EASBizException(EASBizException.CHECKDUPLICATED ,
                     new Object[]
                     {roomAssInfo.getName()});
    	}
    }
    }
}