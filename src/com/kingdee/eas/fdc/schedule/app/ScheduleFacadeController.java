package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleFacadeController extends BizController
{
    public void reCalcParentTaskComplete(Context ctx, Set wbsIds) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo getOtherDeptSchedule(Context ctx, String scheduleId) throws BOSException, EASBizException, RemoteException;
    public void reCalLoadFromTaskLoad(Context ctx, String wbsId) throws BOSException, EASBizException, RemoteException;
    public void synchronizeTask(Context ctx, Set taskId, Set wbsId, String param) throws BOSException, EASBizException, RemoteException;
    public void upateCompleteDate(Context ctx, Set wbsIds, String param) throws BOSException, EASBizException, RemoteException;
    public FDCScheduleInfo importTasks(Context ctx, String scheduleId, String projectId, String taskTypeId, String parentWbsId, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException, RemoteException;
}