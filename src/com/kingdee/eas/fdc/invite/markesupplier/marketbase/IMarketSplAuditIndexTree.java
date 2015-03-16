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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IMarketSplAuditIndexTree extends ITreeBase
{
    public MarketSplAuditIndexTreeInfo getMarketSplAuditIndexTreeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSplAuditIndexTreeInfo getMarketSplAuditIndexTreeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSplAuditIndexTreeInfo getMarketSplAuditIndexTreeInfo(String oql) throws BOSException, EASBizException;
    public MarketSplAuditIndexTreeCollection getMarketSplAuditIndexTreeCollection() throws BOSException;
    public MarketSplAuditIndexTreeCollection getMarketSplAuditIndexTreeCollection(EntityViewInfo view) throws BOSException;
    public MarketSplAuditIndexTreeCollection getMarketSplAuditIndexTreeCollection(String oql) throws BOSException;
}