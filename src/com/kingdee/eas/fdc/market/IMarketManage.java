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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketManage extends IBillBase
{
    public MarketManageInfo getMarketManageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketManageInfo getMarketManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketManageInfo getMarketManageInfo(String oql) throws BOSException, EASBizException;
    public MarketManageCollection getMarketManageCollection() throws BOSException;
    public MarketManageCollection getMarketManageCollection(EntityViewInfo view) throws BOSException;
    public MarketManageCollection getMarketManageCollection(String oql) throws BOSException;
}