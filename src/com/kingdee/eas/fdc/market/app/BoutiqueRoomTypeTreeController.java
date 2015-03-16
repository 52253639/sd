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
import com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.market.BoutiqueRoomTypeTreeInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface BoutiqueRoomTypeTreeController extends TreeBaseController
{
    public BoutiqueRoomTypeTreeInfo getBoutiqueRoomTypeTreeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public BoutiqueRoomTypeTreeInfo getBoutiqueRoomTypeTreeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public BoutiqueRoomTypeTreeInfo getBoutiqueRoomTypeTreeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public BoutiqueRoomTypeTreeCollection getBoutiqueRoomTypeTreeCollection(Context ctx) throws BOSException, RemoteException;
    public BoutiqueRoomTypeTreeCollection getBoutiqueRoomTypeTreeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public BoutiqueRoomTypeTreeCollection getBoutiqueRoomTypeTreeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}