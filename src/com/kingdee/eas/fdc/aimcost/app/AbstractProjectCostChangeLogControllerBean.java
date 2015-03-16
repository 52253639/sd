package com.kingdee.eas.fdc.aimcost.app;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogCollection;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;



public abstract class AbstractProjectCostChangeLogControllerBean extends CoreBaseControllerBean implements ProjectCostChangeLogController
{
    protected AbstractProjectCostChangeLogControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("7839CD65");
    }

    public boolean insertLog(Context ctx, Set prjSet) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2caa37a3-41ee-4da1-8b88-a7bd4c7597de"), new Object[]{ctx, prjSet});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_insertLog(ctx, prjSet);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _insertLog(Context ctx, Set prjSet) throws BOSException;

    public boolean updateLog(Context ctx, Set prjSet) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8f0a9cc8-3f71-4440-8572-711234bca8da"), new Object[]{ctx, prjSet});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_updateLog(ctx, prjSet);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _updateLog(Context ctx, Set prjSet) throws BOSException;

    public boolean deleteLog(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d425d978-1a80-4606-9db2-18009aeede98"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            boolean retValue = (boolean)_deleteLog(ctx);
            svcCtx.setMethodReturnValue(new Boolean(retValue));
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract boolean _deleteLog(Context ctx) throws BOSException;

    public Set getChangePrjs(Context ctx, Set prjSet) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("130026ac-95a8-455e-98e7-1b18fc1b86d7"), new Object[]{ctx, prjSet});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getChangePrjs(ctx, prjSet);
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
    protected abstract Set _getChangePrjs(Context ctx, Set prjSet) throws BOSException;

    public Set getAllChangePrjs(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("01c6842d-8a44-4887-830a-da33e2d5786c"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            Set retValue = (Set)_getAllChangePrjs(ctx);
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
    protected abstract Set _getAllChangePrjs(Context ctx) throws BOSException;

    public ProjectCostChangeLogCollection getProjectCostChangeLogCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2f4df405-40b8-4cc1-b933-6fdc3ff2bd4e"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            ProjectCostChangeLogCollection retValue = (ProjectCostChangeLogCollection)_getCollection(ctx, svcCtx);
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

    public ProjectCostChangeLogCollection getProjectCostChangeLogCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("35f5f19d-8c00-4a3a-86e7-afcf12b31ca5"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            ProjectCostChangeLogCollection retValue = (ProjectCostChangeLogCollection)_getCollection(ctx, svcCtx, view);
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

    public ProjectCostChangeLogCollection getProjectCostChangeLogCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ace85fbf-2a8d-4926-928c-555eeaf9cca9"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectCostChangeLogCollection retValue = (ProjectCostChangeLogCollection)_getCollection(ctx, svcCtx, oql);
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

    public CoreBaseCollection getCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectCostChangeLogCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectCostChangeLogCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectCostChangeLogCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}