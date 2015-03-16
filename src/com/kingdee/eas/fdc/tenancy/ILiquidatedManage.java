package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface ILiquidatedManage extends IFDCDataBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public LiquidatedManageInfo getLiquidatedManageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public LiquidatedManageInfo getLiquidatedManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public LiquidatedManageInfo getLiquidatedManageInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(LiquidatedManageInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, LiquidatedManageInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, LiquidatedManageInfo model) throws BOSException, EASBizException;
    public void updatePartial(LiquidatedManageInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, LiquidatedManageInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public LiquidatedManageCollection getLiquidatedManageCollection() throws BOSException;
    public LiquidatedManageCollection getLiquidatedManageCollection(EntityViewInfo view) throws BOSException;
    public LiquidatedManageCollection getLiquidatedManageCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean account(BOSUuid tenBillOtherPayID) throws BOSException, EASBizException;
    public int batchAccount(BOSUuid sellProject) throws BOSException, EASBizException;
    public boolean genTenBillOtherPay(BOSUuid id) throws BOSException, EASBizException;
}