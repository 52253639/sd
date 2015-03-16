package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IMarketSupplierStockYearSale extends ICoreBillEntryBase
{
    public MarketSupplierStockYearSaleInfo getMarketSupplierStockYearSaleInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierStockYearSaleInfo getMarketSupplierStockYearSaleInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierStockYearSaleInfo getMarketSupplierStockYearSaleInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierStockYearSaleCollection getMarketSupplierStockYearSaleCollection() throws BOSException;
    public MarketSupplierStockYearSaleCollection getMarketSupplierStockYearSaleCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierStockYearSaleCollection getMarketSupplierStockYearSaleCollection(String oql) throws BOSException;
}