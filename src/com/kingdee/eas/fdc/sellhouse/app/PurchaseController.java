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
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PurchaseController extends FDCBillController
{
    public PurchaseInfo getPurchaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PurchaseInfo getPurchaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PurchaseInfo getPurchaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PurchaseCollection getPurchaseCollection(Context ctx) throws BOSException, RemoteException;
    public PurchaseCollection getPurchaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PurchaseCollection getPurchaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void checkPrePurchase(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void uncheckPrePurchase(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void submitPrePurchase(Context ctx, PurchaseInfo model) throws BOSException, RemoteException;
    public void auditPrePurchase(Context ctx, BOSUuid billId) throws BOSException, RemoteException;
    public void receiveBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void purDistillUpdate(Context ctx) throws BOSException, RemoteException;
    public void purAddMarket(Context ctx) throws BOSException, EASBizException, RemoteException;
    public void immediacySave(Context ctx, PurchaseInfo model) throws BOSException, EASBizException, RemoteException;
}