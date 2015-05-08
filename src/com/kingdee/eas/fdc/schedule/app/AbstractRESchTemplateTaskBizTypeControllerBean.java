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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;



public abstract class AbstractRESchTemplateTaskBizTypeControllerBean extends CoreBaseControllerBean implements RESchTemplateTaskBizTypeController
{
    protected AbstractRESchTemplateTaskBizTypeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("7184C55F");
    }

    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f9cf8ba2-9bee-4533-ac33-d3705bb02879"), new Object[]{ctx, pk});
            invokeServiceBefore(svcCtx);
            RESchTemplateTaskBizTypeInfo retValue = (RESchTemplateTaskBizTypeInfo)_getValue(ctx, pk);
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

    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a158756a-d352-4d62-8283-0044be9773c9"), new Object[]{ctx, pk, selector});
            invokeServiceBefore(svcCtx);
            RESchTemplateTaskBizTypeInfo retValue = (RESchTemplateTaskBizTypeInfo)_getValue(ctx, pk, selector);
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

    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(Context ctx, String oql) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d844967b-f477-4264-96d4-2b25873964a7"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            RESchTemplateTaskBizTypeInfo retValue = (RESchTemplateTaskBizTypeInfo)_getValue(ctx, oql);
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

    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f22ad9d7-54af-4166-848d-f42809c47656"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            RESchTemplateTaskBizTypeCollection retValue = (RESchTemplateTaskBizTypeCollection)_getCollection(ctx, svcCtx);
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

    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection(Context ctx, EntityViewInfo view) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2ee43a3d-5437-42c7-aa0d-2eed9884ca5e"), new Object[]{ctx, view});
            invokeServiceBefore(svcCtx);
            RESchTemplateTaskBizTypeCollection retValue = (RESchTemplateTaskBizTypeCollection)_getCollection(ctx, svcCtx, view);
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

    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection(Context ctx, String oql) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("111b8868-e835-429f-a2ab-2c4fdd734f8a"), new Object[]{ctx, oql});
            invokeServiceBefore(svcCtx);
            RESchTemplateTaskBizTypeCollection retValue = (RESchTemplateTaskBizTypeCollection)_getCollection(ctx, svcCtx, oql);
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
    	return (CoreBaseCollection)(getRESchTemplateTaskBizTypeCollection(ctx).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, EntityViewInfo view) throws BOSException
    {
    	return (CoreBaseCollection)(getRESchTemplateTaskBizTypeCollection(ctx, view).cast(CoreBaseCollection.class));
    }
    public CoreBaseCollection getCollection (Context ctx, String oql) throws BOSException
    {
    	return (CoreBaseCollection)(getRESchTemplateTaskBizTypeCollection(ctx, oql).cast(CoreBaseCollection.class));
    }
}