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
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomPriceManageController extends FDCBillController
{
    public RoomPriceManageInfo getRoomPriceManageInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomPriceManageInfo getRoomPriceManageInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomPriceManageInfo getRoomPriceManageInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomPriceManageCollection getRoomPriceManageCollection(Context ctx) throws BOSException, RemoteException;
    public RoomPriceManageCollection getRoomPriceManageCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomPriceManageCollection getRoomPriceManageCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean excute(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public boolean isExistDown(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}