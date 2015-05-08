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

public interface IScheduleAdjustGattReq extends IFDCBill
{
    public ScheduleAdjustGattReqCollection getScheduleAdjustGattReqCollection() throws BOSException;
    public ScheduleAdjustGattReqCollection getScheduleAdjustGattReqCollection(EntityViewInfo view) throws BOSException;
    public ScheduleAdjustGattReqCollection getScheduleAdjustGattReqCollection(String oql) throws BOSException;
    public ScheduleAdjustGattReqInfo getScheduleAdjustGattReqInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleAdjustGattReqInfo getScheduleAdjustGattReqInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleAdjustGattReqInfo getScheduleAdjustGattReqInfo(String oql) throws BOSException, EASBizException;
}