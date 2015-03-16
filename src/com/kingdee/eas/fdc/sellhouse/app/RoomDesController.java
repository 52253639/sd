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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.RoomDesCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomDesController extends FDCDataBaseController
{
    public RoomDesInfo getRoomDesInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomDesInfo getRoomDesInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomDesInfo getRoomDesInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomDesCollection getRoomDesCollection(Context ctx) throws BOSException, RemoteException;
    public RoomDesCollection getRoomDesCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomDesCollection getRoomDesCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean cleanBuilding(Context ctx, IObjectValue building) throws BOSException, EASBizException, RemoteException;
    public boolean roomDesSumbit(Context ctx, IObjectValue building, IObjectCollection Roomdes, IObjectCollection buildingUnit) throws BOSException, EASBizException, RemoteException;
}