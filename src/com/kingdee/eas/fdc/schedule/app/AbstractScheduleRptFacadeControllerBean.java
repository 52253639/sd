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

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;



public abstract class AbstractScheduleRptFacadeControllerBean extends AbstractBizControllerBean implements ScheduleRptFacadeController
{
    protected AbstractScheduleRptFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("8DC04843");
    }

    public RetValue getTimeFilerTasks(Context ctx, ParamValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2aae8817-c48d-4bc8-b0a6-a45bf6657cba"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getTimeFilerTasks(ctx, param);
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
    protected abstract RetValue _getTimeFilerTasks(Context ctx, ParamValue param) throws BOSException, EASBizException;

    public RetValue getFilterStatusTasks(Context ctx, ParamValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("aa8c926f-8143-4064-99f1-7d522a795273"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getFilterStatusTasks(ctx, param);
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
    protected abstract RetValue _getFilterStatusTasks(Context ctx, ParamValue param) throws BOSException, EASBizException;

    public RetValue getFilterStatusAllInfos(Context ctx, ParamValue param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bb904a97-a8e9-4057-b946-2c6f240b489e"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            RetValue retValue = (RetValue)_getFilterStatusAllInfos(ctx, param);
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
    protected abstract RetValue _getFilterStatusAllInfos(Context ctx, ParamValue param) throws BOSException, EASBizException;

}