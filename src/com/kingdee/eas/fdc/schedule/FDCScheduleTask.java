package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.Object;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.IScheduleTaskBase;
import com.kingdee.bos.framework.*;
import java.util.List;

public class FDCScheduleTask extends ScheduleTaskBase implements IFDCScheduleTask
{
    public FDCScheduleTask()
    {
        super();
        registerInterface(IFDCScheduleTask.class, this);
    }
    public FDCScheduleTask(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCScheduleTask.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("90CCF22B");
    }
    private FDCScheduleTaskController getController() throws BOSException
    {
        return (FDCScheduleTaskController)getBizController();
    }
    /**
     *exists-System defined method
     *@param pk pk
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param filter filter
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param oql oql
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleTaskInfo(getContext(), pk);
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
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleTaskInfo(getContext(), pk, selector);
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
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCScheduleTaskInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(FDCScheduleTaskInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param pk pk
     *@param model model
     */
    public void addnew(IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *update-System defined method
     *@param pk pk
     *@param model model
     */
    public void update(IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updatePartial-System defined method
     *@param model model
     *@param selector selector
     */
    public void updatePartial(FDCScheduleTaskInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updateBigObject-System defined method
     *@param pk pk
     *@param model model
     */
    public void updateBigObject(IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException
    {
        try {
            getController().updateBigObject(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param pk pk
     */
    public void delete(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@return
     */
    public IObjectPK[] getPKList() throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getPKList-System defined method
     *@param filter filter
     *@param sorter sorter
     *@return
     */
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException
    {
        try {
            return getController().getPKList(getContext(), filter, sorter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection() throws BOSException
    {
        try {
            return getController().getFDCScheduleTaskCollection(getContext());
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
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCScheduleTaskCollection(getContext(), view);
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
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCScheduleTaskCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param filter filter
     *@return
     */
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param oql oql
     *@return
     */
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().delete(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *delete-System defined method
     *@param arrayPK arrayPK
     */
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException
    {
        try {
            getController().delete(getContext(), arrayPK);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否存在前后置关系-User defined method
     *@param wbsId1 WBSID
     *@param wbsId2 WBSID
     *@return
     */
    public boolean existPreOrDep(String wbsId1, String wbsId2) throws BOSException, EASBizException
    {
        try {
            return getController().existPreOrDep(getContext(), wbsId1, wbsId2);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *重算WBS编码-User defined method
     *@param adjustManager WBS重算信息
     *@return
     */
    public Map reCalWBSNumber(Object adjustManager) throws BOSException, EASBizException
    {
        try {
            return getController().reCalWBSNumber(getContext(), adjustManager);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *重算WBS编码-User defined method
     *@param projectId 工程项目ID
     *@return
     */
    public Map reCalWBSNumber(String projectId) throws BOSException, EASBizException
    {
        try {
            return getController().reCalWBSNumber(getContext(), projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addTask-User defined method
     *@param task 计划任务
     *@return
     */
    public Map addTask(FDCScheduleTaskInfo task) throws BOSException, EASBizException
    {
        try {
            return getController().addTask(getContext(), task);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *moveTask-User defined method
     *@param wbsIds wbsIds
     *@param operateType 操作类型
     *@param projectId projectId
     *@return
     */
    public Map moveTask(String[] wbsIds, String operateType, String projectId) throws BOSException, EASBizException
    {
        try {
            return getController().moveTask(getContext(), wbsIds, operateType, projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *deleteTask-User defined method
     *@param wbsIds wbsIds
     *@param projectId projectId
     *@return
     */
    public Map deleteTask(String[] wbsIds, String projectId) throws BOSException, EASBizException
    {
        try {
            return getController().deleteTask(getContext(), wbsIds, projectId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *找Task的前置任务信息-User defined method
     *@param TaskId 任务ID
     *@return
     */
    public List getDependantTask(String TaskId) throws BOSException, EASBizException
    {
        try {
            return getController().getDependantTask(getContext(), TaskId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过Project和责任部门找Task-User defined method
     *@param projectID 项目ID
     *@param taskState 任务状态
     *@param adminDempID 责任部门ID
     *@return
     */
    public List getTaskByAdminDemp(Set projectID, int taskState, String adminDempID) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskByAdminDemp(getContext(), projectID, taskState, adminDempID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过Project和责任人找Task-User defined method
     *@param projectID 项目ID
     *@param taskState 任务状态
     *@param adminPersonID 责任人ID
     *@return
     */
    public List getTaskByAdminPerson(Set projectID, int taskState, String adminPersonID) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskByAdminPerson(getContext(), projectID, taskState, adminPersonID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *导入Project-User defined method
     *@param currentTaskCollection 当前所有任务
     *@param newTaskCollection 待导入的任务
     *@param currentWbsCollection 新增wbs
     *@param newWbsCollectioin 新增wbs
     *@param currentDependTaskCollection 现有后置任务
     *@param newDependTaskCollection 待导入前置任务
     */
    public void importTasks(CoreBaseCollection currentTaskCollection, CoreBaseCollection newTaskCollection, CoreBaseCollection currentWbsCollection, CoreBaseCollection newWbsCollectioin, CoreBaseCollection currentDependTaskCollection, CoreBaseCollection newDependTaskCollection) throws BOSException, EASBizException
    {
        try {
            getController().importTasks(getContext(), currentTaskCollection, newTaskCollection, currentWbsCollection, newWbsCollectioin, currentDependTaskCollection, newDependTaskCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}