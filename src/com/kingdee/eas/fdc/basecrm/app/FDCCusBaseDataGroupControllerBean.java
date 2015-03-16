package com.kingdee.eas.fdc.basecrm.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupCollection;
import com.kingdee.eas.framework.DataBaseCollection;

public class FDCCusBaseDataGroupControllerBean extends AbstractFDCCusBaseDataGroupControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basecrm.app.FDCCusBaseDataGroupControllerBean");
    
    
    
    protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	FDCCusBaseDataGroupInfo group = (FDCCusBaseDataGroupInfo) model;
    	String name = group.getName();
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("parent.id", group.getParent().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("name", name));
    	if(group.getId() != null){
    		filter.getFilterItems().add(new FilterItemInfo("id", group.getId().toString(), CompareType.NOTEQUALS));
    	}
		if (_exists(ctx, filter)) {
			String t = this._getPropertyAlias(ctx, group, IFWEntityStruct.dataBase_Name) + group.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { t });
		}
    }
    
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	_checkNameDup(ctx, model);
    	return super._submit(ctx, model);
    }
    
    protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	super._checkNumberDup(ctx, model);
    }
    
}