package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.tenancy.HandleTenancyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.HandleTenancyCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface HandleTenancyController extends TenBillBaseController
{
    public HandleTenancyInfo getHandleTenancyInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public HandleTenancyInfo getHandleTenancyInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public HandleTenancyInfo getHandleTenancyInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public HandleTenancyCollection getHandleTenancyCollection(Context ctx) throws BOSException, RemoteException;
    public HandleTenancyCollection getHandleTenancyCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public HandleTenancyCollection getHandleTenancyCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void handleTenancyRoom(Context ctx, IObjectCollection handleRoomEntryColl, IObjectValue tenancyBillInfo) throws BOSException, RemoteException;
}