package com.kingdee.eas.fdc.market.app;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.market.StoreSubjectCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.fdc.market.StoreSubjectInfo;

public class StoreSubjectControllerBean extends AbstractStoreSubjectControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.StoreSubjectControllerBean");

	protected Result _submit(Context ctx, IObjectCollection colls) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		return super._submit(ctx, colls);
	}

	public IObjectPK submit(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		return super.submit(ctx, model);
	}

	public void submit(Context ctx, IObjectPK pk, CoreBaseInfo model) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		super.submit(ctx, pk, model);
	}
    
    
    
    
    
    
    
    
    
}