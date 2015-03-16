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
import com.kingdee.eas.fdc.propertymgmt.PPMProjectDataBaseCollection;
import com.kingdee.eas.fdc.propertymgmt.PPMDataBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.propertymgmt.app.PPMProjectDataBaseControllerBean;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineSellProjectInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;



public abstract class AbstractMoneyDefineSellProjectControllerBean extends PPMProjectDataBaseControllerBean implements MoneyDefineSellProjectController
{
    protected AbstractMoneyDefineSellProjectControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("A10D4667");
    }

    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ef5f3879-1ad9-42bc-aed1-a3616b50df45"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            MoneyDefineSellProjectInfo retValue = (MoneyDefineSellProjectInfo)_getValue(ctx, pk);
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

    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cf08ac73-075a-4406-8ac5-edb6dabd20cc"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            MoneyDefineSellProjectInfo retValue = (MoneyDefineSellProjectInfo)_getValue(ctx, pk, selector);
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

    public MoneyDefineSellProjectInfo getMoneyDefineSellProjectInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bf7bb162-a847-447c-b2a2-e22276a74708"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MoneyDefineSellProjectInfo retValue = (MoneyDefineSellProjectInfo)_getValue(ctx, oql);
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

    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a307266c-90d1-4ef8-8154-0b224cf7d36d"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            MoneyDefineSellProjectCollection retValue = (MoneyDefineSellProjectCollection)_getCollection(ctx, svcCtx);
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

    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cfa43310-98d9-4059-a739-5d86ba6e2ecd"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            MoneyDefineSellProjectCollection retValue = (MoneyDefineSellProjectCollection)_getCollection(ctx, svcCtx, view);
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

    public MoneyDefineSellProjectCollection getMoneyDefineSellProjectCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7203756e-0aa0-4b5b-a85f-49e84a8b3d0b"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MoneyDefineSellProjectCollection retValue = (MoneyDefineSellProjectCollection)_getCollection(ctx, svcCtx, oql);
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

    public PPMProjectDataBaseCollection getPPMProjectDataBaseCollection (Context ctx) throws BOSException
    {
    	return (PPMProjectDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx).cast(PPMProjectDataBaseCollection.class));
    }
    public PPMProjectDataBaseCollection getPPMProjectDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (PPMProjectDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, view).cast(PPMProjectDataBaseCollection.class));
    }
    public PPMProjectDataBaseCollection getPPMProjectDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (PPMProjectDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, oql).cast(PPMProjectDataBaseCollection.class));
    }
    public PPMDataBaseCollection getPPMDataBaseCollection (Context ctx) throws BOSException
    {
    	return (PPMDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx).cast(PPMDataBaseCollection.class));
    }
    public PPMDataBaseCollection getPPMDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (PPMDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, view).cast(PPMDataBaseCollection.class));
    }
    public PPMDataBaseCollection getPPMDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (PPMDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, oql).cast(PPMDataBaseCollection.class));
    }
    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx) throws BOSException
    {
    	return (FDCDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx).cast(FDCDataBaseCollection.class));
    }
    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, view).cast(FDCDataBaseCollection.class));
    }
    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCDataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, oql).cast(FDCDataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getMoneyDefineSellProjectCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getMoneyDefineSellProjectCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getMoneyDefineSellProjectCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getMoneyDefineSellProjectCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getMoneyDefineSellProjectCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getMoneyDefineSellProjectCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getMoneyDefineSellProjectCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getMoneyDefineSellProjectCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}