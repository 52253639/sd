package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCSplitBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import java.util.List;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PaymentSplitController extends FDCSplitBillController
{
    public PaymentSplitInfo getPaymentSplitInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PaymentSplitInfo getPaymentSplitInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PaymentSplitInfo getPaymentSplitInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PaymentSplitCollection getPaymentSplitCollection(Context ctx) throws BOSException, RemoteException;
    public PaymentSplitCollection getPaymentSplitCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PaymentSplitCollection getPaymentSplitCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void traceData(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void afterVoucher(Context ctx, PaymentSplitCollection sourceBillCollection) throws BOSException, EASBizException, RemoteException;
    public void traceSplitByPay(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
}