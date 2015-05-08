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
import java.util.Map;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskExtInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCScheduleTaskExtController extends CoreBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskExtInfo getFDCScheduleTaskExtInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, FDCScheduleTaskExtInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, FDCScheduleTaskExtInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(Context ctx) throws BOSException, RemoteException;
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCScheduleTaskExtCollection getFDCScheduleTaskExtCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public void deletePropByWBSID(Context ctx, String wbsID) throws BOSException, EASBizException, RemoteException;
    public void deletePropByTaskExtID(Context ctx, String taskExtID) throws BOSException, EASBizException, RemoteException;
    public Map getProByWBSID(Context ctx, String WBSID) throws BOSException, EASBizException, RemoteException;
    public Map getProByTaskExtID(Context ctx, String taskExtID) throws BOSException, EASBizException, RemoteException;
    public void updateExeProp(Context ctx, Map extProperties) throws BOSException, EASBizException, RemoteException;
    public void updateCompleProp(Context ctx, Map extProperties) throws BOSException, EASBizException, RemoteException;
    public void saveExeProp(Context ctx, Map exeExtProperties) throws BOSException, EASBizException, RemoteException;
    public void saveCompileProp(Context ctx, Map exeCompileProperties) throws BOSException, EASBizException, RemoteException;
    public void deletePropByWBSIDs(Context ctx, Set wbsIDs) throws BOSException, EASBizException, RemoteException;
    public void deletePropByTaskExtIDs(Context ctx, Set wbsIDs) throws BOSException, EASBizException, RemoteException;
    public void copyFromAdjuestBill(Context ctx, String oldWBSID, String newWBSID) throws BOSException, EASBizException, RemoteException;
}