package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.IScheduleTaskBase;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IFDCScheduleTask extends IScheduleTaskBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCScheduleTaskInfo getFDCScheduleTaskInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FDCScheduleTaskInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException, EASBizException;
    public void updatePartial(FDCScheduleTaskInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FDCScheduleTaskInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection() throws BOSException;
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection(EntityViewInfo view) throws BOSException;
    public FDCScheduleTaskCollection getFDCScheduleTaskCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean existPreOrDep(String wbsId1, String wbsId2) throws BOSException, EASBizException;
    public Map reCalWBSNumber(Object adjustManager) throws BOSException, EASBizException;
    public Map reCalWBSNumber(String projectId) throws BOSException, EASBizException;
    public Map addTask(FDCScheduleTaskInfo task) throws BOSException, EASBizException;
    public Map moveTask(String[] wbsIds, String operateType, String projectId) throws BOSException, EASBizException;
    public Map deleteTask(String[] wbsIds, String projectId) throws BOSException, EASBizException;
    public List getDependantTask(String TaskId) throws BOSException, EASBizException;
    public List getTaskByAdminDemp(Set projectID, int taskState, String adminDempID) throws BOSException, EASBizException;
    public List getTaskByAdminPerson(Set projectID, int taskState, String adminPersonID) throws BOSException, EASBizException;
    public void importTasks(CoreBaseCollection currentTaskCollection, CoreBaseCollection newTaskCollection, CoreBaseCollection currentWbsCollection, CoreBaseCollection newWbsCollectioin, CoreBaseCollection currentDependTaskCollection, CoreBaseCollection newDependTaskCollection) throws BOSException, EASBizException;
}