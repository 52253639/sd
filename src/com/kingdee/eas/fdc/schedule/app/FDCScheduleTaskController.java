package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.framework.app.ScheduleTaskBaseController;
import java.util.Map;
import java.lang.Object;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCScheduleTaskController extends ScheduleTaskBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, FDCScheduleTaskInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, FDCScheduleTaskInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection(Context ctx) throws BOSException, RemoteException;
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public boolean existPreOrDep(Context ctx, String wbsId1, String wbsId2) throws BOSException, EASBizException, RemoteException;
    public Map reCalWBSNumber(Context ctx, Object adjustManager) throws BOSException, EASBizException, RemoteException;
    public Map reCalWBSNumber(Context ctx, String projectId) throws BOSException, EASBizException, RemoteException;
    public Map addTask(Context ctx, FDCScheduleTaskInfo task) throws BOSException, EASBizException, RemoteException;
    public Map moveTask(Context ctx, String[] wbsIds, String operateType, String projectId) throws BOSException, EASBizException, RemoteException;
    public Map deleteTask(Context ctx, String[] wbsIds, String projectId) throws BOSException, EASBizException, RemoteException;
    public List getDependantTask(Context ctx, String TaskId) throws BOSException, EASBizException, RemoteException;
    public List getTaskByAdminDemp(Context ctx, Set projectID, int taskState, String adminDempID) throws BOSException, EASBizException, RemoteException;
    public List getTaskByAdminPerson(Context ctx, Set projectID, int taskState, String adminPersonID) throws BOSException, EASBizException, RemoteException;
    public void importTasks(Context ctx, CoreBaseCollection currentTaskCollection, CoreBaseCollection newTaskCollection, CoreBaseCollection currentWbsCollection, CoreBaseCollection newWbsCollectioin, CoreBaseCollection currentDependTaskCollection, CoreBaseCollection newDependTaskCollection) throws BOSException, EASBizException, RemoteException;
}