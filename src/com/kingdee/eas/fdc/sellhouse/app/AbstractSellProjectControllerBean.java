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

import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.base.permission.UserInfo;
import java.util.ArrayList;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;



public abstract class AbstractSellProjectControllerBean extends TreeBaseControllerBean implements SellProjectController
{
    protected AbstractSellProjectControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("2FFBE5AC");
    }

    public SellProjectInfo getSellProjectInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd4a2701-0113-1000-e000-0002c0a812cc"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            SellProjectInfo retValue = (SellProjectInfo)_getValue(ctx, pk, selector);
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

    public SellProjectInfo getSellProjectInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd4a2701-0113-1000-e000-0003c0a812cc"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            SellProjectInfo retValue = (SellProjectInfo)_getValue(ctx, pk);
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

    public SellProjectInfo getSellProjectInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd4a2701-0113-1000-e000-0004c0a812cc"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SellProjectInfo retValue = (SellProjectInfo)_getValue(ctx, oql);
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

    public SellProjectCollection getSellProjectCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd4a2701-0113-1000-e000-0005c0a812cc"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            SellProjectCollection retValue = (SellProjectCollection)_getCollection(ctx, svcCtx);
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

    public SellProjectCollection getSellProjectCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd4a2701-0113-1000-e000-0006c0a812cc"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            SellProjectCollection retValue = (SellProjectCollection)_getCollection(ctx, svcCtx, view);
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

    public SellProjectCollection getSellProjectCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bd4a2701-0113-1000-e000-0007c0a812cc"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            SellProjectCollection retValue = (SellProjectCollection)_getCollection(ctx, svcCtx, oql);
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

    public SellProjectInfo getBaseValue(Context ctx, String idStr) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cba663ec-3bf9-4154-acf4-0085be884e17"), new Object[]{ctx, idStr});
            invokeServiceBefore(svcCtx);
            SellProjectInfo retValue = (SellProjectInfo)_getBaseValue(ctx, idStr);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectValue _getBaseValue(Context ctx, String idStr) throws BOSException;

    public SellProjectCollection getBaseCollection(Context ctx, Set idSet) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aaaeabbf-cdbd-4249-9ca0-31fa031ebffa"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            SellProjectCollection retValue = (SellProjectCollection)_getBaseCollection(ctx, idSet);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _getBaseCollection(Context ctx, Set idSet) throws BOSException;

    public boolean endInit(Context ctx, List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("78d1b53b-5748-4a21-87af-42167b10fd30"), new Object[]{ctx, projectIds, orgUnitId, userInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_endInit(ctx, projectIds, orgUnitId, userInfo);
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
    protected abstract boolean _endInit(Context ctx, List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;

    public boolean unInit(Context ctx, List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4b2a0a30-3492-4b3a-8981-0e746e0dfe9d"), new Object[]{ctx, projectIds, orgUnitId, userInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_unInit(ctx, projectIds, orgUnitId, userInfo);
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
    protected abstract boolean _unInit(Context ctx, List projectIds, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;

    public boolean allEndInit(Context ctx, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8b05afd9-1038-41a6-bdb3-ac784fe3e080"), new Object[]{ctx, orgUnitId, userInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_allEndInit(ctx, orgUnitId, userInfo);
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
    protected abstract boolean _allEndInit(Context ctx, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;

    public boolean allUnInit(Context ctx, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("908ddff2-d362-441d-8b57-9ed605dec26b"), new Object[]{ctx, orgUnitId, userInfo});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_allUnInit(ctx, orgUnitId, userInfo);
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
    protected abstract boolean _allUnInit(Context ctx, String orgUnitId, UserInfo userInfo) throws BOSException, EASBizException;

    public void nextSystem(Context ctx, String comId, UserInfo userInfo) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("de699c48-64a4-4a01-87d9-e6b89db2c324"), new Object[]{ctx, comId, userInfo});
            invokeServiceBefore(svcCtx);
            _nextSystem(ctx, comId, userInfo);
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
    protected abstract void _nextSystem(Context ctx, String comId, UserInfo userInfo) throws BOSException, EASBizException;

    public void preSystem(Context ctx, String comId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("42e39b97-8be2-4802-9d0c-15416c72d387"), new Object[]{ctx, comId});
            invokeServiceBefore(svcCtx);
            _preSystem(ctx, comId);
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
    protected abstract void _preSystem(Context ctx, String comId) throws BOSException, EASBizException;

    public void projectDataUpdate(Context ctx, IObjectValue model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("13715d66-8cef-4f08-b97f-e55b86a20f4e"), new Object[]{ctx, model});
            invokeServiceBefore(svcCtx);
            _projectDataUpdate(ctx, model);
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
    protected abstract void _projectDataUpdate(Context ctx, IObjectValue model) throws BOSException, EASBizException;

    public void allProjectDataUpdate(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f575fceb-1cfb-47c7-ac82-3247cd72fec9"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            _allProjectDataUpdate(ctx);
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
    protected abstract void _allProjectDataUpdate(Context ctx) throws BOSException, EASBizException;

    public void updateToSHEProject(Context ctx, BOSUuid id, BOSUuid orgUnitID, String longNumber) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8d26af59-824c-43f7-859b-55f8861e0b76"), new Object[]{ctx, id, orgUnitID, longNumber});
            invokeServiceBefore(svcCtx);
            _updateToSHEProject(ctx, id, orgUnitID, longNumber);
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
    protected abstract void _updateToSHEProject(Context ctx, BOSUuid id, BOSUuid orgUnitID, String longNumber) throws BOSException, EASBizException;

    public void updateToSellProject(Context ctx, BOSUuid id, String number, String name) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f1c363b3-00b9-4c77-b606-ae883e1c7f6f"), new Object[]{ctx, id, number, name});
            invokeServiceBefore(svcCtx);
            _updateToSellProject(ctx, id, number, name);
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
    protected abstract void _updateToSellProject(Context ctx, BOSUuid id, String number, String name) throws BOSException, EASBizException;

    public ArrayList getSellProTreeNodes(Context ctx, String type) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("79144166-78a5-49b2-8f84-d912c0c66f74"), new Object[]{ctx, type});
            invokeServiceBefore(svcCtx);
            ArrayList retValue = (ArrayList)_getSellProTreeNodes(ctx, type);
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
    protected abstract ArrayList _getSellProTreeNodes(Context ctx, String type) throws BOSException, EASBizException;

    public void updateDeleteStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6960a75d-1367-46bc-a209-407367895ac9"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _updateDeleteStatus(ctx, billId);
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
    protected abstract void _updateDeleteStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public void deleteSellProject(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("751ae7c0-c948-46e5-8b5b-35e67b74412b"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _deleteSellProject(ctx, billId);
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
    protected abstract void _deleteSellProject(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public void deleteProjectInSystem(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("55a12357-c8d4-46d6-a7b3-9158ec536590"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _deleteProjectInSystem(ctx, billId);
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
    protected abstract void _deleteProjectInSystem(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

    public void updateRoomModelForChild(Context ctx, BOSUuid billId, List roomModelList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4f382180-be6c-4388-9f1d-027b46f0f153"), new Object[]{ctx, billId, roomModelList});
            invokeServiceBefore(svcCtx);
            _updateRoomModelForChild(ctx, billId, roomModelList);
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
    protected abstract void _updateRoomModelForChild(Context ctx, BOSUuid billId, List roomModelList) throws BOSException, EASBizException;

    public TreeBaseCollection getTreeBaseCollection (Context ctx) throws BOSException
    {
    	return (TreeBaseCollection)(getSellProjectCollection(ctx).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TreeBaseCollection)(getSellProjectCollection(ctx, view).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (TreeBaseCollection)(getSellProjectCollection(ctx, oql).cast(TreeBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getSellProjectCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getSellProjectCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getSellProjectCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getSellProjectCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getSellProjectCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getSellProjectCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSellProjectCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSellProjectCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSellProjectCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}