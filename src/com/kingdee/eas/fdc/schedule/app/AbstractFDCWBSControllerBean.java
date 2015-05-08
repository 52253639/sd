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
import java.lang.Object;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataCollection;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataControllerBean;
import com.kingdee.eas.framework.DataBaseCollection;



public abstract class AbstractFDCWBSControllerBean extends FDCTreeBaseDataControllerBean implements FDCWBSController
{
    protected AbstractFDCWBSControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("786DC4B9");
    }

    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d04f7087-5f04-4c20-8c12-aa4159b15c28"), new Object[]{ctx, pk});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2f979bd8-3fa4-430a-8b7e-80c8f0248d31"), new Object[]{ctx, filter});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("320261a6-df24-488c-8e2a-b2fb72955d3a"), new Object[]{ctx, oql});
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

    public FDCWBSInfo getFDCWBSInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9e01314e-f7bc-4e07-b648-bc20708486d3"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            FDCWBSInfo retValue = (FDCWBSInfo)_getValue(ctx, pk);
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

    public FDCWBSInfo getFDCWBSInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8c4ce251-e0a6-434f-a4e6-4b4f404b8968"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            FDCWBSInfo retValue = (FDCWBSInfo)_getValue(ctx, pk, selector);
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

    public FDCWBSInfo getFDCWBSInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d08ac0ee-22d6-4e1c-b066-16931ffb7d73"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCWBSInfo retValue = (FDCWBSInfo)_getValue(ctx, oql);
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

    public IObjectPK addnew(Context ctx, FDCWBSInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1a0d5949-6bda-4d07-bb2f-f2e08e2dbd0a"), new Object[]{ctx, model});
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

    public void addnew(Context ctx, IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c0e39ff8-ad7e-4acc-8135-6f628fc36f84"), new Object[]{ctx, pk, model});
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

    public void update(Context ctx, IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1b238984-6013-4a05-b9d1-f97cc065ab4a"), new Object[]{ctx, pk, model});
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

    public void updatePartial(Context ctx, FDCWBSInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("afdc1bd7-f0c0-4f35-bf2b-8cb45afeb876"), new Object[]{ctx, model, selector});
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

    public void updateBigObject(Context ctx, IObjectPK pk, FDCWBSInfo model) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5c167fac-f251-4f8f-bccb-4eef69b05a73"), new Object[]{ctx, pk, model});
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
        super._updateBigObject(ctx, pk, model);
    }

    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6d9a6c2c-7f9d-491d-9ab9-381a1c33ad93"), new Object[]{ctx, pk});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b722da1b-0ec1-4e0e-8634-09e077f4f34c"), new Object[]{ctx});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("44dca2bb-7845-4503-be3e-bbf90911442c"), new Object[]{ctx, oql});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c4422d20-e4a3-42ab-8bb8-ffdc45f640d9"), new Object[]{ctx, filter, sorter});
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

    public FDCWBSCollection getFDCWBSCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7be5b565-84a1-480a-bb88-08d86514544c"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            FDCWBSCollection retValue = (FDCWBSCollection)_getCollection(ctx, svcCtx);
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

    public FDCWBSCollection getFDCWBSCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8a289ef6-aae9-4f95-8b5c-80720da6bd1d"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            FDCWBSCollection retValue = (FDCWBSCollection)_getCollection(ctx, svcCtx, view);
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

    public FDCWBSCollection getFDCWBSCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6a8f6337-abbf-4bb5-af5f-74fc3c4487b0"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            FDCWBSCollection retValue = (FDCWBSCollection)_getCollection(ctx, svcCtx, oql);
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("288e3a1f-81e5-41c7-a095-028cd82ab33d"), new Object[]{ctx, filter});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("33a2065d-d26a-4b19-8993-cea2e71d9f26"), new Object[]{ctx, oql});
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
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6df64399-8c9f-4e25-bfac-622c7d3165fe"), new Object[]{ctx, arrayPK});
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

    public void importWBSTemplate(Context ctx, Map param) throws BOSException, ScheduleException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7909698c-d8be-48b5-aecb-021e97074bf0"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            _importWBSTemplate(ctx, param);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (ScheduleException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } catch (EASBizException ex1) {
            this.setRollbackOnly();
            throw ex1;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _importWBSTemplate(Context ctx, Map param) throws BOSException, ScheduleException, EASBizException;

    public void batChangeTaskPro(Context ctx, Set wbsIDs, String taskProID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("322eec96-ccdc-4a3f-964d-758bd55a3df9"), new Object[]{ctx, wbsIDs, taskProID});
            invokeServiceBefore(svcCtx);
            _batChangeTaskPro(ctx, wbsIDs, taskProID);
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
    protected abstract void _batChangeTaskPro(Context ctx, Set wbsIDs, String taskProID) throws BOSException, EASBizException;

    public void batChangeAdminDept(Context ctx, Set wbsIDs, String adminDeptID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4beeca32-132b-4e91-80cd-67edade016c5"), new Object[]{ctx, wbsIDs, adminDeptID});
            invokeServiceBefore(svcCtx);
            _batChangeAdminDept(ctx, wbsIDs, adminDeptID);
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
    protected abstract void _batChangeAdminDept(Context ctx, Set wbsIDs, String adminDeptID) throws BOSException, EASBizException;

    public void batChangeAdminPerson(Context ctx, Set wbsIds, String adminPersonID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("329728ca-4284-4e7d-bb84-232b69a3c893"), new Object[]{ctx, wbsIds, adminPersonID});
            invokeServiceBefore(svcCtx);
            _batChangeAdminPerson(ctx, wbsIds, adminPersonID);
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
    protected abstract void _batChangeAdminPerson(Context ctx, Set wbsIds, String adminPersonID) throws BOSException, EASBizException;

    public void batChangeRespDept(Context ctx, Set wbsIds, String respDeptId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ce28133c-63b1-476c-a732-2ad69b06fd12"), new Object[]{ctx, wbsIds, respDeptId});
            invokeServiceBefore(svcCtx);
            _batChangeRespDept(ctx, wbsIds, respDeptId);
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
    protected abstract void _batChangeRespDept(Context ctx, Set wbsIds, String respDeptId) throws BOSException, EASBizException;

    public Map getTemplateFromFDCWBS(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c2769648-0f54-430e-a4c9-dda928a7b0a8"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getTemplateFromFDCWBS(ctx, prjId);
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
    protected abstract Map _getTemplateFromFDCWBS(Context ctx, String prjId) throws BOSException, EASBizException;

    public Map getTaskWBSByProjectId(Context ctx, String projectId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("86d318cb-eef2-4967-9db6-1ad53019de29"), new Object[]{ctx, projectId});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getTaskWBSByProjectId(ctx, projectId);
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
    protected abstract Map _getTaskWBSByProjectId(Context ctx, String projectId) throws BOSException, EASBizException;

    public void reCalculateNumber(Context ctx, Object wbsAdjustManager) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("31fa56b0-9265-442b-b42d-3b437053f615"), new Object[]{ctx, wbsAdjustManager});
            invokeServiceBefore(svcCtx);
            _reCalculateNumber(ctx, wbsAdjustManager);
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
    protected abstract void _reCalculateNumber(Context ctx, Object wbsAdjustManager) throws BOSException, EASBizException;

    public void handleCancleCancle(Context ctx, Map paramMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4492cdf4-22ef-4925-ad8b-0e285db6ef2c"), new Object[]{ctx, paramMap});
            invokeServiceBefore(svcCtx);
            _handleCancleCancle(ctx, paramMap);
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
    protected abstract void _handleCancleCancle(Context ctx, Map paramMap) throws BOSException, EASBizException;

    public Map saveOrderWBS(Context ctx, FDCWBSTree tree, boolean isRetMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("4555cc62-36f3-4872-9a83-f2fb1975027a"), new Object[]{ctx, tree, new Boolean(isRetMap)});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_saveOrderWBS(ctx, tree, isRetMap);
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
    protected abstract Map _saveOrderWBS(Context ctx, FDCWBSTree tree, boolean isRetMap) throws BOSException, EASBizException;

    public FDCTreeBaseDataCollection getFDCTreeBaseDataCollection (Context ctx) throws BOSException
    {
    	return (FDCTreeBaseDataCollection)(getFDCWBSCollection(ctx).cast(FDCTreeBaseDataCollection.class));
    }
    public FDCTreeBaseDataCollection getFDCTreeBaseDataCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCTreeBaseDataCollection)(getFDCWBSCollection(ctx, view).cast(FDCTreeBaseDataCollection.class));
    }
    public FDCTreeBaseDataCollection getFDCTreeBaseDataCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCTreeBaseDataCollection)(getFDCWBSCollection(ctx, oql).cast(FDCTreeBaseDataCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx) throws BOSException
    {
    	return (TreeBaseCollection)(getFDCWBSCollection(ctx).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (TreeBaseCollection)(getFDCWBSCollection(ctx, view).cast(TreeBaseCollection.class));
    }
    public TreeBaseCollection getTreeBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (TreeBaseCollection)(getFDCWBSCollection(ctx, oql).cast(TreeBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx) throws BOSException
    {
    	return (DataBaseCollection)(getFDCWBSCollection(ctx).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (DataBaseCollection)(getFDCWBSCollection(ctx, view).cast(DataBaseCollection.class));
    }
    public DataBaseCollection getDataBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (DataBaseCollection)(getFDCWBSCollection(ctx, oql).cast(DataBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCWBSCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCWBSCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getFDCWBSCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCWBSCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCWBSCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getFDCWBSCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}