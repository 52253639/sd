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
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomJoinController extends FDCBillController
{
    public RoomJoinInfo getRoomJoinInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomJoinInfo getRoomJoinInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomJoinInfo getRoomJoinInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomJoinCollection getRoomJoinCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomJoinCollection getRoomJoinCollection(Context ctx) throws BOSException, RemoteException;
    public RoomJoinCollection getRoomJoinCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void batchSave(Context ctx, RoomJoinCollection roomJoins) throws BOSException, EASBizException, RemoteException;
}