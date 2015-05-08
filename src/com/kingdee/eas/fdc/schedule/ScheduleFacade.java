package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Set;

public class ScheduleFacade extends AbstractBizCtrl implements IScheduleFacade
{
    public ScheduleFacade()
    {
        super();
        registerInterface(IScheduleFacade.class, this);
    }
    public ScheduleFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("32D25F67");
    }
    private ScheduleFacadeController getController() throws BOSException
    {
        return (ScheduleFacadeController)getBizController();
    }
    /**
     *重算父节点完工比率-User defined method
     *@param wbsIds WBSID集合
     */
    public void reCalcParentTaskComplete(Set wbsIds) throws BOSException, EASBizException
    {
        try {
            getController().reCalcParentTaskComplete(getContext(), wbsIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取得本部门及其它部门的进度计划-User defined method
     *@param scheduleId 进度计划ID
     *@return
     */
    public FDCScheduleInfo getOtherDeptSchedule(String scheduleId) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherDeptSchedule(getContext(), scheduleId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据工程量录入反写任务完工工程量-User defined method
     *@param wbsId WBSId
     */
    public void reCalLoadFromTaskLoad(String wbsId) throws BOSException, EASBizException
    {
        try {
            getController().reCalLoadFromTaskLoad(getContext(), wbsId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步任务完工工程量字段-User defined method
     *@param taskId 任务ID
     *@param wbsId WBSID
     *@param param 参数
     */
    public void synchronizeTask(Set taskId, Set wbsId, String param) throws BOSException, EASBizException
    {
        try {
            getController().synchronizeTask(getContext(), taskId, wbsId, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新任务的完工日期-User defined method
     *@param wbsIds WBS编码
     *@param param 工程量填报的类别
     */
    public void upateCompleteDate(Set wbsIds, String param) throws BOSException, EASBizException
    {
        try {
            getController().upateCompleteDate(getContext(), wbsIds, param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *任务导入-User defined method
     *@param scheduleId 要导入的计划id， 新增时导入则可为空
     *@param projectId 工程项目id
     *@param taskTypeId 主项任务导入还是专项任务导入
     *@param parentWbsId 当任务类型是专项时，此值不可为空
     *@param tasks 待导入任务
     *@return
     */
    public FDCScheduleInfo importTasks(String scheduleId, String projectId, String taskTypeId, String parentWbsId, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException
    {
        try {
            return getController().importTasks(getContext(), scheduleId, projectId, taskTypeId, parentWbsId, tasks);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}