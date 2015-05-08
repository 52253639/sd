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
import com.kingdee.eas.fdc.schedule.ProjectWeekReportCollection;
import com.kingdee.eas.fdc.schedule.ProjectWeekReportInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.schedule.OpReportBaseCollection;



public abstract class AbstractProjectWeekReportControllerBean extends OpReportBaseControllerBean implements ProjectWeekReportController
{
    protected AbstractProjectWeekReportControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("23D2312B");
    }

    public ProjectWeekReportInfo getProjectWeekReportInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9b841c25-989b-4164-a11b-a3d644cf6eb0"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            ProjectWeekReportInfo retValue = (ProjectWeekReportInfo)_getValue(ctx, pk);
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

    public ProjectWeekReportInfo getProjectWeekReportInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("5923acbd-94e3-4816-9406-e76f141c26a4"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            ProjectWeekReportInfo retValue = (ProjectWeekReportInfo)_getValue(ctx, pk, selector);
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

    public ProjectWeekReportInfo getProjectWeekReportInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("6aecc1e0-a001-4e0e-9c18-e754f98950a6"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectWeekReportInfo retValue = (ProjectWeekReportInfo)_getValue(ctx, oql);
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

    public ProjectWeekReportCollection getProjectWeekReportCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("837c6fe5-a65e-40c2-a7b2-d3c62ce46e4a"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            ProjectWeekReportCollection retValue = (ProjectWeekReportCollection)_getCollection(ctx, svcCtx);
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

    public ProjectWeekReportCollection getProjectWeekReportCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("91301afa-bbc9-4f70-b87c-fc84204fd2cf"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            ProjectWeekReportCollection retValue = (ProjectWeekReportCollection)_getCollection(ctx, svcCtx, view);
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

    public ProjectWeekReportCollection getProjectWeekReportCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7e9ac680-c8b2-4be7-89e4-2998358f5fb5"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectWeekReportCollection retValue = (ProjectWeekReportCollection)_getCollection(ctx, svcCtx, oql);
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

    public OpReportBaseCollection getOpReportBaseCollection (Context ctx) throws BOSException
    {
    	return (OpReportBaseCollection)(getProjectWeekReportCollection(ctx).cast(OpReportBaseCollection.class));
    }
    public OpReportBaseCollection getOpReportBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (OpReportBaseCollection)(getProjectWeekReportCollection(ctx, view).cast(OpReportBaseCollection.class));
    }
    public OpReportBaseCollection getOpReportBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (OpReportBaseCollection)(getProjectWeekReportCollection(ctx, oql).cast(OpReportBaseCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getProjectWeekReportCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getProjectWeekReportCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getProjectWeekReportCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getProjectWeekReportCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getProjectWeekReportCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getProjectWeekReportCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getProjectWeekReportCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getProjectWeekReportCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getProjectWeekReportCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectWeekReportCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectWeekReportCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectWeekReportCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}