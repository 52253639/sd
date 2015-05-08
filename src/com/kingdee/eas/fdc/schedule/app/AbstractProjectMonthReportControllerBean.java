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
import com.kingdee.eas.fdc.schedule.ProjectMonthReportInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.schedule.OpReportBaseCollection;



public abstract class AbstractProjectMonthReportControllerBean extends OpReportBaseControllerBean implements ProjectMonthReportController
{
    protected AbstractProjectMonthReportControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("58784951");
    }

    public ProjectMonthReportInfo getProjectMonthReportInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0d66b669-6a4b-4927-ac09-479fdbed0390"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectMonthReportInfo retValue = (ProjectMonthReportInfo)_getValue(ctx, oql);
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

    public ProjectMonthReportInfo getProjectMonthReportInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("88b1cfc6-88a9-465f-8a51-c25b6260c8d4"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            ProjectMonthReportInfo retValue = (ProjectMonthReportInfo)_getValue(ctx, pk, selector);
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

    public ProjectMonthReportInfo getProjectMonthReportInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ed271991-e265-45f6-af9b-4224bfc4edcc"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            ProjectMonthReportInfo retValue = (ProjectMonthReportInfo)_getValue(ctx, pk);
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

    public ProjectMonthReportCollection getProjectMonthReportCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("65ec6ed1-7a26-4a2c-b459-40e1ba4a6be8"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            ProjectMonthReportCollection retValue = (ProjectMonthReportCollection)_getCollection(ctx, svcCtx, oql);
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

    public ProjectMonthReportCollection getProjectMonthReportCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0e91d2de-9132-44fb-9e61-664d127b5ff4"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            ProjectMonthReportCollection retValue = (ProjectMonthReportCollection)_getCollection(ctx, svcCtx, view);
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

    public ProjectMonthReportCollection getProjectMonthReportCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7f2c9a3b-70d7-4465-90cc-12da833e97ef"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            ProjectMonthReportCollection retValue = (ProjectMonthReportCollection)_getCollection(ctx, svcCtx);
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

    public OpReportBaseCollection getOpReportBaseCollection (Context ctx) throws BOSException
    {
    	return (OpReportBaseCollection)(getProjectMonthReportCollection(ctx).cast(OpReportBaseCollection.class));
    }
    public OpReportBaseCollection getOpReportBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (OpReportBaseCollection)(getProjectMonthReportCollection(ctx, view).cast(OpReportBaseCollection.class));
    }
    public OpReportBaseCollection getOpReportBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (OpReportBaseCollection)(getProjectMonthReportCollection(ctx, oql).cast(OpReportBaseCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx) throws BOSException
    {
    	return (FDCBillCollection)(getProjectMonthReportCollection(ctx).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (FDCBillCollection)(getProjectMonthReportCollection(ctx, view).cast(FDCBillCollection.class));
    }
    public FDCBillCollection getFDCBillCollection (Context ctx, String oql) throws BOSException
    {
    	return (FDCBillCollection)(getProjectMonthReportCollection(ctx, oql).cast(FDCBillCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBillBaseCollection)(getProjectMonthReportCollection(ctx).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBillBaseCollection)(getProjectMonthReportCollection(ctx, view).cast(CoreBillBaseCollection.class));
    }
    public CoreBillBaseCollection getCoreBillBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBillBaseCollection)(getProjectMonthReportCollection(ctx, oql).cast(CoreBillBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx) throws BOSException
    {
    	return (ObjectBaseCollection)(getProjectMonthReportCollection(ctx).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (ObjectBaseCollection)(getProjectMonthReportCollection(ctx, view).cast(ObjectBaseCollection.class));
    }
    public ObjectBaseCollection getObjectBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (ObjectBaseCollection)(getProjectMonthReportCollection(ctx, oql).cast(ObjectBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectMonthReportCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectMonthReportCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCoreBaseCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getProjectMonthReportCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}