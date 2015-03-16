package com.kingdee.eas.fdc.basecrm.app;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.util.List;
import com.kingdee.eas.framework.DataBaseCollection;



public abstract class AbstractFDCOrgCustomerControllerBean extends FDCBaseCustomerControllerBean implements FDCOrgCustomerController
{
    protected AbstractFDCOrgCustomerControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("79DAF899");
    }

    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bb572b59-9945-4012-a0ae-140bd457772d"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            FDCOrgCustomerInfo retValue = (FDCOrgCustomerInfo)_getValue(ctx, pk);
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

    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("59f1f0b3-5b73-4a31-a3d2-0fefcca7be5a"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            FDCOrgCustomerInfo retValue = (FDCOrgCustomerInfo)_getValue(ctx, pk, selector);
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

    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ad8f8f27-1e4e-4d8e-97ac-60006f33ba82"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCOrgCustomerInfo retValue = (FDCOrgCustomerInfo)_getValue(ctx, oql);
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

    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3b7d1f32-29f9-41d6-a3db-31d0e5d6f381"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            FDCOrgCustomerCollection retValue = (FDCOrgCustomerCollection)_getCollection(ctx, svcCtx);
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

    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("062256cd-d09f-416a-8c06-13b04c4612cb"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            FDCOrgCustomerCollection retValue = (FDCOrgCustomerCollection)_getCollection(ctx, svcCtx, view);
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

    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1ccd6c4c-c22d-4aa9-ac70-9b68234a01a9"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCOrgCustomerCollection retValue = (FDCOrgCustomerCollection)_getCollection(ctx, svcCtx, oql);
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

    public void changeCusName(Context ctx, String cusId, String newName) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f95433ab-1136-49dd-af90-2d6e4da5ee96"), new Object[]{ctx, cusId, newName});
            invokeServiceBefore(svcCtx);
            _changeCusName(ctx, cusId, newName);
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
    protected abstract void _changeCusName(Context ctx, String cusId, String newName) throws BOSException, EASBizException;

    public void updateCustomerInfo(Context ctx, List ids) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e5596195-fecf-4987-b079-bd623e22b59a"), new Object[]{ctx, ids});
            invokeServiceBefore(svcCtx);
            _updateCustomerInfo(ctx, ids);
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
    protected abstract void _updateCustomerInfo(Context ctx, List ids) throws BOSException, EASBizException;

    public void shareCustomer(Context ctx, List cusIds, String shareOrgId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c1b1fc9e-31f7-48ca-9e9a-2b574e302e85"), new Object[]{ctx, cusIds, shareOrgId});
            invokeServiceBefore(svcCtx);
            _shareCustomer(ctx, cusIds, shareOrgId);
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
    protected abstract void _shareCustomer(Context ctx, List cusIds, String shareOrgId) throws BOSException, EASBizException;

    public void mergeCustomer(Context ctx, List srcIds, String toId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b449dd69-97c4-4533-be0b-966650382210"), new Object[]{ctx, srcIds, toId});
            invokeServiceBefore(svcCtx);
            _mergeCustomer(ctx, srcIds, toId);
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
    protected abstract void _mergeCustomer(Context ctx, List srcIds, String toId) throws BOSException, EASBizException;

    public void importCustomer(Context ctx, IObjectCollection res) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6faea2d6-3a90-4289-8d60-2ac87cf562d3"), new Object[]{ctx, res});
            invokeServiceBefore(svcCtx);
            _importCustomer(ctx, res);
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
    protected abstract void _importCustomer(Context ctx, IObjectCollection res) throws BOSException, EASBizException;

    public void submitEnterpriceCustomer(Context ctx, FDCOrgCustomerInfo model, String name, String phone) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("49108c70-55c2-4ae1-abb0-6c2acd0eb186"), new Object[]{ctx, model, name, phone});
            invokeServiceBefore(svcCtx);
            _submitEnterpriceCustomer(ctx, model, name, phone);
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
    protected abstract void _submitEnterpriceCustomer(Context ctx, IObjectValue model, String name, String phone) throws BOSException, EASBizException;

    public void updateEnterpriceCustomer(Context ctx, Set set, String str1, String str2, boolean isLinkMan) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4633e916-1483-4438-b741-cde2a06b4121"), new Object[]{ctx, set, str1, str2, new Boolean(isLinkMan)});
            invokeServiceBefore(svcCtx);
            _updateEnterpriceCustomer(ctx, set, str1, str2, isLinkMan);
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
    protected abstract void _updateEnterpriceCustomer(Context ctx, Set set, String str1, String str2, boolean isLinkMan) throws BOSException, EASBizException;

    public FDCBaseCustomerCollection getFDCBaseCustomerCollection (Context ctx) throws BOSException
    {
    	return (FDCBaseCustomerCollection)(getFDCOrgCustomerCollection(ctx).cast(FDCBaseCustomerCollection.class));
    }
    public FDCBaseCustomerCollection getFDCBaseCustomerCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBaseCustomerCollection)(getFDCOrgCustomerCollection(ctx, view).cast(FDCBaseCustomerCollection.class));
    }
    public FDCBaseCustomerCollection getFDCBaseCustomerCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBaseCustomerCollection)(getFDCOrgCustomerCollection(ctx, oql).cast(FDCBaseCustomerCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getFDCOrgCustomerCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getFDCOrgCustomerCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getFDCOrgCustomerCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCOrgCustomerCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCOrgCustomerCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCOrgCustomerCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCOrgCustomerCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCOrgCustomerCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCOrgCustomerCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}