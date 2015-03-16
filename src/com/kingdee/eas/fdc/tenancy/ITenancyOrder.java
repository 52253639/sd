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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITenancyOrder extends ITenBillBase
{
    public TenancyOrderInfo getTenancyOrderInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenancyOrderInfo getTenancyOrderInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenancyOrderInfo getTenancyOrderInfo(String oql) throws BOSException, EASBizException;
    public TenancyOrderCollection getTenancyOrderCollection() throws BOSException;
    public TenancyOrderCollection getTenancyOrderCollection(EntityViewInfo view) throws BOSException;
    public TenancyOrderCollection getTenancyOrderCollection(String oql) throws BOSException;
}