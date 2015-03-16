package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITenRentBillDaysOfMonth extends IDataBase
{
    public TenRentBillDaysOfMonthCollection getTenRentBillDaysOfMonthCollection() throws BOSException;
    public TenRentBillDaysOfMonthCollection getTenRentBillDaysOfMonthCollection(EntityViewInfo view) throws BOSException;
    public TenRentBillDaysOfMonthCollection getTenRentBillDaysOfMonthCollection(String oql) throws BOSException;
    public TenRentBillDaysOfMonthInfo getTenRentBillDaysOfMonthInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenRentBillDaysOfMonthInfo getTenRentBillDaysOfMonthInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenRentBillDaysOfMonthInfo getTenRentBillDaysOfMonthInfo(String oql) throws BOSException, EASBizException;
}