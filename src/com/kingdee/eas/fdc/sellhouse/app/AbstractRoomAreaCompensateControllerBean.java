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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.CompensateRoomListCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;



public abstract class AbstractRoomAreaCompensateControllerBean extends FDCBillControllerBean implements RoomAreaCompensateController
{
    protected AbstractRoomAreaCompensateControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("455E117A");
    }

    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("75befe4c-0117-1000-e000-0012c0a812cc"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            RoomAreaCompensateInfo retValue = (RoomAreaCompensateInfo)_getValue(ctx, pk);
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

    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("75befe4c-0117-1000-e000-0013c0a812cc"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            RoomAreaCompensateInfo retValue = (RoomAreaCompensateInfo)_getValue(ctx, pk, selector);
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

    public RoomAreaCompensateInfo getRoomAreaCompensateInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("75befe4c-0117-1000-e000-0014c0a812cc"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            RoomAreaCompensateInfo retValue = (RoomAreaCompensateInfo)_getValue(ctx, oql);
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

    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("75befe4c-0117-1000-e000-0015c0a812cc"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            RoomAreaCompensateCollection retValue = (RoomAreaCompensateCollection)_getCollection(ctx, svcCtx, view);
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

    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("75befe4c-0117-1000-e000-0016c0a812cc"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            RoomAreaCompensateCollection retValue = (RoomAreaCompensateCollection)_getCollection(ctx, svcCtx);
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

    public RoomAreaCompensateCollection getRoomAreaCompensateCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("75befe4c-0117-1000-e000-0017c0a812cc"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            RoomAreaCompensateCollection retValue = (RoomAreaCompensateCollection)_getCollection(ctx, svcCtx, oql);
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

    public void batchSave(Context ctx, List idList, Map valueMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e0d79961-0119-1000-e000-0007c0a812a1"), new Object[]{ctx, idList, valueMap});
            invokeServiceBefore(svcCtx);
            _batchSave(ctx, idList, valueMap);
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
    protected abstract void _batchSave(Context ctx, List idList, Map valueMap) throws BOSException, EASBizException;

    public Map calcAmount(Context ctx, List idList, String schemeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3843a9f0-011a-1000-e000-0001c0a812a1"), new Object[]{ctx, idList, schemeId});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_calcAmount(ctx, idList, schemeId);
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
    protected abstract Map _calcAmount(Context ctx, List idList, String schemeId) throws BOSException, EASBizException;

    public void submitToWorkFlow(Context ctx, String buildingId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7a5a2a00-011a-1000-e000-0001c0a812a1"), new Object[]{ctx, buildingId});
            invokeServiceBefore(svcCtx);
            _submitToWorkFlow(ctx, buildingId);
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
    protected abstract void _submitToWorkFlow(Context ctx, String buildingId) throws BOSException, EASBizException;

    public IRowSet getRoomInfoList(Context ctx, String filterStr) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d24e19a4-43af-4eaa-9c72-0517e32d3493"), new Object[]{ctx, filterStr});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getRoomInfoList(ctx, filterStr);
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
    protected abstract IRowSet _getRoomInfoList(Context ctx, String filterStr) throws BOSException, EASBizException;

    public IRowSet getCompenstateRoomInfo(Context ctx, String roomId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("687958cc-df0a-411f-ad06-4e9b9a706858"), new Object[]{ctx, roomId});
            invokeServiceBefore(svcCtx);
            IRowSet retValue = (IRowSet)_getCompenstateRoomInfo(ctx, roomId);
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
    protected abstract IRowSet _getCompenstateRoomInfo(Context ctx, String roomId) throws BOSException, EASBizException;

    public void setNullify(Context ctx, String idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("499959fb-1dda-4cf3-b542-bb6e0aab5dc4"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            _setNullify(ctx, idList);
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
    protected abstract void _setNullify(Context ctx, String idList) throws BOSException, EASBizException;

    public Map calcAmountForSHE(Context ctx, List roomList, String schemeId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a0d1ace9-3025-4060-bb72-d90725ad326a"), new Object[]{ctx, roomList, schemeId});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_calcAmountForSHE(ctx, roomList, schemeId);
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
    protected abstract Map _calcAmountForSHE(Context ctx, List roomList, String schemeId) throws BOSException, EASBizException;

    public void auditAndCalcSellAmount(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("969a12e3-3e41-47bc-9d2b-9753f097ac67"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _auditAndCalcSellAmount(ctx, id);
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
    protected abstract void _auditAndCalcSellAmount(Context ctx, String id) throws BOSException, EASBizException;

    public void unAuditAndCalcSellAmount(Context ctx, String id) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f3787e83-015d-44c7-afb3-37f07135d615"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            _unAuditAndCalcSellAmount(ctx, id);
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
    protected abstract void _unAuditAndCalcSellAmount(Context ctx, String id) throws BOSException, EASBizException;

    public void setAuditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e8e99097-17b0-4468-8c52-094c738aa65b"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _setAuditing(ctx, billId);
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
    protected abstract void _setAuditing(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public void setSubmit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7f33e582-97d1-4a33-b726-09ac8fb219db"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _setSubmit(ctx, billId);
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
    protected abstract void _setSubmit(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public void deleteCompensateInfo(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3fb1f2b9-9668-4395-a1ef-66eb863cad09"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _deleteCompensateInfo(ctx, billId);
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
    protected abstract void _deleteCompensateInfo(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public void createBillForSign(Context ctx, CompensateRoomListCollection compColl) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7ece681f-0eb9-45fd-840f-52e73f9f34ad"), new Object[]{ctx, compColl});
            invokeServiceBefore(svcCtx);
            _createBillForSign(ctx, compColl);
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
    protected abstract void _createBillForSign(Context ctx, CompensateRoomListCollection compColl) throws BOSException, EASBizException;

    public void deleteBillFromSign(Context ctx, String roomId, SignManageCollection comColl) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d171de7e-467b-4c67-9b0e-703e8022da05"), new Object[]{ctx, roomId, comColl});
            invokeServiceBefore(svcCtx);
            _deleteBillFromSign(ctx, roomId, comColl);
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
    protected abstract void _deleteBillFromSign(Context ctx, String roomId, SignManageCollection comColl) throws BOSException, EASBizException;

    public void createRoomCompensateForView(Context ctx, List roomIdList, String compId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ffe93a62-9f33-4e97-ac65-fcb84d54ba1b"), new Object[]{ctx, roomIdList, compId});
            invokeServiceBefore(svcCtx);
            _createRoomCompensateForView(ctx, roomIdList, compId);
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
    protected abstract void _createRoomCompensateForView(Context ctx, List roomIdList, String compId) throws BOSException, EASBizException;

    public void deleteRoomCompensateForView(Context ctx, List roomIdList, String comId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d24cd70a-96a7-4b61-a16e-338ecc6df88c"), new Object[]{ctx, roomIdList, comId});
            invokeServiceBefore(svcCtx);
            _deleteRoomCompensateForView(ctx, roomIdList, comId);
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
    protected abstract void _deleteRoomCompensateForView(Context ctx, List roomIdList, String comId) throws BOSException, EASBizException;

    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getRoomAreaCompensateCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getRoomAreaCompensateCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getRoomAreaCompensateCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getRoomAreaCompensateCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getRoomAreaCompensateCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getRoomAreaCompensateCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getRoomAreaCompensateCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getRoomAreaCompensateCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getRoomAreaCompensateCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getRoomAreaCompensateCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getRoomAreaCompensateCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getRoomAreaCompensateCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}