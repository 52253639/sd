package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.framework.ICoreBase;

public interface IShareSaleMan extends ICoreBase
{
    public ShareSaleManInfo getShareSaleManInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ShareSaleManInfo getShareSaleManInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ShareSaleManInfo getShareSaleManInfo(String oql) throws BOSException, EASBizException;
    public ShareSaleManCollection getShareSaleManCollection() throws BOSException;
    public ShareSaleManCollection getShareSaleManCollection(EntityViewInfo view) throws BOSException;
    public ShareSaleManCollection getShareSaleManCollection(String oql) throws BOSException;
}