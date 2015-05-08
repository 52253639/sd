package com.kingdee.eas.fdc.schedule.report;

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
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public interface IScheduleReportOrg extends IFDCTreeBaseData
{
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleReportOrgInfo getScheduleReportOrgInfo(String oql) throws BOSException, EASBizException;
    public ScheduleReportOrgCollection getScheduleReportOrgCollection() throws BOSException;
    public ScheduleReportOrgCollection getScheduleReportOrgCollection(EntityViewInfo view) throws BOSException;
    public ScheduleReportOrgCollection getScheduleReportOrgCollection(String oql) throws BOSException;
    public void updateScheduleReportData(List scheduleReportCollection, List deleteScheduleReportDataList) throws BOSException, EASBizException;
    public boolean checkDefaultGroupOrg() throws BOSException, EASBizException;
}