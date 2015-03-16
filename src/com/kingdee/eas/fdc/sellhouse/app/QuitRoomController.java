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
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface QuitRoomController extends FDCBillController
{
    public QuitRoomInfo getQuitRoomInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public QuitRoomInfo getQuitRoomInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public QuitRoomInfo getQuitRoomInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public QuitRoomCollection getQuitRoomCollection(Context ctx) throws BOSException, RemoteException;
    public QuitRoomCollection getQuitRoomCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public QuitRoomCollection getQuitRoomCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean exeQuit(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void receiveBill(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void settleMent(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}