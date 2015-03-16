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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TenancyRoomEntryController extends CoreBaseController
{
    public TenancyRoomEntryInfo getTenancyRoomEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TenancyRoomEntryInfo getTenancyRoomEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TenancyRoomEntryInfo getTenancyRoomEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TenancyRoomEntryCollection getTenancyRoomEntryCollection(Context ctx) throws BOSException, RemoteException;
    public TenancyRoomEntryCollection getTenancyRoomEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TenancyRoomEntryCollection getTenancyRoomEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
}