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
import java.util.Map;
import com.kingdee.bos.framework.*;

public class ProjectReportFacade extends AbstractBizCtrl implements IProjectReportFacade
{
    public ProjectReportFacade()
    {
        super();
        registerInterface(IProjectReportFacade.class, this);
    }
    public ProjectReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EEF72511");
    }
    private ProjectReportFacadeController getController() throws BOSException
    {
        return (ProjectReportFacadeController)getBizController();
    }
    /**
     *获取本期及下期任务的功能；传入一个参数Map，Map中需要传入startDate，endDate，project，projectSpecial-User defined method
     *@param param 该方法，需要传递一个param Map；里面需要传递，计划开始时间，计划完成时间，项目，项目专项
     *@return
     */
    public Map getPeriodTask(Map param) throws BOSException, EASBizException
    {
        try {
            return getController().getPeriodTask(getContext(), param);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}