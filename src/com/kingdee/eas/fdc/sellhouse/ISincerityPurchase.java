package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ISincerityPurchase extends IBaseTransaction
{
    public SincerityPurchaseInfo getSincerityPurchaseInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SincerityPurchaseInfo getSincerityPurchaseInfo(String oql) throws BOSException, EASBizException;
    public SincerityPurchaseInfo getSincerityPurchaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SincerityPurchaseCollection getSincerityPurchaseCollection() throws BOSException;
    public SincerityPurchaseCollection getSincerityPurchaseCollection(EntityViewInfo view) throws BOSException;
    public SincerityPurchaseCollection getSincerityPurchaseCollection(String oql) throws BOSException;
    public void submitSincer(IObjectCollection sincerityColl) throws BOSException, EASBizException;
    public void quitNum(IObjectValue sinPurInfo, IObjectCollection selectorCollection) throws BOSException, EASBizException;
}