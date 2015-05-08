package com.kingdee.eas.fdc.schedule.framework;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public interface IScheduleCalendar extends ICoreBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ScheduleCalendarInfo getScheduleCalendarInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleCalendarInfo getScheduleCalendarInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleCalendarInfo getScheduleCalendarInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(ScheduleCalendarInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, ScheduleCalendarInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, ScheduleCalendarInfo model) throws BOSException, EASBizException;
    public void updatePartial(ScheduleCalendarInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, ScheduleCalendarInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public ScheduleCalendarCollection getScheduleCalendarCollection() throws BOSException;
    public ScheduleCalendarCollection getScheduleCalendarCollection(EntityViewInfo view) throws BOSException;
    public ScheduleCalendarCollection getScheduleCalendarCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public Map getCalendar(String calendarId) throws BOSException, EASBizException;
    public ScheduleCalendarInfo getDefaultCal(String prjID) throws BOSException, EASBizException;
    public boolean isquote(String[] idstr) throws BOSException;
    public void reCalculateSchedule(Set scheduleIDSet, IObjectValue calendar) throws BOSException, EASBizException;
    public String verifyremote(IObjectValue model) throws BOSException;
    public String verifyremoteupdate(IObjectValue model) throws BOSException;
    public String addOnetoNewNum() throws BOSException;
}