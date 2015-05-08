package com.kingdee.eas.fdc.schedule.report.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleReportFacadeController extends BizController
{
    public Map getScheduleReportData(Context ctx, Map orgIds, Map dates, String scheduleType) throws BOSException, EASBizException, RemoteException;
    public Map getScheduleExpertReportData(Context ctx, Map dates, String projectID) throws BOSException, RemoteException;
    public Map getScheduleTaskInfo(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
}