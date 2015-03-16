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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketType extends ITreeBase
{
    public MarketTypeInfo getMarketTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketTypeInfo getMarketTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketTypeInfo getMarketTypeInfo(String oql) throws BOSException, EASBizException;
    public MarketTypeCollection getMarketTypeCollection() throws BOSException;
    public MarketTypeCollection getMarketTypeCollection(EntityViewInfo view) throws BOSException;
    public MarketTypeCollection getMarketTypeCollection(String oql) throws BOSException;
}