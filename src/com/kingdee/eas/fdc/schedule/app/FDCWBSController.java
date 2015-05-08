package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataController;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.Object;
import java.util.Map;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCWBSController extends FDCTreeBaseDataController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCWBSInfo getFDCWBSInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCWBSInfo getFDCWBSInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCWBSInfo getFDCWBSInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, FDCWBSInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, FDCWBSInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, FDCWBSInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, FDCWBSInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public FDCWBSCollection getFDCWBSCollection(Context ctx) throws BOSException, RemoteException;
    public FDCWBSCollection getFDCWBSCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCWBSCollection getFDCWBSCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public void importWBSTemplate(Context ctx, Map param) throws BOSException, ScheduleException, EASBizException, RemoteException;
    public void batChangeTaskPro(Context ctx, Set wbsIDs, String taskProID) throws BOSException, EASBizException, RemoteException;
    public void batChangeAdminDept(Context ctx, Set wbsIDs, String adminDeptID) throws BOSException, EASBizException, RemoteException;
    public void batChangeAdminPerson(Context ctx, Set wbsIds, String adminPersonID) throws BOSException, EASBizException, RemoteException;
    public void batChangeRespDept(Context ctx, Set wbsIds, String respDeptId) throws BOSException, EASBizException, RemoteException;
    public Map getTemplateFromFDCWBS(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public Map getTaskWBSByProjectId(Context ctx, String projectId) throws BOSException, EASBizException, RemoteException;
    public void reCalculateNumber(Context ctx, Object wbsAdjustManager) throws BOSException, EASBizException, RemoteException;
    public void handleCancleCancle(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public Map saveOrderWBS(Context ctx, FDCWBSTree tree, boolean isRetMap) throws BOSException, EASBizException, RemoteException;
}