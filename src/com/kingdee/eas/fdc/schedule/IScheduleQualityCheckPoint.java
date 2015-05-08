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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IScheduleQualityCheckPoint extends IFDCBill
{
    public ScheduleQualityCheckPointInfo getScheduleQualityCheckPointInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleQualityCheckPointInfo getScheduleQualityCheckPointInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleQualityCheckPointInfo getScheduleQualityCheckPointInfo(String oql) throws BOSException, EASBizException;
    public ScheduleQualityCheckPointCollection getScheduleQualityCheckPointCollection() throws BOSException;
    public ScheduleQualityCheckPointCollection getScheduleQualityCheckPointCollection(EntityViewInfo view) throws BOSException;
    public ScheduleQualityCheckPointCollection getScheduleQualityCheckPointCollection(String oql) throws BOSException;
}