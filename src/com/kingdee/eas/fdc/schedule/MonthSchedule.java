package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBase;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.framework.IScheduleBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.bos.framework.*;
import java.sql.ResultSet;
import com.kingdee.bos.util.BOSUuid;

public class MonthSchedule extends ScheduleBase implements IMonthSchedule
{
    public MonthSchedule()
    {
        super();
        registerInterface(IMonthSchedule.class, this);
    }
    public MonthSchedule(Context ctx)
    {
        super(ctx);
        registerInterface(IMonthSchedule.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C896D921");
    }
    private MonthScheduleController getController() throws BOSException
    {
        return (MonthScheduleController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MonthScheduleInfo getMonthScheduleInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMonthScheduleInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MonthScheduleInfo getMonthScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMonthScheduleInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public MonthScheduleInfo getMonthScheduleInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMonthScheduleInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MonthScheduleCollection getMonthScheduleCollection() throws BOSException
    {
        try {
            return getController().getMonthScheduleCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public MonthScheduleCollection getMonthScheduleCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMonthScheduleCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public MonthScheduleCollection getMonthScheduleCollection(String oql) throws BOSException
    {
        try {
            return getController().getMonthScheduleCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *fetch任务-User defined method
     *@param startDate 开始时间
     *@param endDate 结束时间
     *@param adminDept 任务归属部门
     *@param adminPerson 任务负责人
     *@return
     */
    public ResultSet fetchTask(String startDate, String endDate, FullOrgUnitInfo adminDept, PersonInfo adminPerson) throws BOSException, EASBizException
    {
        try {
            return getController().fetchTask(getContext(), startDate, endDate, adminDept, adminPerson);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *fetchTask0-User defined method
     *@param start start
     *@param end end
     *@param adminDeptID adminDeptID
     *@param adminPerson adminPerson
     *@param isThisMonth 是否本月
     *@param curProject curProject
     *@return
     */
    public FDCScheduleTaskCollection fetchTask0(Date start, Date end, String adminDeptID, PersonInfo adminPerson, boolean isThisMonth, CurProjectInfo curProject) throws BOSException, EASBizException
    {
        try {
            return getController().fetchTask0(getContext(), start, end, adminDeptID, adminPerson, isThisMonth, curProject);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批 -User defined method
     *@param ids ids
     *@param auditor auditor
     *@return
     */
    public Map audit(Set ids, UserInfo auditor) throws BOSException, EASBizException
    {
        try {
            return getController().audit(getContext(), ids, auditor);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param ids ids
     *@param unAduitor unAduitor
     *@return
     */
    public Map unAudit(Set ids, UserInfo unAduitor) throws BOSException, EASBizException
    {
        try {
            return getController().unAudit(getContext(), ids, unAduitor);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置审批中状态-User defined method
     *@param billId 单据编号
     */
    public void setAudittingStatus(BOSUuid billId) throws BOSException
    {
        try {
            getController().setAudittingStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *设置提交状态-User defined method
     *@param billId 单据编号
     */
    public void setSubmitStatus(BOSUuid billId) throws BOSException
    {
        try {
            getController().setSubmitStatus(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param billID 单据编号
     */
    public void unAudit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().unAudit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *审批 -User defined method
     *@param billID 单据编号
     */
    public void audit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().audit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}