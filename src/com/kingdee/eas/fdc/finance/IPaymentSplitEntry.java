package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
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

public interface IPaymentSplitEntry extends IFDCSplitBillEntry
{
    public PaymentSplitEntryInfo getPaymentSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PaymentSplitEntryInfo getPaymentSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PaymentSplitEntryInfo getPaymentSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public PaymentSplitEntryCollection getPaymentSplitEntryCollection() throws BOSException;
    public PaymentSplitEntryCollection getPaymentSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public PaymentSplitEntryCollection getPaymentSplitEntryCollection(String oql) throws BOSException;
}