package com.kingdee.eas.fdc.schedule.report;

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

public interface IScheduleReportFacade extends IBizCtrl
{
    public Map getScheduleReportData(Map orgIds, Map dates, String scheduleType) throws BOSException, EASBizException;
    public Map getScheduleExpertReportData(Map dates, String projectID) throws BOSException;
    public Map getScheduleTaskInfo(Map paramMap) throws BOSException, EASBizException;
}