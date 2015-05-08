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
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import java.util.Set;



public abstract class AbstractScheduleFacadeControllerBean extends AbstractBizControllerBean implements ScheduleFacadeController
{
    protected AbstractScheduleFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("32D25F67");
    }

    public void reCalcParentTaskComplete(Context ctx, Set wbsIds) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("1852de24-d5f4-431a-bb14-0469d422df74"), new Object[]{ctx, wbsIds});
            invokeServiceBefore(svcCtx);
            _reCalcParentTaskComplete(ctx, wbsIds);
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
    protected abstract void _reCalcParentTaskComplete(Context ctx, Set wbsIds) throws BOSException, EASBizException;

    public FDCScheduleInfo getOtherDeptSchedule(Context ctx, String scheduleId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("cd737f7d-2ff4-4679-9bcb-bb8e61c81a3f"), new Object[]{ctx, scheduleId});
            invokeServiceBefore(svcCtx);
            FDCScheduleInfo retValue = (FDCScheduleInfo)_getOtherDeptSchedule(ctx, scheduleId);
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
    protected abstract IObjectValue _getOtherDeptSchedule(Context ctx, String scheduleId) throws BOSException, EASBizException;

    public void reCalLoadFromTaskLoad(Context ctx, String wbsId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d55f2d67-0674-4d5e-9833-2fd5ef2a832c"), new Object[]{ctx, wbsId});
            invokeServiceBefore(svcCtx);
            _reCalLoadFromTaskLoad(ctx, wbsId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _reCalLoadFromTaskLoad(Context ctx, String wbsId) throws BOSException, EASBizException;

    public void synchronizeTask(Context ctx, Set taskId, Set wbsId, String param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("f26ecec7-aaa7-414d-823b-8fdb141def57"), new Object[]{ctx, taskId, wbsId, param});
            invokeServiceBefore(svcCtx);
            _synchronizeTask(ctx, taskId, wbsId, param);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _synchronizeTask(Context ctx, Set taskId, Set wbsId, String param) throws BOSException, EASBizException;

    public void upateCompleteDate(Context ctx, Set wbsIds, String param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ca80cf30-a877-4d72-8fce-cf961425cba3"), new Object[]{ctx, wbsIds, param});
            invokeServiceBefore(svcCtx);
            _upateCompleteDate(ctx, wbsIds, param);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _upateCompleteDate(Context ctx, Set wbsIds, String param) throws BOSException, EASBizException;

    public FDCScheduleInfo importTasks(Context ctx, String scheduleId, String projectId, String taskTypeId, String parentWbsId, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8558c6db-d607-47d1-824d-b58cd05ded01"), new Object[]{ctx, scheduleId, projectId, taskTypeId, parentWbsId, tasks});
            invokeServiceBefore(svcCtx);
            FDCScheduleInfo retValue = (FDCScheduleInfo)_importTasks(ctx, scheduleId, projectId, taskTypeId, parentWbsId, tasks);
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
    protected abstract IObjectValue _importTasks(Context ctx, String scheduleId, String projectId, String taskTypeId, String parentWbsId, IObjectCollection tasks) throws BOSException, EASBizException;

}