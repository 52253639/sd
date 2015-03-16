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
import com.kingdee.eas.fdc.sellhouse.PushManageCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.PushManageInfo;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PushManageController extends FDCDataBaseController
{
    public PushManageInfo getPushManageInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PushManageInfo getPushManageInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PushManageInfo getPushManageInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PushManageCollection getPushManageCollection(Context ctx) throws BOSException, RemoteException;
    public PushManageCollection getPushManageCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PushManageCollection getPushManageCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void executed(Context ctx, Set pushRoomIds) throws BOSException, RemoteException;
    public void quitOrder(Context ctx, Set quitRoomIds) throws BOSException, RemoteException;
}