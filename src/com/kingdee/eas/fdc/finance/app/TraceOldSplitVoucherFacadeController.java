package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TraceOldSplitVoucherFacadeController extends BizController
{
    public void traceSplitVoucher(Context ctx, String contractID) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] splitContract(Context ctx, String contractID, PaymentBillCollection paymentBill) throws BOSException, EASBizException, RemoteException;
    public IObjectPK splitPayment(Context ctx, PaymentSplitInfo model) throws BOSException, EASBizException, RemoteException;
    public IObjectPK splitNoCostPayment(Context ctx, PaymentNoCostSplitInfo model) throws BOSException, EASBizException, RemoteException;
    public void traceContracts(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void traceContractNoText(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void traceAdjustContracts(Context ctx, List idList, boolean isNoText, VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException, EASBizException, RemoteException;
}