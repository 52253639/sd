package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.BillAdjustInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.BillAdjustCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BillAdjustController extends FDCBillController
{
    public BillAdjustInfo getBillAdjustInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BillAdjustInfo getBillAdjustInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BillAdjustInfo getBillAdjustInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BillAdjustCollection getBillAdjustCollection(Context ctx) throws BOSException, RemoteException;
    public BillAdjustCollection getBillAdjustCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BillAdjustCollection getBillAdjustCollection(Context ctx, String oql) throws BOSException, RemoteException;
}