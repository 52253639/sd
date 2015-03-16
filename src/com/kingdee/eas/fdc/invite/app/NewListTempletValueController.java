package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.eas.fdc.invite.NewListTempletValueCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.invite.NewListTempletValueInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewListTempletValueController extends CoreBillEntryBaseController
{
    public NewListTempletValueInfo getNewListTempletValueInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NewListTempletValueInfo getNewListTempletValueInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NewListTempletValueInfo getNewListTempletValueInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NewListTempletValueCollection getNewListTempletValueCollection(Context ctx) throws BOSException, RemoteException;
    public NewListTempletValueCollection getNewListTempletValueCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NewListTempletValueCollection getNewListTempletValueCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public NewListTempletValueCollection getCollectionBySQL(Context ctx, String listingIds) throws BOSException, EASBizException, RemoteException;
}