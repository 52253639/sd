package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewQuotingPriceController extends FDCBillController
{
    public NewQuotingPriceInfo getNewQuotingPriceInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NewQuotingPriceInfo getNewQuotingPriceInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NewQuotingPriceInfo getNewQuotingPriceInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NewQuotingPriceCollection getNewQuotingPriceCollection(Context ctx) throws BOSException, RemoteException;
    public NewQuotingPriceCollection getNewQuotingPriceCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NewQuotingPriceCollection getNewQuotingPriceCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void acceptBidExportQuoting(Context ctx, String quotingId, String listingId) throws BOSException, EASBizException, RemoteException;
    public void acceptBid(Context ctx, String quotingId) throws BOSException, EASBizException, RemoteException;
    public void exportQuoting(Context ctx, String quotingId, String listingId) throws BOSException, EASBizException, RemoteException;
    public void unacceptBid(Context ctx, String quotingId) throws BOSException, EASBizException, RemoteException;
}