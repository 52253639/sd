package com.kingdee.eas.fdc.invite.markesupplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import java.util.HashSet;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketSupplierStockController extends FDCDataBaseController
{
    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx) throws BOSException, RemoteException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map approve(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public Map unApprove(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void submitTwo(Context ctx, IObjectValue supObjectValue) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockCollection getSupplierDataBase(Context ctx, String supLongNumber) throws BOSException, EASBizException, RemoteException;
    public Map importDataBase(Context ctx, IObjectCollection objCollection, String typeLongNumber) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierStockCollection getSericeArea(Context ctx) throws BOSException, EASBizException, RemoteException;
    public Set getDataBase(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public Set getAHDBValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public Set getEHDBValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public Set getSHDBValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public boolean getATRFValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public void updateHisCount(Context ctx, String historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException, RemoteException;
    public void beachUpdateHisCount(Context ctx, String historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException, RemoteException;
    public void batchAssign2(Context ctx, String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException, RemoteException;
    public void batchAssign2(Context ctx, String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException, RemoteException;
    public void addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException, RemoteException;
}