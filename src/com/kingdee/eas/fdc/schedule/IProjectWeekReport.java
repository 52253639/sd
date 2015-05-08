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

public interface IProjectWeekReport extends IOpReportBase
{
    public ProjectWeekReportInfo getProjectWeekReportInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ProjectWeekReportInfo getProjectWeekReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ProjectWeekReportInfo getProjectWeekReportInfo(String oql) throws BOSException, EASBizException;
    public ProjectWeekReportCollection getProjectWeekReportCollection() throws BOSException;
    public ProjectWeekReportCollection getProjectWeekReportCollection(EntityViewInfo view) throws BOSException;
    public ProjectWeekReportCollection getProjectWeekReportCollection(String oql) throws BOSException;
}