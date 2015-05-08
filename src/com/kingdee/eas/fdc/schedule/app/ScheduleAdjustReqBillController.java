package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustReqBillCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleAdjustReqBillController extends FDCBillController
{
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection(Context ctx) throws BOSException, RemoteException;
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void createNewVerData(Context ctx, ScheduleAdjustReqBillInfo model) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustReqBillInfo getValue2(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustReqBillInfo getNewData(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}