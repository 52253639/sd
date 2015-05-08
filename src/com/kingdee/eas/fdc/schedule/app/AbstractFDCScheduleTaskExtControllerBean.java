package com.kingdee.eas.fdc.schedule.app;

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
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtCollection;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;



public abstract class AbstractFDCScheduleTaskExtControllerBean extends CoreBaseControllerBean implements FDCScheduleTaskExtController
{
    protected AbstractFDCScheduleTaskExtControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("99CA6BF6");
    }

    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("20c38f86-4429-4599-a684-fbc4b865cfde"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_exists(ctx, pk);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
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
    protected boolean _exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        return super._exists(ctx, pk);
    }

    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2a9fa46a-07c2-4870-a68a-923546cf93d1"), new Object[]{ctx, filter});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_exists(ctx, filter);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
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
    protected boolean _exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException
    {
        return super._exists(ctx, filter);
    }

    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3b0c2249-0109-4bb9-a5d4-994ddfd7815a"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_exists(ctx, oql);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
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
    protected boolean _exists(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._exists(ctx, oql);
    }

    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("73589682-d14a-4f2d-8a42-ae9c24679adc"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            FDCScheduleTaskExtInfo retValue = (FDCScheduleTaskExtInfo)_getValue(ctx, pk);
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

    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4a5141ff-0829-4417-a768-6b2094e681eb"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            FDCScheduleTaskExtInfo retValue = (FDCScheduleTaskExtInfo)_getValue(ctx, pk, selector);
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

    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f153814c-0b8c-4555-a946-add73d35ea78"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCScheduleTaskExtInfo retValue = (FDCScheduleTaskExtInfo)_getValue(ctx, oql);
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

    public IObjectPK addnew(Context ctx, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("fa54658d-dea4-4399-a555-80fbce21d132"), new Object[]{ctx, model});
            invokeServiceBefore(svcCtx);
            IObjectPK retValue = (IObjectPK)_addnew(ctx, model);
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
    protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException
    {
        return super._addnew(ctx, model);
    }

    public void addnew(Context ctx, IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6390adc2-872a-467c-913e-675a4ea8c306"), new Object[]{ctx, pk, model});
            invokeServiceBefore(svcCtx);
            _addnew(ctx, pk, model);
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
    protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
        super._addnew(ctx, pk, model);
    }

    public void update(Context ctx, IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f5ceeb8f-17f5-4369-ac88-9b7c9e05b721"), new Object[]{ctx, pk, model});
            invokeServiceBefore(svcCtx);
            _update(ctx, pk, model);
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
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
        super._update(ctx, pk, model);
    }

    public void updatePartial(Context ctx, FDCScheduleTaskExtInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("74e36504-933a-4e75-9842-6fb33f614aed"), new Object[]{ctx, model, selector});
            invokeServiceBefore(svcCtx);
            _updatePartial(ctx, model, selector);
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
    protected void _updatePartial(Context ctx, IObjectValue model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        super._updatePartial(ctx, model, selector);
    }

    public void updateBigObject(Context ctx, IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("37b4ec1f-0d5c-44b5-a501-a46ec0f183b7"), new Object[]{ctx, pk, model});
            invokeServiceBefore(svcCtx);
            _updateBigObject(ctx, pk, model);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected void _updateBigObject(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException
    {
        innerUpdateBigObject(ctx, pk, model);
    }

    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("03171733-c5a9-4bdc-8509-d1231d2063a9"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            _delete(ctx, pk);
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
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        super._delete(ctx, pk);
    }

    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1d1945cc-8d3f-420a-b0a2-64ba79e2ac96"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            IObjectPK[] retValue = (IObjectPK[])_getPKList(ctx);
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
    protected IObjectPK[] _getPKList(Context ctx) throws BOSException, EASBizException
    {
        return super._getPKList(ctx);
    }

    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1cc4310d-bb6c-44d6-842d-b08167dd36c5"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            IObjectPK[] retValue = (IObjectPK[])_getPKList(ctx, oql);
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
    protected IObjectPK[] _getPKList(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._getPKList(ctx, oql);
    }

    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b526c0d0-c891-4f52-86a6-3c3714c9cbf1"), new Object[]{ctx, filter, sorter});
            invokeServiceBefore(svcCtx);
            IObjectPK[] retValue = (IObjectPK[])_getPKList(ctx, filter, sorter);
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
    protected IObjectPK[] _getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        return super._getPKList(ctx, filter, sorter);
    }

    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e8bba74f-de79-468c-901f-a871d10c16a3"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            FDCScheduleTaskExtCollection retValue = (FDCScheduleTaskExtCollection)_getCollection(ctx, svcCtx);
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

    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f5841d66-2475-4886-bbb9-ad8d1631f0a9"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            FDCScheduleTaskExtCollection retValue = (FDCScheduleTaskExtCollection)_getCollection(ctx, svcCtx, view);
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

    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("e3733943-e135-4da6-90ad-694c66af5bbd"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCScheduleTaskExtCollection retValue = (FDCScheduleTaskExtCollection)_getCollection(ctx, svcCtx, oql);
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

    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ce83729e-7efe-4540-8476-3d6e594122fe"), new Object[]{ctx, filter});
            invokeServiceBefore(svcCtx);
            IObjectPK[] retValue = (IObjectPK[])_delete(ctx, filter);
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
    protected IObjectPK[] _delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException
    {
        return super._delete(ctx, filter);
    }

    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("10709a91-14b6-4398-8472-f052351e94c2"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            IObjectPK[] retValue = (IObjectPK[])_delete(ctx, oql);
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
    protected IObjectPK[] _delete(Context ctx, String oql) throws BOSException, EASBizException
    {
        return super._delete(ctx, oql);
    }

    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0a2dc0ba-b547-453c-89ae-9483f8867065"), new Object[]{ctx, arrayPK});
            invokeServiceBefore(svcCtx);
            _delete(ctx, arrayPK);
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
    protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        super._delete(ctx, arrayPK);
    }

    public void deletePropByWBSID(Context ctx, String wbsID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("02cad323-0c7f-472e-8b3b-38409af1bf4c"), new Object[]{ctx, wbsID});
            invokeServiceBefore(svcCtx);
            _deletePropByWBSID(ctx, wbsID);
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
    protected abstract void _deletePropByWBSID(Context ctx, String wbsID) throws BOSException, EASBizException;

    public void deletePropByTaskExtID(Context ctx, String taskExtID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("78220b4a-6d9d-4ed9-b0cf-f6b57056e719"), new Object[]{ctx, taskExtID});
            invokeServiceBefore(svcCtx);
            _deletePropByTaskExtID(ctx, taskExtID);
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
    protected abstract void _deletePropByTaskExtID(Context ctx, String taskExtID) throws BOSException, EASBizException;

    public Map getProByWBSID(Context ctx, String WBSID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d7e92734-3e3d-4f22-aa81-9b04cb2e7d63"), new Object[]{ctx, WBSID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getProByWBSID(ctx, WBSID);
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
    protected abstract Map _getProByWBSID(Context ctx, String WBSID) throws BOSException, EASBizException;

    public Map getProByTaskExtID(Context ctx, String taskExtID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3c9ea3ec-ec67-4dc5-95e8-28fe13d561c8"), new Object[]{ctx, taskExtID});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getProByTaskExtID(ctx, taskExtID);
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
    protected abstract Map _getProByTaskExtID(Context ctx, String taskExtID) throws BOSException, EASBizException;

    public void updateExeProp(Context ctx, Map extProperties) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("67a85de5-8008-404f-8131-9a09ae036cbe"), new Object[]{ctx, extProperties});
            invokeServiceBefore(svcCtx);
            _updateExeProp(ctx, extProperties);
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
    protected abstract void _updateExeProp(Context ctx, Map extProperties) throws BOSException, EASBizException;

    public void updateCompleProp(Context ctx, Map extProperties) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("eaaee8f1-eda1-4ca8-a8a8-87b5aed55fc4"), new Object[]{ctx, extProperties});
            invokeServiceBefore(svcCtx);
            _updateCompleProp(ctx, extProperties);
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
    protected abstract void _updateCompleProp(Context ctx, Map extProperties) throws BOSException, EASBizException;

    public void saveExeProp(Context ctx, Map exeExtProperties) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("65bee1e6-b630-4276-bfc2-b163f8009bde"), new Object[]{ctx, exeExtProperties});
            invokeServiceBefore(svcCtx);
            _saveExeProp(ctx, exeExtProperties);
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
    protected abstract void _saveExeProp(Context ctx, Map exeExtProperties) throws BOSException, EASBizException;

    public void saveCompileProp(Context ctx, Map exeCompileProperties) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("74a31ea3-ef4e-4921-a674-b538784c78a7"), new Object[]{ctx, exeCompileProperties});
            invokeServiceBefore(svcCtx);
            _saveCompileProp(ctx, exeCompileProperties);
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
    protected abstract void _saveCompileProp(Context ctx, Map exeCompileProperties) throws BOSException, EASBizException;

    public void deletePropByWBSIDs(Context ctx, Set wbsIDs) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d533bca0-b680-478b-abdd-e09c0335efb9"), new Object[]{ctx, wbsIDs});
            invokeServiceBefore(svcCtx);
            _deletePropByWBSIDs(ctx, wbsIDs);
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
    protected abstract void _deletePropByWBSIDs(Context ctx, Set wbsIDs) throws BOSException, EASBizException;

    public void deletePropByTaskExtIDs(Context ctx, Set wbsIDs) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0651e655-f76b-4f30-84a6-eed11520c8e4"), new Object[]{ctx, wbsIDs});
            invokeServiceBefore(svcCtx);
            _deletePropByTaskExtIDs(ctx, wbsIDs);
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
    protected abstract void _deletePropByTaskExtIDs(Context ctx, Set wbsIDs) throws BOSException, EASBizException;

    public void copyFromAdjuestBill(Context ctx, String oldWBSID, String newWBSID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("00e40c31-9272-48be-8a00-59ab9ea9525a"), new Object[]{ctx, oldWBSID, newWBSID});
            invokeServiceBefore(svcCtx);
            _copyFromAdjuestBill(ctx, oldWBSID, newWBSID);
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
    protected abstract void _copyFromAdjuestBill(Context ctx, String oldWBSID, String newWBSID) throws BOSException, EASBizException;

    public CoreBaseCollection getCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCScheduleTaskExtCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCScheduleTaskExtCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCScheduleTaskExtCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}