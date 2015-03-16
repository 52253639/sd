package com.kingdee.eas.fdc.invite.markesupplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAchievementCollection;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockAchievementInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketSupplierStockAchievementController extends CoreBillEntryBaseController
{
    public MarketSupplierStockAchievementInfo getMarketSupplierStockAchievementInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockAchievementInfo getMarketSupplierStockAchievementInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockAchievementInfo getMarketSupplierStockAchievementInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockAchievementCollection getMarketSupplierStockAchievementCollection(Context ctx) throws BOSException, RemoteException;
    public MarketSupplierStockAchievementCollection getMarketSupplierStockAchievementCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketSupplierStockAchievementCollection getMarketSupplierStockAchievementCollection(Context ctx, String oql) throws BOSException, RemoteException;
}