package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASAppException;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCReceivingBillController extends FDCBillController
{
    public FDCReceivingBillInfo getFDCReceivingBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCReceivingBillInfo getFDCReceivingBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCReceivingBillInfo getFDCReceivingBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCReceivingBillCollection getFDCReceivingBillCollection(Context ctx) throws BOSException, RemoteException;
    public FDCReceivingBillCollection getFDCReceivingBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCReceivingBillCollection getFDCReceivingBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public String submitRev(Context ctx, IObjectValue rev, String handleClazzName) throws BOSException, EASBizException, RemoteException;
    public void receive(Context ctx, ArrayList recidList) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, BOSUuid fdcReceivingID, String handleClazzName) throws BOSException, EASBizException, RemoteException;
    public void canceReceive(Context ctx, ArrayList recidList) throws BOSException, EASBizException, RemoteException;
    public Map adjust(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void createCashBill(Context ctx, ArrayList idList, boolean isCreate) throws BOSException, EASAppException, RemoteException;
    public void adjustReceiveBill(Context ctx, BOSUuid billId, Map map) throws BOSException, EASBizException, RemoteException;
    public void receive(Context ctx, BOSUuid BOSUuid) throws BOSException, EASBizException, RemoteException;
}