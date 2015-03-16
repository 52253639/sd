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
import com.kingdee.eas.fdc.propertymgmt.PPMBillCollection;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.propertymgmt.PPMProjectBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillCollection;
import com.kingdee.eas.fdc.propertymgmt.app.PPMProjectBillControllerBean;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;



public abstract class AbstractInsteadCollectOutBillControllerBean extends PPMProjectBillControllerBean implements InsteadCollectOutBillController
{
    protected AbstractInsteadCollectOutBillControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("F2A687D2");
    }

    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3f4c1595-e4b4-401a-8f72-e3c12f879d2e"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            InsteadCollectOutBillCollection retValue = (InsteadCollectOutBillCollection)_getCollection(ctx, svcCtx);
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

    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9d1124dd-9738-458f-800a-eca6562306f3"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            InsteadCollectOutBillCollection retValue = (InsteadCollectOutBillCollection)_getCollection(ctx, svcCtx, view);
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

    public InsteadCollectOutBillCollection getInsteadCollectOutBillCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("708b51d5-cf0b-46a5-8ddc-e7fc32ede192"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            InsteadCollectOutBillCollection retValue = (InsteadCollectOutBillCollection)_getCollection(ctx, svcCtx, oql);
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

    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("020c0ded-cb33-46d4-b1a6-180553f34dad"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            InsteadCollectOutBillInfo retValue = (InsteadCollectOutBillInfo)_getValue(ctx, pk);
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

    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cecbcf3e-075d-4cd0-bd4a-04c9a783a831"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            InsteadCollectOutBillInfo retValue = (InsteadCollectOutBillInfo)_getValue(ctx, pk, selector);
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

    public InsteadCollectOutBillInfo getInsteadCollectOutBillInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("94e0a1e6-0628-4461-95e0-69f45edf3c55"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            InsteadCollectOutBillInfo retValue = (InsteadCollectOutBillInfo)_getValue(ctx, oql);
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

    public void generateNewData(Context ctx, PersonInfo personInfo, Date bizDate, Set rows) throws BOSException, SellHouseException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aae9f867-9948-44eb-9fc8-cb9f6227762d"), new Object[]{ctx, personInfo, bizDate, rows});
            invokeServiceBefore(svcCtx);
            _generateNewData(ctx, personInfo, bizDate, rows);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (SellHouseException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _generateNewData(Context ctx, PersonInfo personInfo, Date bizDate, Set rows) throws BOSException, SellHouseException;

    public PPMProjectBillCollection getPPMProjectBillCollection (Context ctx) throws BOSException
    {
    	return (PPMProjectBillCollection)(getInsteadCollectOutBillCollection(ctx).cast(PPMProjectBillCollection.class));
    }
    public PPMProjectBillCollection getPPMProjectBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (PPMProjectBillCollection)(getInsteadCollectOutBillCollection(ctx, view).cast(PPMProjectBillCollection.class));
    }
    public PPMProjectBillCollection getPPMProjectBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (PPMProjectBillCollection)(getInsteadCollectOutBillCollection(ctx, oql).cast(PPMProjectBillCollection.class));
    }
    public PPMBillCollection getPPMBillCollection (Context ctx) throws BOSException
    {
    	return (PPMBillCollection)(getInsteadCollectOutBillCollection(ctx).cast(PPMBillCollection.class));
    }
    public PPMBillCollection getPPMBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (PPMBillCollection)(getInsteadCollectOutBillCollection(ctx, view).cast(PPMBillCollection.class));
    }
    public PPMBillCollection getPPMBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (PPMBillCollection)(getInsteadCollectOutBillCollection(ctx, oql).cast(PPMBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getInsteadCollectOutBillCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getInsteadCollectOutBillCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getInsteadCollectOutBillCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getInsteadCollectOutBillCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getInsteadCollectOutBillCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getInsteadCollectOutBillCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getInsteadCollectOutBillCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getInsteadCollectOutBillCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getInsteadCollectOutBillCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getInsteadCollectOutBillCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getInsteadCollectOutBillCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getInsteadCollectOutBillCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}