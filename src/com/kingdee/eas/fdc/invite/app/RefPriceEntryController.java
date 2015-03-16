package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.RefPriceEntryCollection;
import java.util.HashMap;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface RefPriceEntryController extends CoreBaseController
{
    public RefPriceEntryInfo getRefPriceEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public RefPriceEntryInfo getRefPriceEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public RefPriceEntryInfo getRefPriceEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public RefPriceEntryCollection getRefPriceEntryCollection(Context ctx) throws BOSException, RemoteException;
    public RefPriceEntryCollection getRefPriceEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public RefPriceEntryCollection getRefPriceEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public RefPriceEntryCollection getCollection(Context ctx, EntityViewInfo view, HashMap cols) throws BOSException, RemoteException;
}