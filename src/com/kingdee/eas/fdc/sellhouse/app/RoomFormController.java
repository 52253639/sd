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
import com.kingdee.eas.fdc.sellhouse.RoomFormCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RoomFormController extends FDCDataBaseController
{
    public RoomFormInfo getRoomFormInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RoomFormInfo getRoomFormInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RoomFormInfo getRoomFormInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RoomFormCollection getRoomFormCollection(Context ctx) throws BOSException, RemoteException;
    public RoomFormCollection getRoomFormCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RoomFormCollection getRoomFormCollection(Context ctx, String oql) throws BOSException, RemoteException;
}