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
import com.kingdee.eas.fdc.finance.DeductBillCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface DeductBillController extends FDCBillController
{
    public DeductBillInfo getDeductBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public DeductBillInfo getDeductBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public DeductBillInfo getDeductBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public DeductBillCollection getDeductBillCollection(Context ctx) throws BOSException, RemoteException;
    public DeductBillCollection getDeductBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public DeductBillCollection getDeductBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void createPartADeductBill(Context ctx, String paymentId) throws BOSException, EASBizException, RemoteException;
}