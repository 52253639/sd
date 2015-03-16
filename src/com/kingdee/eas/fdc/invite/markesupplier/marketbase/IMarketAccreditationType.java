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

public interface IMarketAccreditationType extends IDataBase
{
    public MarketAccreditationTypeInfo getMarketAccreditationTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketAccreditationTypeInfo getMarketAccreditationTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketAccreditationTypeInfo getMarketAccreditationTypeInfo(String oql) throws BOSException, EASBizException;
    public MarketAccreditationTypeCollection getMarketAccreditationTypeCollection() throws BOSException;
    public MarketAccreditationTypeCollection getMarketAccreditationTypeCollection(EntityViewInfo view) throws BOSException;
    public MarketAccreditationTypeCollection getMarketAccreditationTypeCollection(String oql) throws BOSException;
}