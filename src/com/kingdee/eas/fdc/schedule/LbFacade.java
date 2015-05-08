package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;

public class LbFacade extends AbstractBizCtrl implements ILbFacade
{
    public LbFacade()
    {
        super();
        registerInterface(ILbFacade.class, this);
    }
    public LbFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ILbFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BC0C2F86");
    }
    private LbFacadeController getController() throws BOSException
    {
        return (LbFacadeController)getBizController();
    }
    /**
     *得到成果类型最大编码-User defined method
     *@return
     */
    public String getBiggestNumber() throws BOSException
    {
        try {
            return getController().getBiggestNumber(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *成果类别是否关联成果成果专业-User defined method
     *@param fprofession fprofession
     *@return
     */
    public boolean isQuoteProfeseeion(String fprofession) throws BOSException
    {
        try {
            return getController().isQuoteProfeseeion(getContext(), fprofession);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *成果阶段是否关联到成果类别-User defined method
     *@param fstage fstage
     *@return
     */
    public boolean isQuoteStage(String fstage) throws BOSException
    {
        try {
            return getController().isQuoteStage(getContext(), fstage);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *选中专项是否存在已编制的专项计划-User defined method
     *@param strFachievementTypeId strFachievementTypeId
     *@return
     */
    public boolean isQuoteScheduleTask(String strFachievementTypeId) throws BOSException
    {
        try {
            return getController().isQuoteScheduleTask(getContext(), strFachievementTypeId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到成果专业最大编码-User defined method
     *@return
     */
    public String getBiggestNumber2() throws BOSException
    {
        try {
            return getController().getBiggestNumber2(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到成果阶段最大编码-User defined method
     *@return
     */
    public String getBiggestNumber3() throws BOSException
    {
        try {
            return getController().getBiggestNumber3(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到列名即阶段-User defined method
     *@return
     */
    public List getAchievementManagerHeader1() throws BOSException
    {
        try {
            return getController().getAchievementManagerHeader1(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到行名即专业-User defined method
     *@return
     */
    public List getAchievementManagerHeader2() throws BOSException
    {
        try {
            return getController().getAchievementManagerHeader2(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到类别-User defined method
     *@param curproId 利用工程项目的ID过滤右边的成果
     *@return
     */
    public List getAchievementManagerData(String curproId) throws BOSException
    {
        try {
            return getController().getAchievementManagerData(getContext(), curproId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *得到成果类别上关于该任务带出的信息-User defined method
     *@param taskTypeName taskTypeName
     *@return
     */
    public AchievementTypeInfo getAchievementManagerInfo(String taskTypeName) throws BOSException
    {
        try {
            return getController().getAchievementManagerInfo(getContext(), taskTypeName);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *根据任务id判断是主项任务还是专项任务-User defined method
     *@param taskid taskid
     *@return
     */
    public FDCScheduleInfo isMain(String taskid) throws BOSException
    {
        try {
            return getController().isMain(getContext(), taskid);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}