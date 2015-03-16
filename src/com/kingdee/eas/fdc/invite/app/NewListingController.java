package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.invite.NewListingCollection;
import com.kingdee.eas.fdc.invite.FDCInviteException;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewListingController extends FDCBillController
{
    public NewListingInfo getNewListingInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NewListingInfo getNewListingInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NewListingInfo getNewListingInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NewListingCollection getNewListingCollection(Context ctx) throws BOSException, RemoteException;
    public NewListingCollection getNewListingCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NewListingCollection getNewListingCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public NewListingInfo getNewListingAllValue(Context ctx, String listingId) throws BOSException, EASBizException, RemoteException;
    public void inviteAuditSubmit(Context ctx, BOSUuid listingId) throws BOSException, EASBizException, RemoteException;
    public void setAuditing(Context ctx, BOSUuid listingId) throws BOSException, EASBizException, RemoteException;
    public void setAudited(Context ctx, BOSUuid listingId) throws BOSException, EASBizException, RemoteException;
    public void setInviteSubmitStatus(Context ctx, BOSUuid listingId) throws BOSException, EASBizException, RemoteException;
    public IObjectValue getAllReversionData(Context ctx, BOSUuid billId) throws BOSException, FDCInviteException, RemoteException;
}