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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IShareSellProject extends IDataBase
{
    public ShareSellProjectInfo getShareSellProjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ShareSellProjectInfo getShareSellProjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ShareSellProjectInfo getShareSellProjectInfo(String oql) throws BOSException, EASBizException;
    public ShareSellProjectCollection getShareSellProjectCollection() throws BOSException;
    public ShareSellProjectCollection getShareSellProjectCollection(EntityViewInfo view) throws BOSException;
    public ShareSellProjectCollection getShareSellProjectCollection(String oql) throws BOSException;
}