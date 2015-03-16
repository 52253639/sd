package com.kingdee.eas.fdc.invite.supplier.app;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillCollection;



public abstract class AbstractFDCSplKeepContractAuditBillControllerBean extends FDCSplAuditBaseBillControllerBean implements FDCSplKeepContractAuditBillController
{
    protected AbstractFDCSplKeepContractAuditBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("FC287569");
    }

    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ef49ae93-64b0-4e96-9653-e6cd1b8ed934"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCSplKeepContractAuditBillInfo retValue = (FDCSplKeepContractAuditBillInfo)_getValue(ctx, oql);
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

    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b3474d0e-1d01-4159-8f61-9c0a5d045b13"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            FDCSplKeepContractAuditBillInfo retValue = (FDCSplKeepContractAuditBillInfo)_getValue(ctx, pk, selector);
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

    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d8ac842c-4575-41f6-8df6-c7f4f5f3518a"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            FDCSplKeepContractAuditBillInfo retValue = (FDCSplKeepContractAuditBillInfo)_getValue(ctx, pk);
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

    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("462685da-4e93-42b6-bc2d-b5047befb7dc"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCSplKeepContractAuditBillCollection retValue = (FDCSplKeepContractAuditBillCollection)_getCollection(ctx, svcCtx, oql);
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

    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8594f4fc-473a-4c7f-8f8b-fb17c7c68328"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            FDCSplKeepContractAuditBillCollection retValue = (FDCSplKeepContractAuditBillCollection)_getCollection(ctx, svcCtx, view);
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

    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a9310dcd-5496-4662-b9b9-77ace629ce81"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            FDCSplKeepContractAuditBillCollection retValue = (FDCSplKeepContractAuditBillCollection)_getCollection(ctx, svcCtx);
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

    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection (Context ctx) throws BOSException
    {
    	return (FDCSplAuditBaseBillCollection)(getFDCSplKeepContractAuditBillCollection(ctx).cast(FDCSplAuditBaseBillCollection.class));
    }
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCSplAuditBaseBillCollection)(getFDCSplKeepContractAuditBillCollection(ctx, view).cast(FDCSplAuditBaseBillCollection.class));
    }
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCSplAuditBaseBillCollection)(getFDCSplKeepContractAuditBillCollection(ctx, oql).cast(FDCSplAuditBaseBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getFDCSplKeepContractAuditBillCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getFDCSplKeepContractAuditBillCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getFDCSplKeepContractAuditBillCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCSplKeepContractAuditBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}