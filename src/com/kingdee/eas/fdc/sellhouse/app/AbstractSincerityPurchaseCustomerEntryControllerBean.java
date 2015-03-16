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
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryCollection;



public abstract class AbstractSincerityPurchaseCustomerEntryControllerBean extends TranCustomerEntryControllerBean implements SincerityPurchaseCustomerEntryController
{
    protected AbstractSincerityPurchaseCustomerEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("282C39B8");
    }

    public SincerityPurchaseCustomerEntryInfo getSincerityPurchaseCustomerEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("09ebb8fc-02fb-4612-a59f-9cf4ea4fbfb1"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            SincerityPurchaseCustomerEntryInfo retValue = (SincerityPurchaseCustomerEntryInfo)_getValue(ctx, pk);
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

    public SincerityPurchaseCustomerEntryInfo getSincerityPurchaseCustomerEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("15254392-6d69-43c0-a7a1-2596a6b67e6a"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            SincerityPurchaseCustomerEntryInfo retValue = (SincerityPurchaseCustomerEntryInfo)_getValue(ctx, pk, selector);
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

    public SincerityPurchaseCustomerEntryInfo getSincerityPurchaseCustomerEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("99ebbacb-c82b-4ef5-a96e-0af27f66b41c"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SincerityPurchaseCustomerEntryInfo retValue = (SincerityPurchaseCustomerEntryInfo)_getValue(ctx, oql);
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

    public SincerityPurchaseCustomerEntryCollection getSincerityPurchaseCustomerEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2c0d3062-20fb-4336-9ba8-fab6c92d2e85"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            SincerityPurchaseCustomerEntryCollection retValue = (SincerityPurchaseCustomerEntryCollection)_getCollection(ctx, svcCtx, view);
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

    public SincerityPurchaseCustomerEntryCollection getSincerityPurchaseCustomerEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("469e106d-c94f-41dc-8b89-e0020de39275"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            SincerityPurchaseCustomerEntryCollection retValue = (SincerityPurchaseCustomerEntryCollection)_getCollection(ctx, svcCtx);
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

    public SincerityPurchaseCustomerEntryCollection getSincerityPurchaseCustomerEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8c7ff081-3167-4ec8-a301-504dc3122b89"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SincerityPurchaseCustomerEntryCollection retValue = (SincerityPurchaseCustomerEntryCollection)_getCollection(ctx, svcCtx, oql);
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

    public TranCustomerEntryCollection getTranCustomerEntryCollection (Context ctx) throws BOSException
    {
    	return (TranCustomerEntryCollection)(getSincerityPurchaseCustomerEntryCollection(ctx).cast(TranCustomerEntryCollection.class));
    }
    public TranCustomerEntryCollection getTranCustomerEntryCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TranCustomerEntryCollection)(getSincerityPurchaseCustomerEntryCollection(ctx, view).cast(TranCustomerEntryCollection.class));
    }
    public TranCustomerEntryCollection getTranCustomerEntryCollection (Context ctx, String oql) throws BOSException
    {
    	return (TranCustomerEntryCollection)(getSincerityPurchaseCustomerEntryCollection(ctx, oql).cast(TranCustomerEntryCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSincerityPurchaseCustomerEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSincerityPurchaseCustomerEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSincerityPurchaseCustomerEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}