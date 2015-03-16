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
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public interface ISaleBalance extends IObjectBase
{
    public SaleBalanceInfo getSaleBalanceInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SaleBalanceInfo getSaleBalanceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SaleBalanceInfo getSaleBalanceInfo(String oql) throws BOSException, EASBizException;
    public SaleBalanceCollection getSaleBalanceCollection() throws BOSException;
    public SaleBalanceCollection getSaleBalanceCollection(EntityViewInfo view) throws BOSException;
    public SaleBalanceCollection getSaleBalanceCollection(String oql) throws BOSException;
    public Date getLeatestEndDate(String sellProjectId) throws BOSException;
}