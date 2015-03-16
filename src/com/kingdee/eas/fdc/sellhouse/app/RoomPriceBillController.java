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
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomPriceBillController extends FDCBillController
{
    public RoomPriceBillInfo getRoomPriceBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomPriceBillInfo getRoomPriceBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomPriceBillInfo getRoomPriceBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomPriceBillCollection getRoomPriceBillCollection(Context ctx) throws BOSException, RemoteException;
    public RoomPriceBillCollection getRoomPriceBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomPriceBillCollection getRoomPriceBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean execute(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void updateIsCalByRoomArea(Context ctx, RoomPriceBillEntryInfo module) throws BOSException, EASBizException, RemoteException;
}