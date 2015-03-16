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
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CommerceChanceController extends FDCBillController
{
    public CommerceChanceInfo getCommerceChanceInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CommerceChanceInfo getCommerceChanceInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CommerceChanceInfo getCommerceChanceInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CommerceChanceCollection getCommerceChanceCollection(Context ctx) throws BOSException, RemoteException;
    public CommerceChanceCollection getCommerceChanceCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CommerceChanceCollection getCommerceChanceCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateToTransaction(Context ctx, String type, String id) throws BOSException, EASBizException, RemoteException;
}