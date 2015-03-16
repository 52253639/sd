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
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ChooseRoomController extends FDCBillController
{
    public ChooseRoomInfo getChooseRoomInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ChooseRoomInfo getChooseRoomInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ChooseRoomInfo getChooseRoomInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ChooseRoomCollection getChooseRoomCollection(Context ctx) throws BOSException, RemoteException;
    public ChooseRoomCollection getChooseRoomCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ChooseRoomCollection getChooseRoomCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean isValid(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public void cancelChooseRoom(Context ctx, String billID) throws BOSException, EASBizException, RemoteException;
    public void updateTrans(Context ctx, String billID, ChooseRoomStateEnum chooseRoomState) throws BOSException, EASBizException, RemoteException;
}