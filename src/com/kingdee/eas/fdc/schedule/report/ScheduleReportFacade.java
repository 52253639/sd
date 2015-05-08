package com.kingdee.eas.fdc.schedule.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.eas.fdc.schedule.report.app.*;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

public class ScheduleReportFacade extends AbstractBizCtrl implements IScheduleReportFacade
{
    public ScheduleReportFacade()
    {
        super();
        registerInterface(IScheduleReportFacade.class, this);
    }
    public ScheduleReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("19531543");
    }
    private ScheduleReportFacadeController getController() throws BOSException
    {
        return (ScheduleReportFacadeController)getBizController();
    }
    /**
     *获取进度报告数据-User defined method
     *@param orgIds 过滤的组织编码集合
     *@param dates 查询的开始日期及结束日期
     *@param scheduleType （考核节点、主项计划、专项计划）
     *@return
     */
    public Map getScheduleReportData(Map orgIds, Map dates, String scheduleType) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleReportData(getContext(), orgIds, dates, scheduleType);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取进度中专项计划的信息-User defined method
     *@param dates 开始日期和结束日期
     *@param projectID 专项所属工程的ID
     *@return
     */
    public Map getScheduleExpertReportData(Map dates, String projectID) throws BOSException
    {
        try {
            return getController().getScheduleExpertReportData(getContext(), dates, projectID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取所有任务信息-User defined method
     *@param paramMap 参数表
     *@return
     */
    public Map getScheduleTaskInfo(Map paramMap) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskInfo(getContext(), paramMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}