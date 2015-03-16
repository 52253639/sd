package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SincerityPurchaseController extends BaseTransactionController
{
    public SincerityPurchaseInfo getSincerityPurchaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SincerityPurchaseInfo getSincerityPurchaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SincerityPurchaseInfo getSincerityPurchaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SincerityPurchaseCollection getSincerityPurchaseCollection(Context ctx) throws BOSException, RemoteException;
    public SincerityPurchaseCollection getSincerityPurchaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SincerityPurchaseCollection getSincerityPurchaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void submitSincer(Context ctx, IObjectCollection sincerityColl) throws BOSException, EASBizException, RemoteException;
    public void quitNum(Context ctx, IObjectValue sinPurInfo, IObjectCollection selectorCollection) throws BOSException, EASBizException, RemoteException;
}