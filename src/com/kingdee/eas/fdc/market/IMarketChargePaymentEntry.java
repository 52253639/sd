package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.IObjectBase;

public interface IMarketChargePaymentEntry extends IObjectBase
{
    public MarketChargePaymentEntryInfo getMarketChargePaymentEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketChargePaymentEntryInfo getMarketChargePaymentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketChargePaymentEntryInfo getMarketChargePaymentEntryInfo(String oql) throws BOSException, EASBizException;
    public MarketChargePaymentEntryCollection getMarketChargePaymentEntryCollection() throws BOSException;
    public MarketChargePaymentEntryCollection getMarketChargePaymentEntryCollection(EntityViewInfo view) throws BOSException;
    public MarketChargePaymentEntryCollection getMarketChargePaymentEntryCollection(String oql) throws BOSException;
}