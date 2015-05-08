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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IScheduleAdjustGattReqWBSEntry extends ICoreBillEntryBase
{
    public ScheduleAdjustGattReqWBSEntryInfo getScheduleAdjustGattReqWBSEntryInfo(String oql) throws BOSException, EASBizException;
    public ScheduleAdjustGattReqWBSEntryInfo getScheduleAdjustGattReqWBSEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleAdjustGattReqWBSEntryInfo getScheduleAdjustGattReqWBSEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleAdjustGattReqWBSEntryCollection getScheduleAdjustGattReqWBSEntryCollection(String oql) throws BOSException;
    public ScheduleAdjustGattReqWBSEntryCollection getScheduleAdjustGattReqWBSEntryCollection(EntityViewInfo view) throws BOSException;
    public ScheduleAdjustGattReqWBSEntryCollection getScheduleAdjustGattReqWBSEntryCollection() throws BOSException;
}