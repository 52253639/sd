package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.BankPaymentInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.sellhouse.BankPaymentCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BankPaymentController extends FDCBillController
{
    public BankPaymentInfo getBankPaymentInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BankPaymentInfo getBankPaymentInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BankPaymentInfo getBankPaymentInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BankPaymentCollection getBankPaymentCollection(Context ctx) throws BOSException, RemoteException;
    public BankPaymentCollection getBankPaymentCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BankPaymentCollection getBankPaymentCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}