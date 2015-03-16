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
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryCollection;



public abstract class AbstractPrePurchaseCustomerEntryControllerBean extends TranCustomerEntryControllerBean implements PrePurchaseCustomerEntryController
{
    protected AbstractPrePurchaseCustomerEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("F473DEAB");
    }

    public PrePurchaseCustomerEntryInfo getPrePurchaseCustomerEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("eb7e6164-9d7d-4946-82e2-bf26d20e7f4f"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            PrePurchaseCustomerEntryInfo retValue = (PrePurchaseCustomerEntryInfo)_getValue(ctx, pk);
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

    public PrePurchaseCustomerEntryInfo getPrePurchaseCustomerEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("913fa168-a69c-4c57-92bd-d762121207f2"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            PrePurchaseCustomerEntryInfo retValue = (PrePurchaseCustomerEntryInfo)_getValue(ctx, pk, selector);
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

    public PrePurchaseCustomerEntryInfo getPrePurchaseCustomerEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8c8d1bd1-04bb-440f-81f6-67764da53337"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            PrePurchaseCustomerEntryInfo retValue = (PrePurchaseCustomerEntryInfo)_getValue(ctx, oql);
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

    public PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3ac51173-0715-46e8-8aa3-94712d201c91"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            PrePurchaseCustomerEntryCollection retValue = (PrePurchaseCustomerEntryCollection)_getCollection(ctx, svcCtx);
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

    public PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f78c1039-fc87-4c87-afe1-10450498666c"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            PrePurchaseCustomerEntryCollection retValue = (PrePurchaseCustomerEntryCollection)_getCollection(ctx, svcCtx, view);
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

    public PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("64461e86-2906-4a5e-aad3-e224d1477730"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            PrePurchaseCustomerEntryCollection retValue = (PrePurchaseCustomerEntryCollection)_getCollection(ctx, svcCtx, oql);
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
    	return (TranCustomerEntryCollection)(getPrePurchaseCustomerEntryCollection(ctx).cast(TranCustomerEntryCollection.class));
    }
    public TranCustomerEntryCollection getTranCustomerEntryCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TranCustomerEntryCollection)(getPrePurchaseCustomerEntryCollection(ctx, view).cast(TranCustomerEntryCollection.class));
    }
    public TranCustomerEntryCollection getTranCustomerEntryCollection (Context ctx, String oql) throws BOSException
    {
    	return (TranCustomerEntryCollection)(getPrePurchaseCustomerEntryCollection(ctx, oql).cast(TranCustomerEntryCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getPrePurchaseCustomerEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getPrePurchaseCustomerEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getPrePurchaseCustomerEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}