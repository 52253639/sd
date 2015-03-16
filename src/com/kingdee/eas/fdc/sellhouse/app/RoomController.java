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
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomController extends FDCDataBaseController
{
    public RoomInfo getRoomInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomInfo getRoomInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomInfo getRoomInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomCollection getRoomCollection(Context ctx) throws BOSException, RemoteException;
    public RoomCollection getRoomCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public RoomCollection getRoomCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public void doAreaAudit(Context ctx, List idList) throws BOSException, RemoteException;
    public void doActualAreaAudit(Context ctx, List idList) throws BOSException, RemoteException;
    public void doBasePriceAudit(Context ctx, List idList) throws BOSException, RemoteException;
    public void reclaimRoom(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public Map getRoomInfoCollectionMap(Context ctx, RoomInfo roomInfo, String collInfoNames) throws BOSException, EASBizException, RemoteException;
    public void roomIpdateBatch(Context ctx, List idList, Map map) throws BOSException, RemoteException;
    public void addRoomAreaChange(Context ctx, List idList, RoomAreaChangeTypeEnum type) throws BOSException, EASBizException, RemoteException;
    public void updateAreaInfo(Context ctx, List roomList) throws BOSException, EASBizException, RemoteException;
    public void planAudit(Context ctx, List roomIdList) throws BOSException, EASBizException, RemoteException;
    public void planUnAudit(Context ctx, List roomIdList) throws BOSException, EASBizException, RemoteException;
    public void preAudit(Context ctx, List roomIdList) throws BOSException, EASBizException, RemoteException;
    public void preUnAudit(Context ctx, List roomIdList) throws BOSException, EASBizException, RemoteException;
    public void actAudit(Context ctx, List roomIdList) throws BOSException, EASBizException, RemoteException;
    public void actUnAudit(Context ctx, List roomIdList) throws BOSException, EASBizException, RemoteException;
}