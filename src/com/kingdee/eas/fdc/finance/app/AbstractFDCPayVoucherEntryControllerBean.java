package com.kingdee.eas.fdc.finance.app;

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

import com.kingdee.eas.framework.BillEntryBaseCollection;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.FDCBaseVoucherEntryCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.finance.FDCPayVoucherEntryCollection;
import com.kingdee.eas.fdc.finance.FDCPayVoucherEntryInfo;



public abstract class AbstractFDCPayVoucherEntryControllerBean extends FDCBaseVoucherEntryControllerBean implements FDCPayVoucherEntryController
{
    protected AbstractFDCPayVoucherEntryControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("AF19A856");
    }

    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c32dba9b-011d-1000-e000-00acc0a810b3"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            FDCPayVoucherEntryInfo retValue = (FDCPayVoucherEntryInfo)_getValue(ctx, pk);
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

    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c32dba9b-011d-1000-e000-00adc0a810b3"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            FDCPayVoucherEntryInfo retValue = (FDCPayVoucherEntryInfo)_getValue(ctx, pk, selector);
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

    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c32dba9b-011d-1000-e000-00aec0a810b3"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCPayVoucherEntryInfo retValue = (FDCPayVoucherEntryInfo)_getValue(ctx, oql);
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

    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c32dba9b-011d-1000-e000-00b8c0a810b3"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            FDCPayVoucherEntryCollection retValue = (FDCPayVoucherEntryCollection)_getCollection(ctx, svcCtx);
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

    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c32dba9b-011d-1000-e000-00b9c0a810b3"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            FDCPayVoucherEntryCollection retValue = (FDCPayVoucherEntryCollection)_getCollection(ctx, svcCtx, view);
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

    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c32dba9b-011d-1000-e000-00bac0a810b3"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCPayVoucherEntryCollection retValue = (FDCPayVoucherEntryCollection)_getCollection(ctx, svcCtx, oql);
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

    public FDCBaseVoucherEntryCollection getFDCBaseVoucherEntryCollection (Context ctx) throws BOSException
    {
    	return (FDCBaseVoucherEntryCollection)(getFDCPayVoucherEntryCollection(ctx).cast(FDCBaseVoucherEntryCollection.class));
    }
    public FDCBaseVoucherEntryCollection getFDCBaseVoucherEntryCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBaseVoucherEntryCollection)(getFDCPayVoucherEntryCollection(ctx, view).cast(FDCBaseVoucherEntryCollection.class));
    }
    public FDCBaseVoucherEntryCollection getFDCBaseVoucherEntryCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBaseVoucherEntryCollection)(getFDCPayVoucherEntryCollection(ctx, oql).cast(FDCBaseVoucherEntryCollection.class));
    }
    public BillEntryBaseCollection getBillEntryBaseCollection (Context ctx) throws BOSException
    {
    	return (BillEntryBaseCollection)(getFDCPayVoucherEntryCollection(ctx).cast(BillEntryBaseCollection.class));
    }
    public BillEntryBaseCollection getBillEntryBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (BillEntryBaseCollection)(getFDCPayVoucherEntryCollection(ctx, view).cast(BillEntryBaseCollection.class));
    }
    public BillEntryBaseCollection getBillEntryBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (BillEntryBaseCollection)(getFDCPayVoucherEntryCollection(ctx, oql).cast(BillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getFDCPayVoucherEntryCollection(ctx).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getFDCPayVoucherEntryCollection(ctx, view).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBillEntryBaseCollection getCoreBillEntryBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillEntryBaseCollection)(getFDCPayVoucherEntryCollection(ctx, oql).cast(CoreBillEntryBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCPayVoucherEntryCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCPayVoucherEntryCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCPayVoucherEntryCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}