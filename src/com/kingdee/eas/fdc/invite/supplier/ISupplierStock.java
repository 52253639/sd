package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import java.util.List;

public interface ISupplierStock extends IFDCDataBase
{
    public SupplierStockInfo getSupplierStockInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierStockInfo getSupplierStockInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierStockInfo getSupplierStockInfo(String oql) throws BOSException, EASBizException;
    public SupplierStockCollection getSupplierStockCollection() throws BOSException;
    public SupplierStockCollection getSupplierStockCollection(EntityViewInfo view) throws BOSException;
    public SupplierStockCollection getSupplierStockCollection(String oql) throws BOSException;
    public Map approve(IObjectPK[] pks) throws BOSException, EASBizException;
    public Map unApprove(IObjectPK[] pks) throws BOSException, EASBizException;
    public void submitTwo(IObjectValue supObjectValue) throws BOSException, EASBizException;
    public SupplierCollection getSupplierDataBase(String supLongNumber) throws BOSException, EASBizException;
    public Map importDataBase(IObjectCollection objCollection, String typeLongNumber) throws BOSException, EASBizException;
    public FDCSplAreaCollection getSericeArea() throws BOSException, EASBizException;
    public Set getDataBase(List list) throws BOSException, EASBizException;
    public Set getAHDBValue(List list) throws BOSException, EASBizException;
    public Set getEHDBValue(List list) throws BOSException, EASBizException;
    public Set getSHDBValue(List list) throws BOSException, EASBizException;
    public boolean getATRFValue(List list) throws BOSException, EASBizException;
    public void updateHisCount(UpdateHistoryTypeEnum historyType, IObjectPK pk, boolean isAudit) throws BOSException, EASBizException;
    public void beachUpdateHisCount(UpdateHistoryTypeEnum historyType, IObjectPK[] pks, boolean isAudit) throws BOSException, EASBizException;
    public void batchAssign2(String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException;
    public void batchAssign2(String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException;
    public void addToSysSupplier(IObjectValue objectValue) throws BOSException, EASBizException;
}