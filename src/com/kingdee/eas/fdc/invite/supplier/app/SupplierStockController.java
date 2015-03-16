package com.kingdee.eas.fdc.invite.supplier.app;

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
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.supplier.UpdateHistoryTypeEnum;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import java.util.List;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SupplierStockController extends FDCDataBaseController
{
    public SupplierStockInfo getSupplierStockInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SupplierStockInfo getSupplierStockInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SupplierStockInfo getSupplierStockInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SupplierStockCollection getSupplierStockCollection(Context ctx) throws BOSException, RemoteException;
    public SupplierStockCollection getSupplierStockCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SupplierStockCollection getSupplierStockCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Map approve(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public Map unApprove(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException, RemoteException;
    public void submitTwo(Context ctx, IObjectValue supObjectValue) throws BOSException, EASBizException, RemoteException;
    public SupplierCollection getSupplierDataBase(Context ctx, String supLongNumber) throws BOSException, EASBizException, RemoteException;
    public Map importDataBase(Context ctx, IObjectCollection objCollection, String typeLongNumber) throws BOSException, EASBizException, RemoteException;
    public FDCSplAreaCollection getSericeArea(Context ctx) throws BOSException, EASBizException, RemoteException;
    public Set getDataBase(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public Set getAHDBValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public Set getEHDBValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public Set getSHDBValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public boolean getATRFValue(Context ctx, List list) throws BOSException, EASBizException, RemoteException;
    public void updateHisCount(Context ctx, UpdateHistoryTypeEnum historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException, RemoteException;
    public void beachUpdateHisCount(Context ctx, UpdateHistoryTypeEnum historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException, RemoteException;
    public void batchAssign2(Context ctx, String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException, RemoteException;
    public void batchAssign2(Context ctx, String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException, RemoteException;
    public void addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException, RemoteException;
}