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
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillCollection;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCReceiveBillController extends DataBaseController
{
    public FDCReceiveBillInfo getFDCReceiveBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCReceiveBillInfo getFDCReceiveBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCReceiveBillInfo getFDCReceiveBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCReceiveBillCollection getFDCReceiveBillCollection(Context ctx) throws BOSException, RemoteException;
    public FDCReceiveBillCollection getFDCReceiveBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCReceiveBillCollection getFDCReceiveBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public String submitByCasRev(Context ctx, ReceivingBillInfo casRev) throws BOSException, EASBizException, RemoteException;
    public void submitByCasRevColl(Context ctx, ReceivingBillCollection casRevColl) throws BOSException, EASBizException, RemoteException;
    public void addTemporaBill(Context ctx, ArrayList listIds) throws BOSException, EASBizException, RemoteException;
    public int getPrintCount(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public void updatePrintCount(Context ctx, String billID, int printCount) throws BOSException, EASBizException, RemoteException;
    public void addTemporaBill(Context ctx, ArrayList listBills, IObjectValue billInfo) throws BOSException, EASBizException, RemoteException;
}