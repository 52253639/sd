package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.schedule.framework.IScheduleBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.bos.framework.*;
import java.sql.ResultSet;
import com.kingdee.bos.util.BOSUuid;

public interface IMonthSchedule extends IScheduleBase
{
    public MonthScheduleInfo getMonthScheduleInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MonthScheduleInfo getMonthScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MonthScheduleInfo getMonthScheduleInfo(String oql) throws BOSException, EASBizException;
    public MonthScheduleCollection getMonthScheduleCollection() throws BOSException;
    public MonthScheduleCollection getMonthScheduleCollection(EntityViewInfo view) throws BOSException;
    public MonthScheduleCollection getMonthScheduleCollection(String oql) throws BOSException;
    public ResultSet fetchTask(String startDate, String endDate, FullOrgUnitInfo adminDept, PersonInfo adminPerson) throws BOSException, EASBizException;
    public FDCScheduleTaskCollection fetchTask0(Date start, Date end, String adminDeptID, PersonInfo adminPerson, boolean isThisMonth, CurProjectInfo curProject) throws BOSException, EASBizException;
    public Map audit(Set ids, UserInfo auditor) throws BOSException, EASBizException;
    public Map unAudit(Set ids, UserInfo unAduitor) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billId) throws BOSException;
    public void setSubmitStatus(BOSUuid billId) throws BOSException;
    public void unAudit(BOSUuid billID) throws BOSException, EASBizException;
    public void audit(BOSUuid billID) throws BOSException, EASBizException;
}