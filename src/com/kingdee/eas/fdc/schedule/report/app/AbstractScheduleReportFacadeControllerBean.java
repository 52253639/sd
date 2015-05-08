package com.kingdee.eas.fdc.schedule.report.app;

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

import java.util.Map;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;



public abstract class AbstractScheduleReportFacadeControllerBean extends AbstractBizControllerBean implements ScheduleReportFacadeController
{
    protected AbstractScheduleReportFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("19531543");
    }

    public Map getScheduleReportData(Context ctx, Map orgIds, Map dates, String scheduleType) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("ee40667b-8a1a-44b4-b4ba-2c1fb839d586"), new Object[]{ctx, orgIds, dates, scheduleType});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getScheduleReportData(ctx, orgIds, dates, scheduleType);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getScheduleReportData(Context ctx, Map orgIds, Map dates, String scheduleType) throws BOSException, EASBizException;

    public Map getScheduleExpertReportData(Context ctx, Map dates, String projectID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a97f0561-1ab0-4a3d-a07a-30fd6772c3aa"), new Object[]{ctx, dates, projectID});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getScheduleExpertReportData(ctx, dates, projectID);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getScheduleExpertReportData(Context ctx, Map dates, String projectID) throws BOSException;

    public Map getScheduleTaskInfo(Context ctx, Map paramMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9fde7eea-7f47-49bc-862f-0b1c1a0f3f68"), new Object[]{ctx, paramMap});
            invokeServiceBefore(svcCtx);
            if(!svcCtx.invokeBreak()) {
            Map retValue = (Map)_getScheduleTaskInfo(ctx, paramMap);
            svcCtx.setMethodReturnValue(retValue);
            }
            invokeServiceAfter(svcCtx);
            return (Map)svcCtx.getMethodReturnValue();
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getScheduleTaskInfo(Context ctx, Map paramMap) throws BOSException, EASBizException;

}