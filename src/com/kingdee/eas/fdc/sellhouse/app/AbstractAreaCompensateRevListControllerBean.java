package com.kingdee.eas.fdc.sellhouse.app;

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
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.RevListCollection;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.AreaCompensateRevListInfo;
import com.kingdee.eas.fdc.basecrm.app.RevListControllerBean;



public abstract class AbstractAreaCompensateRevListControllerBean extends RevListControllerBean implements AreaCompensateRevListController
{
    protected AbstractAreaCompensateRevListControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("179EB1A2");
    }

    public AreaCompensateRevListInfo getAreaCompensateRevListInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f4c5ce30-96b1-45ad-8882-bb46ff10004a"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            AreaCompensateRevListInfo retValue = (AreaCompensateRevListInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk);
    }

    public AreaCompensateRevListInfo getAreaCompensateRevListInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e8b84359-f421-419b-80f4-8d51fc2b9ca8"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            AreaCompensateRevListInfo retValue = (AreaCompensateRevListInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        return super._getValue(ctx, pk, selector);
    }

    public AreaCompensateRevListInfo getAreaCompensateRevListInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e3be4847-4d5e-490a-91c7-8b4b1215e6c1"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            AreaCompensateRevListInfo retValue = (AreaCompensateRevListInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getValue(ctx, oql);
    }

    public AreaCompensateRevListCollection getAreaCompensateRevListCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f79ab39e-e490-49fc-917f-864324d862ae"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            AreaCompensateRevListCollection retValue = (AreaCompensateRevListCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx) throws BOSException
    {
        return super._getCollection(ctx, svcCtx);
    }

    public AreaCompensateRevListCollection getAreaCompensateRevListCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5f9923f3-6798-4045-a7ce-7003ac1ae550"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            AreaCompensateRevListCollection retValue = (AreaCompensateRevListCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, EntityViewInfo view) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, view);
    }

    public AreaCompensateRevListCollection getAreaCompensateRevListCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("973c059f-4853-4a74-bfac-df631b4cec58"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            AreaCompensateRevListCollection retValue = (AreaCompensateRevListCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectCollection _getCollection(Context ctx, IServiceContext svcCtx, String oql) throws BOSException
    {
        return super._getCollection(ctx, svcCtx, oql);
    }

    public RevListCollection getRevListCollection (Context ctx) throws BOSException
    {
    	return (RevListCollection)(getAreaCompensateRevListCollection(ctx).cast(RevListCollection.class));
    }
    public RevListCollection getRevListCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (RevListCollection)(getAreaCompensateRevListCollection(ctx, view).cast(RevListCollection.class));
    }
    public RevListCollection getRevListCollection (Context ctx, String oql) throws BOSException
    {
    	return (RevListCollection)(getAreaCompensateRevListCollection(ctx, oql).cast(RevListCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getAreaCompensateRevListCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getAreaCompensateRevListCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getAreaCompensateRevListCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}