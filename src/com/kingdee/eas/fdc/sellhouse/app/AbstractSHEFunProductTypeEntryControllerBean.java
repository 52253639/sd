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
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryInfo;



public abstract class AbstractSHEFunProductTypeEntryControllerBean extends AbstractEntityControllerBean implements SHEFunProductTypeEntryController
{
    protected AbstractSHEFunProductTypeEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("B354D293");
    }

    public SHEFunProductTypeEntryInfo getValue(Context ctx, IObjectPK pk) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bf4b1c6c-ebe5-4c34-80b6-75e30f758c1e"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            SHEFunProductTypeEntryInfo retValue = (SHEFunProductTypeEntryInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException
    {
        return innerGetValue(ctx, pk);
    }

    public SHEFunProductTypeEntryInfo getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("01198f26-fdc1-46d6-ab44-ceda53d296c0"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            SHEFunProductTypeEntryInfo retValue = (SHEFunProductTypeEntryInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException
    {
        return innerGetValue(ctx, pk, selector);
    }

    public SHEFunProductTypeEntryInfo getValue(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5d23734c-78f1-4562-a7c6-05966deff792"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SHEFunProductTypeEntryInfo retValue = (SHEFunProductTypeEntryInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected IObjectValue _getValue(Context ctx, String oql) throws BOSException
    {
        return innerGetValue(ctx, oql);
    }

    public SHEFunProductTypeEntryCollection getCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0583f50e-d7e8-4bd6-98d3-2b5178479a7d"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            SHEFunProductTypeEntryCollection retValue = (SHEFunProductTypeEntryCollection)_getCollection(ctx, svcCtx);
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
        return innerGetCollection(ctx, svcCtx);
    }

    public SHEFunProductTypeEntryCollection getCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4c34280c-72d2-4c30-885e-9e74e8302a53"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            SHEFunProductTypeEntryCollection retValue = (SHEFunProductTypeEntryCollection)_getCollection(ctx, svcCtx, view);
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
        return innerGetCollection(ctx, svcCtx, view);
    }

    public SHEFunProductTypeEntryCollection getCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3a975768-2aae-4b48-9223-6b2e0ea719a3"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SHEFunProductTypeEntryCollection retValue = (SHEFunProductTypeEntryCollection)_getCollection(ctx, svcCtx, oql);
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
        return innerGetCollection(ctx, svcCtx, oql);
    }

}