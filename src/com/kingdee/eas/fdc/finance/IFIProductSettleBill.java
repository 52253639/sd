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

public interface IFIProductSettleBill extends IFDCBill
{
    public FIProductSettleBillInfo getFIProductSettleBillInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FIProductSettleBillInfo getFIProductSettleBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FIProductSettleBillInfo getFIProductSettleBillInfo(String oql) throws BOSException, EASBizException;
    public FIProductSettleBillCollection getFIProductSettleBillCollection() throws BOSException;
    public FIProductSettleBillCollection getFIProductSettleBillCollection(EntityViewInfo view) throws BOSException;
    public FIProductSettleBillCollection getFIProductSettleBillCollection(String oql) throws BOSException;
}