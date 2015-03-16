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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basecrm.app.RevListController;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TenancyRoomPayListEntryController extends RevListController
{
    public TenancyRoomPayListEntryInfo getTenancyRoomPayListEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TenancyRoomPayListEntryInfo getTenancyRoomPayListEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TenancyRoomPayListEntryInfo getTenancyRoomPayListEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TenancyRoomPayListEntryCollection getTenancyRoomPayListEntryCollection(Context ctx) throws BOSException, RemoteException;
    public TenancyRoomPayListEntryCollection getTenancyRoomPayListEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TenancyRoomPayListEntryCollection getTenancyRoomPayListEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}