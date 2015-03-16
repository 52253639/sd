package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketSupplierAppraiseTemplate extends IDataBase
{
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(String oql) throws BOSException, EASBizException;
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection() throws BOSException;
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection(EntityViewInfo view) throws BOSException;
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection(String oql) throws BOSException;
    public void audit(MarketSupplierAppraiseTemplateInfo model) throws BOSException;
    public void unaudit(MarketSupplierAppraiseTemplateInfo model) throws BOSException;
}