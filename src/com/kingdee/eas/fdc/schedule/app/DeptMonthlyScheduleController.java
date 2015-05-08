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
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DeptMonthlyScheduleController extends FDCBillController
{
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection(Context ctx) throws BOSException, RemoteException;
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public List getAllTask(Context ctx, int year, int month, String id) throws BOSException, EASBizException, RemoteException;
    public List getAllTask(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void submitForWF(Context ctx, IObjectValue model) throws BOSException, EASBizException, RemoteException;
}