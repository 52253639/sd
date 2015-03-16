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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.sellhouse.ReceiveGatherCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ReceiveGatherController extends FDCBillController
{
    public ReceiveGatherInfo getReceiveGatherInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ReceiveGatherInfo getReceiveGatherInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ReceiveGatherInfo getReceiveGatherInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ReceiveGatherCollection getReceiveGatherCollection(Context ctx) throws BOSException, RemoteException;
    public ReceiveGatherCollection getReceiveGatherCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ReceiveGatherCollection getReceiveGatherCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void createReceivingBill(Context ctx, BOSUuid billID) throws BOSException, EASBizException, RemoteException;
    public void delReceivingToRev(Context ctx, IObjectValue info) throws BOSException, EASBizException, RemoteException;
    public void createVoucherToRev(Context ctx, ArrayList revList) throws BOSException, EASBizException, RemoteException;
    public void delVoucherToRev(Context ctx, ArrayList revList) throws BOSException, EASBizException, RemoteException;
    public void delVoucherToRev(Context ctx, IObjectPK sourceBillPk) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}