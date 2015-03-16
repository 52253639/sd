package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewListingValueController extends CoreBillEntryBaseController
{
    public NewListingValueInfo getNewListingValueInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NewListingValueInfo getNewListingValueInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NewListingValueInfo getNewListingValueInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NewListingValueCollection getNewListingValueCollection(Context ctx) throws BOSException, RemoteException;
    public NewListingValueCollection getNewListingValueCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NewListingValueCollection getNewListingValueCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public NewListingValueCollection getCollectionBySQL(Context ctx, String listingId) throws BOSException, EASBizException, RemoteException;
}