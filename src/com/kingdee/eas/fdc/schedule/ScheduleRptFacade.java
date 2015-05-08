package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;

public class ScheduleRptFacade extends AbstractBizCtrl implements IScheduleRptFacade
{
    public ScheduleRptFacade()
    {
        super();
        registerInterface(IScheduleRptFacade.class, this);
    }
    public ScheduleRptFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleRptFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("8DC04843");
    }
    private ScheduleRptFacadeController getController() throws BOSException
    {
        return (ScheduleRptFacadeController)getBizController();
    }
    /**
     *按时间过滤查询任务-User defined method
     *@param param 参数
     *@return
     */
    public RetValue getTimeFilerTasks(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getTimeFilerTasks(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *按任务状态过滤任务-User defined method
     *@param param 参数
     *@return
     */
    public RetValue getFilterStatusTasks(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getFilterStatusTasks(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *按任务状态过滤任务形成汇总信息-User defined method
     *@param param 参数
     *@return
     */
    public RetValue getFilterStatusAllInfos(ParamValue param) throws BOSException, EASBizException
    {
        try {
            return getController().getFilterStatusAllInfos(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}