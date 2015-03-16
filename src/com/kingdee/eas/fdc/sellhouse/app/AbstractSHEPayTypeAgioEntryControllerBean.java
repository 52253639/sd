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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeAgioEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;



public abstract class AbstractSHEPayTypeAgioEntryControllerBean extends AgioEntryControllerBean implements SHEPayTypeAgioEntryController
{
    protected AbstractSHEPayTypeAgioEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("14552C39");
    }

    public SHEPayTypeAgioEntryInfo getSHEPayTypeAgioEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5f272c36-ead6-48bf-bc27-9c8c38268834"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            SHEPayTypeAgioEntryInfo retValue = (SHEPayTypeAgioEntryInfo)_getValue(ctx, pk);
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

    public SHEPayTypeAgioEntryInfo getSHEPayTypeAgioEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("07280214-f73a-4b57-ae50-238d4f30c4ac"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            SHEPayTypeAgioEntryInfo retValue = (SHEPayTypeAgioEntryInfo)_getValue(ctx, pk, selector);
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

    public SHEPayTypeAgioEntryInfo getSHEPayTypeAgioEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("311c75d0-54b2-4a64-acb6-271798bd99f5"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SHEPayTypeAgioEntryInfo retValue = (SHEPayTypeAgioEntryInfo)_getValue(ctx, oql);
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

    public SHEPayTypeAgioEntryCollection getSHEPayTypeAgioEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("79756a82-c1be-4cee-ab4b-546926778e61"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            SHEPayTypeAgioEntryCollection retValue = (SHEPayTypeAgioEntryCollection)_getCollection(ctx, svcCtx);
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

    public SHEPayTypeAgioEntryCollection getSHEPayTypeAgioEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("835c5930-f83d-47c0-bc81-c125232d2f8a"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            SHEPayTypeAgioEntryCollection retValue = (SHEPayTypeAgioEntryCollection)_getCollection(ctx, svcCtx, view);
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

    public SHEPayTypeAgioEntryCollection getSHEPayTypeAgioEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2c7ac87a-9fd5-4628-bbdd-7c8463e84b78"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SHEPayTypeAgioEntryCollection retValue = (SHEPayTypeAgioEntryCollection)_getCollection(ctx, svcCtx, oql);
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

    public AgioEntryCollection getAgioEntryCollection (Context ctx) throws BOSException
    {
    	return (AgioEntryCollection)(getSHEPayTypeAgioEntryCollection(ctx).cast(AgioEntryCollection.class));
    }
    public AgioEntryCollection getAgioEntryCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (AgioEntryCollection)(getSHEPayTypeAgioEntryCollection(ctx, view).cast(AgioEntryCollection.class));
    }
    public AgioEntryCollection getAgioEntryCollection (Context ctx, String oql) throws BOSException
    {
    	return (AgioEntryCollection)(getSHEPayTypeAgioEntryCollection(ctx, oql).cast(AgioEntryCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSHEPayTypeAgioEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSHEPayTypeAgioEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSHEPayTypeAgioEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}