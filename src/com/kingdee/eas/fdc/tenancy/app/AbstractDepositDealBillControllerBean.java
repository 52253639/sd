package com.kingdee.eas.fdc.tenancy.app;

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
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.DepositDealBillInfo;
import com.kingdee.eas.fdc.tenancy.DepositDealBillCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.TenBillBaseCollection;



public abstract class AbstractDepositDealBillControllerBean extends TenBillBaseControllerBean implements DepositDealBillController
{
    protected AbstractDepositDealBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("756F2288");
    }

    public DepositDealBillInfo getDepositDealBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("603c879a-fd43-484c-965b-3af71beade46"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            DepositDealBillInfo retValue = (DepositDealBillInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (DepositDealBillInfo)svcCtx.getMethodReturnValue();
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

    public DepositDealBillInfo getDepositDealBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a82f5c57-6bd7-4544-bafe-cbbe070c9e54"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            DepositDealBillInfo retValue = (DepositDealBillInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (DepositDealBillInfo)svcCtx.getMethodReturnValue();
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

    public DepositDealBillInfo getDepositDealBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1cfcc7a4-ba89-41fc-9d45-cca460cffa89"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            DepositDealBillInfo retValue = (DepositDealBillInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (DepositDealBillInfo)svcCtx.getMethodReturnValue();
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

    public DepositDealBillCollection getDepositDealBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8e458c98-75a2-4ea0-a162-fa262bf5a72e"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            DepositDealBillCollection retValue = (DepositDealBillCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (DepositDealBillCollection)svcCtx.getMethodReturnValue();
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

    public DepositDealBillCollection getDepositDealBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("53ff7d64-f5cf-40ba-8e25-420edf0298e6"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            DepositDealBillCollection retValue = (DepositDealBillCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (DepositDealBillCollection)svcCtx.getMethodReturnValue();
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

    public DepositDealBillCollection getDepositDealBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("60d7c89f-53ff-42ed-a1ce-b0cdbaf54de0"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            DepositDealBillCollection retValue = (DepositDealBillCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (DepositDealBillCollection)svcCtx.getMethodReturnValue();
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

    public TenBillBaseCollection getTenBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TenBillBaseCollection)(getDepositDealBillCollection(ctx, view).cast(TenBillBaseCollection.class));
    }
    public TenBillBaseCollection getTenBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (TenBillBaseCollection)(getDepositDealBillCollection(ctx, oql).cast(TenBillBaseCollection.class));
    }
    public TenBillBaseCollection getTenBillBaseCollection (Context ctx) throws BOSException
    {
    	return (TenBillBaseCollection)(getDepositDealBillCollection(ctx).cast(TenBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getDepositDealBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getDepositDealBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getDepositDealBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getDepositDealBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getDepositDealBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getDepositDealBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getDepositDealBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getDepositDealBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getDepositDealBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}