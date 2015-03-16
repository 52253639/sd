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
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomRecoverHistoryController extends DataBaseController
{
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, RoomRecoverHistoryInfo model) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, RoomRecoverHistoryInfo model) throws BOSException, EASBizException, RemoteException;
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection(Context ctx) throws BOSException, RemoteException;
}