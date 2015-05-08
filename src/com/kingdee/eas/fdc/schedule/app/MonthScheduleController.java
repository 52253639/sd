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
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.schedule.MonthScheduleCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.base.permission.UserInfo;
import java.sql.ResultSet;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.framework.app.ScheduleBaseController;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.schedule.MonthScheduleInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MonthScheduleController extends ScheduleBaseController
{
    public MonthScheduleInfo getMonthScheduleInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MonthScheduleInfo getMonthScheduleInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MonthScheduleInfo getMonthScheduleInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MonthScheduleCollection getMonthScheduleCollection(Context ctx) throws BOSException, RemoteException;
    public MonthScheduleCollection getMonthScheduleCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MonthScheduleCollection getMonthScheduleCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ResultSet fetchTask(Context ctx, String startDate, String endDate, FullOrgUnitInfo adminDept, PersonInfo adminPerson) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleTaskCollection fetchTask0(Context ctx, Date start, Date end, String adminDeptID, PersonInfo adminPerson, boolean isThisMonth, CurProjectInfo curProject) throws BOSException, EASBizException, RemoteException;
    public Map audit(Context ctx, Set ids, UserInfo auditor) throws BOSException, EASBizException, RemoteException;
    public Map unAudit(Context ctx, Set ids, UserInfo unAduitor) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
}