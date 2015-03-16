package com.kingdee.eas.fdc.sellhouse;

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

public interface IBillAdjust extends IFDCBill
{
    public BillAdjustInfo getBillAdjustInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BillAdjustInfo getBillAdjustInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BillAdjustInfo getBillAdjustInfo(String oql) throws BOSException, EASBizException;
    public BillAdjustCollection getBillAdjustCollection() throws BOSException;
    public BillAdjustCollection getBillAdjustCollection(EntityViewInfo view) throws BOSException;
    public BillAdjustCollection getBillAdjustCollection(String oql) throws BOSException;
}