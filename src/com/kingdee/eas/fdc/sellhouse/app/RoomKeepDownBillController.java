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
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillCollection;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomKeepDownBillController extends FDCBillController
{
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomKeepDownBillInfo getRoomKeepDownBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection(Context ctx) throws BOSException, RemoteException;
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomKeepDownBillCollection getRoomKeepDownBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void cancelKeepDown(Context ctx, String roomId, String billId) throws BOSException, EASBizException, RemoteException;
    public boolean checkRoomKeepDown(Context ctx, String roomId, IObjectValue sheCustomer) throws BOSException, EASBizException, RemoteException;
}