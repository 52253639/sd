package com.kingdee.eas.fdc.invite.markesupplier.app;

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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import java.util.HashSet;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.util.List;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.framework.DataBaseCollection;



public abstract class AbstractMarketSupplierStockControllerBean extends FDCDataBaseControllerBean implements MarketSupplierStockController
{
    protected AbstractMarketSupplierStockControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("98690D80");
    }

    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4f2ec725-d186-4860-898e-2f99ab9fcdbb"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockInfo retValue = (MarketSupplierStockInfo)_getValue(ctx, pk);
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

    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a5aee625-48c8-4f2b-b201-ad02d4088af8"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockInfo retValue = (MarketSupplierStockInfo)_getValue(ctx, pk, selector);
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

    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("76fd1eb1-a230-4d7e-94bf-526899c96399"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockInfo retValue = (MarketSupplierStockInfo)_getValue(ctx, oql);
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

    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f54a073c-b8c5-4d87-8704-44941215a0b3"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockCollection retValue = (MarketSupplierStockCollection)_getCollection(ctx, svcCtx);
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

    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f55930d2-6c35-4864-85ae-26ecc258b916"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockCollection retValue = (MarketSupplierStockCollection)_getCollection(ctx, svcCtx, view);
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

    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c613a915-6226-4173-8931-0d5629b07c1e"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockCollection retValue = (MarketSupplierStockCollection)_getCollection(ctx, svcCtx, oql);
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

    public Map approve(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7924dda5-f664-43a8-8b61-c660d727ba2d"), new Object[]{ctx, pks});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_approve(ctx, pks);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _approve(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException;

    public Map unApprove(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("98bf55e4-edbf-451c-b2c3-f9fc9a5faa70"), new Object[]{ctx, pks});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_unApprove(ctx, pks);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _unApprove(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException;

    public void submitTwo(Context ctx, IObjectValue supObjectValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("626c1003-e1ba-4ffc-97d6-f968256e9d77"), new Object[]{ctx, supObjectValue});
            invokeServiceBefore(svcCtx);
            _submitTwo(ctx, supObjectValue);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _submitTwo(Context ctx, IObjectValue supObjectValue) throws BOSException, EASBizException;

    public MarketSupplierStockCollection getSupplierDataBase(Context ctx, String supLongNumber) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("332f20dc-d15f-40a3-b71d-6314e12de55e"), new Object[]{ctx, supLongNumber});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockCollection retValue = (MarketSupplierStockCollection)_getSupplierDataBase(ctx, supLongNumber);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _getSupplierDataBase(Context ctx, String supLongNumber) throws BOSException, EASBizException;

    public Map importDataBase(Context ctx, IObjectCollection objCollection, String typeLongNumber) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("02d762ba-bbde-4853-8367-666434f0e4f0"), new Object[]{ctx, objCollection, typeLongNumber});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_importDataBase(ctx, objCollection, typeLongNumber);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _importDataBase(Context ctx, IObjectCollection objCollection, String typeLongNumber) throws BOSException, EASBizException;

    public MarketSupplierStockCollection getSericeArea(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b9f58f53-2c0b-4f16-8482-d05eb8fdbd69"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            MarketSupplierStockCollection retValue = (MarketSupplierStockCollection)_getSericeArea(ctx);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _getSericeArea(Context ctx) throws BOSException, EASBizException;

    public Set getDataBase(Context ctx, List list) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("188716dd-a410-43e5-9225-c62a4c7ea0fd"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getDataBase(ctx, list);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getDataBase(Context ctx, List list) throws BOSException, EASBizException;

    public Set getAHDBValue(Context ctx, List list) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9a1bdaae-6da2-4a6f-b396-efc5a8ee20c6"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getAHDBValue(ctx, list);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getAHDBValue(Context ctx, List list) throws BOSException, EASBizException;

    public Set getEHDBValue(Context ctx, List list) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c95db15c-5129-40bb-94dc-4c3a3c4f0a4f"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getEHDBValue(ctx, list);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getEHDBValue(Context ctx, List list) throws BOSException, EASBizException;

    public Set getSHDBValue(Context ctx, List list) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3dc9d92d-03a0-46a4-a279-95e90db4dbd3"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getSHDBValue(ctx, list);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Set _getSHDBValue(Context ctx, List list) throws BOSException, EASBizException;

    public boolean getATRFValue(Context ctx, List list) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bc222820-a59a-4902-8faf-9691d011df69"), new Object[]{ctx, list});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_getATRFValue(ctx, list);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _getATRFValue(Context ctx, List list) throws BOSException, EASBizException;

    public void updateHisCount(Context ctx,String  historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("02810b48-d4b6-4182-a89a-af2fab684b37"), new Object[]{ctx, historyType, pk, new Boolean(isAudit)});
            invokeServiceBefore(svcCtx);
            _updateHisCount(ctx, historyType, pk, isAudit);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateHisCount(Context ctx, String historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException;

    public void beachUpdateHisCount(Context ctx, String historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ac3ac9a4-63e9-4901-b433-1c4d7663a694"), new Object[]{ctx, historyType, pks, new Boolean(isAudit)});
            invokeServiceBefore(svcCtx);
            _beachUpdateHisCount(ctx, historyType, pks, isAudit);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _beachUpdateHisCount(Context ctx, String historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException;

    public void batchAssign2(Context ctx, String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("541907e6-89ed-48ba-a04b-e8369e4f78ab"), new Object[]{ctx, cuid, diddata, cuiddata});
            invokeServiceBefore(svcCtx);
            _batchAssign2(ctx, cuid, diddata, cuiddata);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _batchAssign2(Context ctx, String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException;

    public void batchAssign2(Context ctx, String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5d620872-9271-4986-b774-1aa480771c00"), new Object[]{ctx, cuid, sql, basedata, cuData});
            invokeServiceBefore(svcCtx);
            _batchAssign2(ctx, cuid, sql, basedata, cuData);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _batchAssign2(Context ctx, String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException;

    public void addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8aaecb69-a5e2-4b1f-9582-0b79e38d5878"), new Object[]{ctx, objectValue});
            invokeServiceBefore(svcCtx);
            _addToSysSupplier(ctx, objectValue);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException;

    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx) throws BOSException
    {
    	return (FDCDataBaseCollection)(getMarketSupplierStockCollection(ctx).cast(FDCDataBaseCollection.class));
    }
    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCDataBaseCollection)(getMarketSupplierStockCollection(ctx, view).cast(FDCDataBaseCollection.class));
    }
    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCDataBaseCollection)(getMarketSupplierStockCollection(ctx, oql).cast(FDCDataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getMarketSupplierStockCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getMarketSupplierStockCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getMarketSupplierStockCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getMarketSupplierStockCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getMarketSupplierStockCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getMarketSupplierStockCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getMarketSupplierStockCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getMarketSupplierStockCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getMarketSupplierStockCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}