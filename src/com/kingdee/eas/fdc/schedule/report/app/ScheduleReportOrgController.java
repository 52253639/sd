package com.kingdee.eas.fdc.schedule.report.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.app.FDCTreeBaseDataController;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgCollection;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleReportOrgController extends FDCTreeBaseDataController
{
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ScheduleReportOrgCollection getScheduleReportOrgCollection(Context ctx) throws BOSException, RemoteException;
    public ScheduleReportOrgCollection getScheduleReportOrgCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ScheduleReportOrgCollection getScheduleReportOrgCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateScheduleReportData(Context ctx, List scheduleReportCollection, List deleteScheduleReportDataList) throws BOSException, EASBizException, RemoteException;
    public boolean checkDefaultGroupOrg(Context ctx) throws BOSException, EASBizException, RemoteException;
}