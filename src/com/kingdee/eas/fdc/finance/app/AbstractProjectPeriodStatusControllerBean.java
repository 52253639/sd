package com.kingdee.eas.fdc.finance.app;

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
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusCollection;
import java.util.Date;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import java.util.List;



public abstract class AbstractProjectPeriodStatusControllerBean extends CoreBaseControllerBean implements ProjectPeriodStatusController
{
    protected AbstractProjectPeriodStatusControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("30DDC95D");
    }

    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2a14eb0-0117-1000-e000-0031c0a81297"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            ProjectPeriodStatusInfo retValue = (ProjectPeriodStatusInfo)_getValue(ctx, pk);
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

    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2a14eb0-0117-1000-e000-0032c0a81297"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            ProjectPeriodStatusInfo retValue = (ProjectPeriodStatusInfo)_getValue(ctx, pk, selector);
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

    public ProjectPeriodStatusInfo getProjectPeriodStatusInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2a14eb0-0117-1000-e000-0033c0a81297"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectPeriodStatusInfo retValue = (ProjectPeriodStatusInfo)_getValue(ctx, oql);
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

    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2a14eb0-0117-1000-e000-0034c0a81297"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            ProjectPeriodStatusCollection retValue = (ProjectPeriodStatusCollection)_getCollection(ctx, svcCtx, view);
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

    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2a14eb0-0117-1000-e000-0035c0a81297"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            ProjectPeriodStatusCollection retValue = (ProjectPeriodStatusCollection)_getCollection(ctx, svcCtx);
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

    public ProjectPeriodStatusCollection getProjectPeriodStatusCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2a14eb0-0117-1000-e000-0036c0a81297"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectPeriodStatusCollection retValue = (ProjectPeriodStatusCollection)_getCollection(ctx, svcCtx, oql);
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

    public Map closeInit(Context ctx, List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d3fd5eef-0117-1000-e000-0009c0a81297"), new Object[]{ctx, projectIds, orgUnitId, new Boolean(isCompany)});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_closeInit(ctx, projectIds, orgUnitId, isCompany);
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
    protected abstract Map _closeInit(Context ctx, List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException;

    public void closeProject(Context ctx, List projectIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d3fd5eef-0117-1000-e000-000bc0a81297"), new Object[]{ctx, projectIds});
            invokeServiceBefore(svcCtx);
            _closeProject(ctx, projectIds);
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
    protected abstract void _closeProject(Context ctx, List projectIds) throws BOSException, EASBizException;

    public Map closeAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d3fd5eef-0117-1000-e000-000cc0a81297"), new Object[]{ctx, orgUnit, new Boolean(isCompany)});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_closeAll(ctx, orgUnit, isCompany);
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
    protected abstract Map _closeAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException;

    public Map closeUnInit(Context ctx, List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d50158e3-0117-1000-e000-0008c0a81297"), new Object[]{ctx, projectIds, orgUnitId, new Boolean(isCompany)});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_closeUnInit(ctx, projectIds, orgUnitId, isCompany);
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
    protected abstract Map _closeUnInit(Context ctx, List projectIds, String orgUnitId, boolean isCompany) throws BOSException, EASBizException;

    public void closeUnProject(Context ctx, List projectIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d50158e3-0117-1000-e000-0009c0a81297"), new Object[]{ctx, projectIds});
            invokeServiceBefore(svcCtx);
            _closeUnProject(ctx, projectIds);
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
    protected abstract void _closeUnProject(Context ctx, List projectIds) throws BOSException, EASBizException;

    public Map closeUnAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d50158e3-0117-1000-e000-000ac0a81297"), new Object[]{ctx, orgUnit, new Boolean(isCompany)});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_closeUnAll(ctx, orgUnit, isCompany);
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
    protected abstract Map _closeUnAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException;

    public PeriodInfo fetchPeriod(Context ctx, String projectId, Date bookedDate, boolean isCost) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d50158e3-0117-1000-e000-0247c0a81297"), new Object[]{ctx, projectId, bookedDate, new Boolean(isCost)});
            invokeServiceBefore(svcCtx);
            PeriodInfo retValue = (PeriodInfo)_fetchPeriod(ctx, projectId, bookedDate, isCost);
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
    protected abstract IObjectValue _fetchPeriod(Context ctx, String projectId, Date bookedDate, boolean isCost) throws BOSException, EASBizException;

    public Map fetchInitData(Context ctx, String curOrgId, boolean isCompany) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a2a8cf9b-0118-1000-e000-0117c0a8129f"), new Object[]{ctx, curOrgId, new Boolean(isCompany)});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_fetchInitData(ctx, curOrgId, isCompany);
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
    protected abstract Map _fetchInitData(Context ctx, String curOrgId, boolean isCompany) throws BOSException, EASBizException;

    public void paramCheck(Context ctx, String comId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ceee5baa-0118-1000-e000-04c6c0a8129f"), new Object[]{ctx, comId});
            invokeServiceBefore(svcCtx);
            _paramCheck(ctx, comId);
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
    protected abstract void _paramCheck(Context ctx, String comId) throws BOSException, EASBizException;

    public CoreBaseCollection getCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectPeriodStatusCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectPeriodStatusCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectPeriodStatusCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}