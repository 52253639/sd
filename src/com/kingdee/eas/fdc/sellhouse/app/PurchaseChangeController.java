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
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PurchaseChangeController extends FDCBillController
{
    public PurchaseChangeInfo getPurchaseChangeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PurchaseChangeInfo getPurchaseChangeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PurchaseChangeInfo getPurchaseChangeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PurchaseChangeCollection getPurchaseChangeCollection(Context ctx) throws BOSException, RemoteException;
    public PurchaseChangeCollection getPurchaseChangeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PurchaseChangeCollection getPurchaseChangeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void setAudited(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setAuditing(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
    public void setState(Context ctx, BOSUuid id, FDCBillStateEnum state) throws BOSException, EASBizException, RemoteException;
    public void purchaseChangeToPurchase(Context ctx, BOSUuid id) throws BOSException, EASBizException, RemoteException;
}