package com.kingdee.eas.fdc.aimcost;

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

public interface IVoucherForDynamic extends IFDCBill
{
    public VoucherForDynamicInfo getVoucherForDynamicInfo(IObjectPK pk) throws BOSException, EASBizException;
    public VoucherForDynamicInfo getVoucherForDynamicInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public VoucherForDynamicInfo getVoucherForDynamicInfo(String oql) throws BOSException, EASBizException;
    public VoucherForDynamicCollection getVoucherForDynamicCollection() throws BOSException;
    public VoucherForDynamicCollection getVoucherForDynamicCollection(EntityViewInfo view) throws BOSException;
    public VoucherForDynamicCollection getVoucherForDynamicCollection(String oql) throws BOSException;
}