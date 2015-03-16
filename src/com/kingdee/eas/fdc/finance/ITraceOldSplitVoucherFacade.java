package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fi.cas.PaymentBillCollection;

public interface ITraceOldSplitVoucherFacade extends IBizCtrl
{
    public void traceSplitVoucher(String contractID) throws BOSException, EASBizException;
    public IObjectPK[] splitContract(String contractID, PaymentBillCollection paymentBill) throws BOSException, EASBizException;
    public IObjectPK splitPayment(PaymentSplitInfo model) throws BOSException, EASBizException;
    public IObjectPK splitNoCostPayment(PaymentNoCostSplitInfo model) throws BOSException, EASBizException;
    public void traceContracts(List idList) throws BOSException, EASBizException;
    public void traceContractNoText(List idList) throws BOSException, EASBizException;
    public void traceAdjustContracts(List idList, boolean isNoText, VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException, EASBizException;
}