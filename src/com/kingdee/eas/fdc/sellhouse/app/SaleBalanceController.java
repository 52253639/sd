package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.ObjectBaseController;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.SaleBalanceCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SaleBalanceController extends ObjectBaseController
{
    public SaleBalanceInfo getSaleBalanceInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SaleBalanceInfo getSaleBalanceInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SaleBalanceInfo getSaleBalanceInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SaleBalanceCollection getSaleBalanceCollection(Context ctx) throws BOSException, RemoteException;
    public SaleBalanceCollection getSaleBalanceCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SaleBalanceCollection getSaleBalanceCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Date getLeatestEndDate(Context ctx, String sellProjectId) throws BOSException, RemoteException;
}