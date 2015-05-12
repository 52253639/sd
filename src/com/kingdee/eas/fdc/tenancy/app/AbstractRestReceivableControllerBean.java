package com.kingdee.eas.fdc.tenancy.app;

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
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.tenancy.RestReceivableInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.RestReceivableCollection;



public abstract class AbstractRestReceivableControllerBean extends CoreBillBaseControllerBean implements RestReceivableController
{
    protected AbstractRestReceivableControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("B42ABEF9");
    }

    public RestReceivableCollection getRestReceivableCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("15733509-fa92-4c16-ad84-c5d1e59b8d46"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RestReceivableCollection retValue = (RestReceivableCollection)_getCollection(ctx, svcCtx);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RestReceivableCollection)svcCtx.getMethodReturnValue();
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

    public RestReceivableCollection getRestReceivableCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3add228d-cc2b-4d33-aaf2-87fd89f59745"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RestReceivableCollection retValue = (RestReceivableCollection)_getCollection(ctx, svcCtx, view);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RestReceivableCollection)svcCtx.getMethodReturnValue();
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

    public RestReceivableCollection getRestReceivableCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0e4f44f0-699f-47c7-b7db-272dabc4a3e1"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RestReceivableCollection retValue = (RestReceivableCollection)_getCollection(ctx, svcCtx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RestReceivableCollection)svcCtx.getMethodReturnValue();
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

    public RestReceivableInfo getRestReceivableInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ad14ed22-7d87-4f16-878e-a50f4a59bcbb"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RestReceivableInfo retValue = (RestReceivableInfo)_getValue(ctx, pk);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RestReceivableInfo)svcCtx.getMethodReturnValue();
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

    public RestReceivableInfo getRestReceivableInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7d323bfb-b1a2-4446-a718-989f50a55cf5"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RestReceivableInfo retValue = (RestReceivableInfo)_getValue(ctx, pk, selector);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RestReceivableInfo)svcCtx.getMethodReturnValue();
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

    public RestReceivableInfo getRestReceivableInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa8bfa7f-2623-4b82-98ee-eb17480979f2"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            RestReceivableInfo retValue = (RestReceivableInfo)_getValue(ctx, oql);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
        
          return (RestReceivableInfo)svcCtx.getMethodReturnValue();
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

    public void setAuditting(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ee477103-1c00-4596-a978-1d19ef0ee040"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setAuditting(ctx, billId);
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
    protected void _setAuditting(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void setSubmit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("44a5371d-9551-4f5a-8076-494174c7a8df"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _setSubmit(ctx, billId);
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
    protected void _setSubmit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3803ce41-7412-4cc9-bb07-6db68c028e35"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _audit(ctx, billId);
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
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3f4b806d-c51c-4bd6-924b-27b16006ced1"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
              if(!svcCtx.invokeBreak()) {
            _unAudit(ctx, billId);
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
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {    	
        return;
    }

    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getRestReceivableCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getRestReceivableCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getRestReceivableCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getRestReceivableCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getRestReceivableCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getRestReceivableCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getRestReceivableCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getRestReceivableCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getRestReceivableCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}