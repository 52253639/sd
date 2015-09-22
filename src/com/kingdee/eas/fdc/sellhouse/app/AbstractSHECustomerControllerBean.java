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
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import java.util.Map;
import java.util.HashMap;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerCollection;
import java.util.List;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.basecrm.app.FDCBaseCustomerControllerBean;



public abstract class AbstractSHECustomerControllerBean extends FDCBaseCustomerControllerBean implements SHECustomerController
{
    protected AbstractSHECustomerControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("F1713EF3");
    }

    public SHECustomerInfo getSHECustomerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("67ab7a2d-b051-410b-819a-4d0b5b3e2822"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SHECustomerInfo retValue = (SHECustomerInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SHECustomerInfo)svcCtx.getMethodReturnValue();
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

    public SHECustomerInfo getSHECustomerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8b78a773-9f45-45e8-a00e-a2c3df53e4f9"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SHECustomerInfo retValue = (SHECustomerInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SHECustomerInfo)svcCtx.getMethodReturnValue();
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

    public SHECustomerInfo getSHECustomerInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("35e0c1a1-9a36-4df8-8453-ae852fa421c8"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SHECustomerInfo retValue = (SHECustomerInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SHECustomerInfo)svcCtx.getMethodReturnValue();
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

    public SHECustomerCollection getSHECustomerCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ce6d6c3f-c06d-44ab-b635-ce097e732abf"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SHECustomerCollection retValue = (SHECustomerCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SHECustomerCollection)svcCtx.getMethodReturnValue();
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

    public SHECustomerCollection getSHECustomerCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fdf820a9-5fb4-49d3-82b7-295f26eb2eee"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SHECustomerCollection retValue = (SHECustomerCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SHECustomerCollection)svcCtx.getMethodReturnValue();
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

    public SHECustomerCollection getSHECustomerCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("142b9278-ac43-4ae6-9701-4e9684ae85a8"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            SHECustomerCollection retValue = (SHECustomerCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (SHECustomerCollection)svcCtx.getMethodReturnValue();
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

    public void updateData(Context ctx, List ids) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("639ec97b-6973-43ee-b151-782f94ac4028"), new Object[]{ctx, ids});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _updateData(ctx, ids);
            }
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
    protected void _updateData(Context ctx, List ids) throws BOSException, EASBizException
    {    	
        return;
    }

    public void mergeCustomer(Context ctx, List srcIds, String toId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f20eba83-c6ed-4960-9a1c-112f7aee7c5f"), new Object[]{ctx, srcIds, toId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _mergeCustomer(ctx, srcIds, toId);
            }
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
    protected void _mergeCustomer(Context ctx, List srcIds, String toId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void changeName(Context ctx, SHECustomerInfo model, Map map) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("422f5b86-0548-4b03-8ee9-cfb9efbb1019"), new Object[]{ctx, model, map});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _changeName(ctx, model, map);
            }
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
    protected void _changeName(Context ctx, IObjectValue model, Map map) throws BOSException, EASBizException
    {    	
        return;
    }

    public void shareCustomer(Context ctx, SHECustomerCollection objectColl, Map map) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a03e91c5-5277-490f-a445-4631d01c698d"), new Object[]{ctx, objectColl, map});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _shareCustomer(ctx, objectColl, map);
            }
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
    protected void _shareCustomer(Context ctx, IObjectCollection objectColl, Map map) throws BOSException, EASBizException
    {    	
        return;
    }

    public void deliverCustomer(Context ctx, SHECustomerInfo model, Map map) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e5611d2c-292d-4544-8180-d6c1f3720b13"), new Object[]{ctx, model, map});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _deliverCustomer(ctx, model, map);
            }
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
    protected void _deliverCustomer(Context ctx, IObjectValue model, Map map) throws BOSException, EASBizException
    {    	
        return;
    }

    public void importCustomer(Context ctx, SHECustomerCollection res, SellProjectInfo sellProject) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("59fc12a2-19eb-46b3-9c8f-025fe71ad616"), new Object[]{ctx, res, sellProject});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _importCustomer(ctx, res, sellProject);
            }
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
    protected void _importCustomer(Context ctx, IObjectCollection res, IObjectValue sellProject) throws BOSException, EASBizException
    {    	
        return;
    }

    public void submitEnterpriceCustomer(Context ctx, SHECustomerInfo model, String name, String phone) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2c3ee0e0-2743-405a-b064-996b74408e3c"), new Object[]{ctx, model, name, phone});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _submitEnterpriceCustomer(ctx, model, name, phone);
            }
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
    protected void _submitEnterpriceCustomer(Context ctx, IObjectValue model, String name, String phone) throws BOSException, EASBizException
    {    	
        return;
    }

    public IObjectPK submitAll(Context ctx, HashMap hashMap, IObjectValue editData) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5a6c1b28-d8f5-4c22-a769-bc82b77d77c8"), new Object[]{ctx, hashMap, editData});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            IObjectPK retValue = (IObjectPK)_submitAll(ctx, hashMap, editData);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (IObjectPK)svcCtx.getMethodReturnValue();
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
    protected IObjectPK _submitAll(Context ctx, HashMap hashMap, IObjectValue editData) throws BOSException, EASBizException
    {    	
        return null;
    }

    public FDCBaseCustomerCollection getFDCBaseCustomerCollection (Context ctx) throws BOSException
    {
    	return (FDCBaseCustomerCollection)(getSHECustomerCollection(ctx).cast(FDCBaseCustomerCollection.class));
    }
    public FDCBaseCustomerCollection getFDCBaseCustomerCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBaseCustomerCollection)(getSHECustomerCollection(ctx, view).cast(FDCBaseCustomerCollection.class));
    }
    public FDCBaseCustomerCollection getFDCBaseCustomerCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBaseCustomerCollection)(getSHECustomerCollection(ctx, oql).cast(FDCBaseCustomerCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getSHECustomerCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getSHECustomerCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getSHECustomerCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getSHECustomerCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getSHECustomerCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getSHECustomerCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getSHECustomerCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getSHECustomerCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getSHECustomerCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}