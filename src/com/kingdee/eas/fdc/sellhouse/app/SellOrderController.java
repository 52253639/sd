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
import com.kingdee.eas.fdc.sellhouse.SellOrderCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.SellOrderInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SellOrderController extends FDCDataBaseController
{
    public SellOrderInfo getSellOrderInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SellOrderInfo getSellOrderInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SellOrderInfo getSellOrderInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SellOrderCollection getSellOrderCollection(Context ctx) throws BOSException, RemoteException;
    public SellOrderCollection getSellOrderCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SellOrderCollection getSellOrderCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void quitOrder(Context ctx, Set quitRoomIds) throws BOSException, EASBizException, RemoteException;
}