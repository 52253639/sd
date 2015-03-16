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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection;
import com.kingdee.eas.framework.CoreBaseCollection;



public abstract class AbstractPrePurchaseAgioEntryControllerBean extends AgioEntryControllerBean implements PrePurchaseAgioEntryController
{
    protected AbstractPrePurchaseAgioEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("E0CFCEFD");
    }

    public PrePurchaseAgioEntryCollection getPrePurchaseAgioEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("57d2ecc2-2fbe-43e0-87ca-92f23720977e"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            PrePurchaseAgioEntryCollection retValue = (PrePurchaseAgioEntryCollection)_getCollection(ctx, svcCtx);
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

    public PrePurchaseAgioEntryCollection getPrePurchaseAgioEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("57de8018-a61e-4b1f-8aa4-e33a14772f82"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            PrePurchaseAgioEntryCollection retValue = (PrePurchaseAgioEntryCollection)_getCollection(ctx, svcCtx, view);
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

    public PrePurchaseAgioEntryCollection getPrePurchaseAgioEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("008891c6-9089-48f3-a419-001a85cfb050"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            PrePurchaseAgioEntryCollection retValue = (PrePurchaseAgioEntryCollection)_getCollection(ctx, svcCtx, oql);
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
    	return (AgioEntryCollection)(getPrePurchaseAgioEntryCollection(ctx).cast(AgioEntryCollection.class));
    }
    public AgioEntryCollection getAgioEntryCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (AgioEntryCollection)(getPrePurchaseAgioEntryCollection(ctx, view).cast(AgioEntryCollection.class));
    }
    public AgioEntryCollection getAgioEntryCollection (Context ctx, String oql) throws BOSException
    {
    	return (AgioEntryCollection)(getPrePurchaseAgioEntryCollection(ctx, oql).cast(AgioEntryCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getPrePurchaseAgioEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getPrePurchaseAgioEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getPrePurchaseAgioEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}