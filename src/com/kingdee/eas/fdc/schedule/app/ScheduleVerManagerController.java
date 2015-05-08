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
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerCollection;
import com.kingdee.eas.framework.app.BillBaseController;
import com.kingdee.eas.fdc.schedule.ScheduleVerManagerInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleVerManagerController extends BillBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ScheduleVerManagerInfo getScheduleVerManagerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ScheduleVerManagerInfo getScheduleVerManagerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ScheduleVerManagerInfo getScheduleVerManagerInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, ScheduleVerManagerInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, ScheduleVerManagerInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, ScheduleVerManagerInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, ScheduleVerManagerInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, ScheduleVerManagerInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public ScheduleVerManagerCollection getScheduleVerManagerCollection(Context ctx) throws BOSException, RemoteException;
    public ScheduleVerManagerCollection getScheduleVerManagerCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ScheduleVerManagerCollection getScheduleVerManagerCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getNewVerData(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getVerData(Context ctx, String prjId, float version) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getVerData(Context ctx, String verId) throws BOSException, EASBizException, RemoteException;
    public ScheduleVerManagerCollection getVerData(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public IObjectPK createNewVer(Context ctx, String prjId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}