package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import java.util.List;

public interface IMarketSupplierStock extends IFDCDataBase
{
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierStockInfo getMarketSupplierStockInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection() throws BOSException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierStockCollection getMarketSupplierStockCollection(String oql) throws BOSException;
    public Map approve(IObjectPK[] pks) throws BOSException, EASBizException;
    public Map unApprove(IObjectPK[] pks) throws BOSException, EASBizException;
    public void submitTwo(IObjectValue supObjectValue) throws BOSException, EASBizException;
    public MarketSupplierStockCollection getSupplierDataBase(String supLongNumber) throws BOSException, EASBizException;
    public Map importDataBase(IObjectCollection objCollection, String typeLongNumber) throws BOSException, EASBizException;
    public MarketSupplierStockCollection getSericeArea() throws BOSException, EASBizException;
    public Set getDataBase(List list) throws BOSException, EASBizException;
    public Set getAHDBValue(List list) throws BOSException, EASBizException;
    public Set getEHDBValue(List list) throws BOSException, EASBizException;
    public Set getSHDBValue(List list) throws BOSException, EASBizException;
    public boolean getATRFValue(List list) throws BOSException, EASBizException;
    public void updateHisCount(String historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException;
    public void beachUpdateHisCount(String historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException;
    public void batchAssign2(String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException;
    public void batchAssign2(String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException;
    public void addToSysSupplier(IObjectValue objectValue) throws BOSException, EASBizException;
}