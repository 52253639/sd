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
import com.kingdee.eas.framework.ICoreBillBase;

public interface IMarketSurvey extends ICoreBillBase
{
    public MarketSurveyCollection getMarketSurveyCollection() throws BOSException;
    public MarketSurveyCollection getMarketSurveyCollection(EntityViewInfo view) throws BOSException;
    public MarketSurveyCollection getMarketSurveyCollection(String oql) throws BOSException;
    public MarketSurveyInfo getMarketSurveyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public MarketSurveyInfo getMarketSurveyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public MarketSurveyInfo getMarketSurveyInfo(String oql) throws BOSException, EASBizException;
}