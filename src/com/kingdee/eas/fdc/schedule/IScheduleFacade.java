package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Set;

public interface IScheduleFacade extends IBizCtrl
{
    public void reCalcParentTaskComplete(Set wbsIds) throws BOSException, EASBizException;
    public FDCScheduleInfo getOtherDeptSchedule(String scheduleId) throws BOSException, EASBizException;
    public void reCalLoadFromTaskLoad(String wbsId) throws BOSException, EASBizException;
    public void synchronizeTask(Set taskId, Set wbsId, String param) throws BOSException, EASBizException;
    public void upateCompleteDate(Set wbsIds, String param) throws BOSException, EASBizException;
    public FDCScheduleInfo importTasks(String scheduleId, String projectId, String taskTypeId, String parentWbsId, FDCScheduleTaskCollection tasks) throws BOSException, EASBizException;
}