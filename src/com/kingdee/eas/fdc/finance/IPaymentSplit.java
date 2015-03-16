package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.basedata.IFDCSplitBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IPaymentSplit extends IFDCSplitBill
{
    public PaymentSplitInfo getPaymentSplitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PaymentSplitInfo getPaymentSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PaymentSplitInfo getPaymentSplitInfo(String oql) throws BOSException, EASBizException;
    public PaymentSplitCollection getPaymentSplitCollection() throws BOSException;
    public PaymentSplitCollection getPaymentSplitCollection(EntityViewInfo view) throws BOSException;
    public PaymentSplitCollection getPaymentSplitCollection(String oql) throws BOSException;
    public void traceData(List idList) throws BOSException, EASBizException;
    public void afterVoucher(PaymentSplitCollection sourceBillCollection) throws BOSException, EASBizException;
    public void traceSplitByPay(String id) throws BOSException, EASBizException;
}