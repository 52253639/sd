package com.kingdee.eas.fdc.tenancy;

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

public interface IMarketingUnitSellProject extends IDataBase
{
    public MarketingUnitSellProjectInfo getMarketingUnitSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketingUnitSellProjectInfo getMarketingUnitSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketingUnitSellProjectInfo getMarketingUnitSellProjectInfo(String oql) throws BOSException, EASBizException;
    public MarketingUnitSellProjectCollection getMarketingUnitSellProjectCollection() throws BOSException;
    public MarketingUnitSellProjectCollection getMarketingUnitSellProjectCollection(EntityViewInfo view) throws BOSException;
    public MarketingUnitSellProjectCollection getMarketingUnitSellProjectCollection(String oql) throws BOSException;
}