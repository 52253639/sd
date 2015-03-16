package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.BoutiqueRoomTypeInfo;
import com.kingdee.eas.fdc.market.BoutiqueRoomTypeCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BoutiqueRoomTypeController extends DataBaseController
{
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BoutiqueRoomTypeInfo getBoutiqueRoomTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection(Context ctx) throws BOSException, RemoteException;
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BoutiqueRoomTypeCollection getBoutiqueRoomTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}