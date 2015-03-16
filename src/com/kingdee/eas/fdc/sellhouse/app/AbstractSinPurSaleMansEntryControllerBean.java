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
import com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryCollection;



public abstract class AbstractSinPurSaleMansEntryControllerBean extends TranSaleManEntryControllerBean implements SinPurSaleMansEntryController
{
    protected AbstractSinPurSaleMansEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("C6EF0942");
    }

    public SinPurSaleMansEntryInfo getSinPurSaleMansEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("60222176-c256-4968-9864-74b28b931261"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            SinPurSaleMansEntryInfo retValue = (SinPurSaleMansEntryInfo)_getValue(ctx, pk);
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

    public SinPurSaleMansEntryInfo getSinPurSaleMansEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("31f6d6b2-de7b-4b9a-a364-fdfa854e9825"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            SinPurSaleMansEntryInfo retValue = (SinPurSaleMansEntryInfo)_getValue(ctx, pk, selector);
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

    public SinPurSaleMansEntryInfo getSinPurSaleMansEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fb36e829-4e64-493f-b9a7-139dbe83b3a3"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SinPurSaleMansEntryInfo retValue = (SinPurSaleMansEntryInfo)_getValue(ctx, oql);
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

    public SinPurSaleMansEntryCollection getSinPurSaleMansEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3475cbb0-0eee-4ced-8316-08d166bc236d"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            SinPurSaleMansEntryCollection retValue = (SinPurSaleMansEntryCollection)_getCollection(ctx, svcCtx);
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

    public SinPurSaleMansEntryCollection getSinPurSaleMansEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("832b4bec-eb7d-49cc-9d00-38291431b7b6"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SinPurSaleMansEntryCollection retValue = (SinPurSaleMansEntryCollection)_getCollection(ctx, svcCtx, oql);
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

    public SinPurSaleMansEntryCollection getSinPurSaleMansEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4e58298d-a814-4099-8d47-1e794cb78367"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            SinPurSaleMansEntryCollection retValue = (SinPurSaleMansEntryCollection)_getCollection(ctx, svcCtx, view);
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

    public TranSaleManEntryCollection getTranSaleManEntryCollection (Context ctx) throws BOSException
    {
    	return (TranSaleManEntryCollection)(getSinPurSaleMansEntryCollection(ctx).cast(TranSaleManEntryCollection.class));
    }
    public TranSaleManEntryCollection getTranSaleManEntryCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TranSaleManEntryCollection)(getSinPurSaleMansEntryCollection(ctx, view).cast(TranSaleManEntryCollection.class));
    }
    public TranSaleManEntryCollection getTranSaleManEntryCollection (Context ctx, String oql) throws BOSException
    {
    	return (TranSaleManEntryCollection)(getSinPurSaleMansEntryCollection(ctx, oql).cast(TranSaleManEntryCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSinPurSaleMansEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSinPurSaleMansEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSinPurSaleMansEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}