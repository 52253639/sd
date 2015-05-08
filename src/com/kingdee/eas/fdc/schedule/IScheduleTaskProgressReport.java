package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import java.lang.String;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.*;

public interface IScheduleTaskProgressReport extends IProgressReportBase
{
    public ScheduleTaskProgressReportInfo getScheduleTaskProgressReportInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleTaskProgressReportInfo getScheduleTaskProgressReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleTaskProgressReportInfo getScheduleTaskProgressReportInfo(String oql) throws BOSException, EASBizException;
    public ScheduleTaskProgressReportCollection getScheduleTaskProgressReportCollection() throws BOSException;
    public ScheduleTaskProgressReportCollection getScheduleTaskProgressReportCollection(EntityViewInfo view) throws BOSException;
    public ScheduleTaskProgressReportCollection getScheduleTaskProgressReportCollection(String oql) throws BOSException;
    public void afterschreportwriteBack(IObjectValue model) throws BOSException;
}