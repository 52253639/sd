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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IScheduleAdjustReqBill extends IFDCBill
{
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleAdjustReqBillInfo getScheduleAdjustReqBillInfo(String oql) throws BOSException, EASBizException;
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection() throws BOSException;
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection(EntityViewInfo view) throws BOSException;
    public ScheduleAdjustReqBillCollection getScheduleAdjustReqBillCollection(String oql) throws BOSException;
    public void createNewVerData(ScheduleAdjustReqBillInfo model) throws BOSException, EASBizException;
    public ScheduleAdjustReqBillInfo getValue2(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ScheduleAdjustReqBillInfo getNewData(Map param) throws BOSException, EASBizException;
}