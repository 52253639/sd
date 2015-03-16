package com.kingdee.eas.fdc.finance;

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

public interface IFDCAdjustBill extends IFDCBill
{
    public FDCAdjustBillInfo getFDCAdjustBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCAdjustBillInfo getFDCAdjustBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCAdjustBillInfo getFDCAdjustBillInfo(String oql) throws BOSException, EASBizException;
    public FDCAdjustBillCollection getFDCAdjustBillCollection() throws BOSException;
    public FDCAdjustBillCollection getFDCAdjustBillCollection(EntityViewInfo view) throws BOSException;
    public FDCAdjustBillCollection getFDCAdjustBillCollection(String oql) throws BOSException;
}