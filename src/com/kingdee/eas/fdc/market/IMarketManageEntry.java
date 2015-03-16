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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketManageEntry extends IBillEntryBase
{
    public MarketManageEntryInfo getMarketManageEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketManageEntryInfo getMarketManageEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketManageEntryInfo getMarketManageEntryInfo(String oql) throws BOSException, EASBizException;
    public MarketManageEntryCollection getMarketManageEntryCollection() throws BOSException;
    public MarketManageEntryCollection getMarketManageEntryCollection(EntityViewInfo view) throws BOSException;
    public MarketManageEntryCollection getMarketManageEntryCollection(String oql) throws BOSException;
}