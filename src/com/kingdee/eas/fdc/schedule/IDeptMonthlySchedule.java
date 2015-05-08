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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IDeptMonthlySchedule extends IFDCBill
{
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection() throws BOSException;
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection(EntityViewInfo view) throws BOSException;
    public DeptMonthlyScheduleCollection getDeptMonthlyScheduleCollection(String oql) throws BOSException;
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DeptMonthlyScheduleInfo getDeptMonthlyScheduleInfo(String oql) throws BOSException, EASBizException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public List getAllTask(int year, int month, String id) throws BOSException, EASBizException;
    public List getAllTask(String id) throws BOSException, EASBizException;
    public void submitForWF(IObjectValue model) throws BOSException, EASBizException;
}