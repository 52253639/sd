package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PrePurchasePayListEntryController extends TranPayListEntryController
{
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection(Context ctx) throws BOSException, RemoteException;
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PrePurchasePayListEntryCollection getPrePurchasePayListEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PrePurchasePayListEntryInfo getPrePurchasePayListEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, PrePurchasePayListEntryInfo model) throws BOSException, EASBizException, RemoteException;
}