package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IProjectMonthReportEntry extends IOpReportEntryBase
{
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(String oql) throws BOSException, EASBizException;
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection(String oql) throws BOSException;
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection(EntityViewInfo view) throws BOSException;
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection() throws BOSException;
}