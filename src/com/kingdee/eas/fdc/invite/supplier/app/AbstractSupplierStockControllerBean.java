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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import java.util.HashSet;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.invite.supplier.UpdateHistoryTypeEnum;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import java.util.List;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;



public abstract class AbstractSupplierStockControllerBean extends FDCDataBaseControllerBean implements SupplierStockController
{
    protected AbstractSupplierStockControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B04106C");
    }

    public SupplierStockInfo getSupplierStockInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8af90a65-fdc8-4551-9dea-48ee883809c6"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            SupplierStockInfo retValue = (SupplierStockInfo)_getValue(ctx, pk);
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

    public SupplierStockInfo getSupplierStockInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f41e665b-319e-496d-8436-f3c376ebf863"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            SupplierStockInfo retValue = (SupplierStockInfo)_getValue(ctx, pk, selector);
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

    public SupplierStockInfo getSupplierStockInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("edb12aa9-20eb-4efc-be70-fd3854e5d44c"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SupplierStockInfo retValue = (SupplierStockInfo)_getValue(ctx, oql);
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

    public SupplierStockCollection getSupplierStockCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("220a0e16-baf5-47e0-a65d-fd6789d58a15"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            SupplierStockCollection retValue = (SupplierStockCollection)_getCollection(ctx, svcCtx);
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

    public SupplierStockCollection getSupplierStockCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a6b773de-f28b-4f03-902c-a7e3b80c4bf8"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            SupplierStockCollection retValue = (SupplierStockCollection)_getCollection(ctx, svcCtx, view);
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

    public SupplierStockCollection getSupplierStockCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bfe7a736-c9dc-47c4-a07d-37b11cf0d2b4"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SupplierStockCollection retValue = (SupplierStockCollection)_getCollection(ctx, svcCtx, oql);
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b8df2702-e367-4508-bf63-deae6e6a9a25"), new Object[]{ctx, pks});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d1517050-21ed-45e6-b2dc-48bd3ab6b7c4"), new Object[]{ctx, pks});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c5dee966-20f5-4229-a123-ea389843eb5b"), new Object[]{ctx, supObjectValue});
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

    public SupplierCollection getSupplierDataBase(Context ctx, String supLongNumber) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ef743dac-f935-4cce-9533-0e0dd5e77c48"), new Object[]{ctx, supLongNumber});
            invokeServiceBefore(svcCtx);
            SupplierCollection retValue = (SupplierCollection)_getSupplierDataBase(ctx, supLongNumber);
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("757c90d9-3b54-4da2-97fc-65e14f4c578d"), new Object[]{ctx, objCollection, typeLongNumber});
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

    public FDCSplAreaCollection getSericeArea(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2e4e4eed-f553-4822-8974-5032a6188cf3"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            FDCSplAreaCollection retValue = (FDCSplAreaCollection)_getSericeArea(ctx);
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c22b2b2a-2a34-4b2d-ab0c-9f50d2e9e209"), new Object[]{ctx, list});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1a03a8f9-48f7-47f4-af44-869ccbf9cb31"), new Object[]{ctx, list});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("036baddd-79e2-4306-ab5e-0149a375e8cc"), new Object[]{ctx, list});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("93038ccd-0b1f-41a0-9172-ab8d921473fa"), new Object[]{ctx, list});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9d18ebe7-42a4-4cc7-aa43-cd97705b7f7a"), new Object[]{ctx, list});
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

    public void updateHisCount(Context ctx, UpdateHistoryTypeEnum historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("14d976fe-dcd5-4a20-82ea-1b47d09116b7"), new Object[]{ctx, historyType, pk, new Boolean(isAudit)});
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
    protected abstract void _updateHisCount(Context ctx, UpdateHistoryTypeEnum historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException;

    public void beachUpdateHisCount(Context ctx, UpdateHistoryTypeEnum historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("47dbe8d3-1fbf-4eb6-b9a5-8f763284eca3"), new Object[]{ctx, historyType, pks, new Boolean(isAudit)});
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
    protected abstract void _beachUpdateHisCount(Context ctx, UpdateHistoryTypeEnum historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException;

    public void batchAssign2(Context ctx, String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("244d76d2-81d3-438d-9707-e14a31f61fac"), new Object[]{ctx, cuid, diddata, cuiddata});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a336fa3d-8f8f-41e9-aee8-bbed0e465679"), new Object[]{ctx, cuid, sql, basedata, cuData});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6c3e3fc9-bffc-4798-84d7-43e6cdd0ef11"), new Object[]{ctx, objectValue});
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
    	return (FDCDataBaseCollection)(getSupplierStockCollection(ctx).cast(FDCDataBaseCollection.class));
    }
    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCDataBaseCollection)(getSupplierStockCollection(ctx, view).cast(FDCDataBaseCollection.class));
    }
    public FDCDataBaseCollection getFDCDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCDataBaseCollection)(getSupplierStockCollection(ctx, oql).cast(FDCDataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getSupplierStockCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getSupplierStockCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getSupplierStockCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getSupplierStockCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getSupplierStockCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getSupplierStockCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSupplierStockCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSupplierStockCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSupplierStockCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}