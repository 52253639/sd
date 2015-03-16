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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;

public interface IMarketSplServiceType extends IFDCTreeBaseData
{
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSplServiceTypeInfo getMarketSplServiceTypeInfo(String oql) throws BOSException, EASBizException;
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection(String oql) throws BOSException;
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection() throws BOSException;
    public MarketSplServiceTypeCollection getMarketSplServiceTypeCollection(EntityViewInfo view) throws BOSException;
}