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

public interface IShareSeller extends IDataBase
{
    public ShareSellerInfo getShareSellerInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ShareSellerInfo getShareSellerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ShareSellerInfo getShareSellerInfo(String oql) throws BOSException, EASBizException;
    public ShareSellerCollection getShareSellerCollection() throws BOSException;
    public ShareSellerCollection getShareSellerCollection(EntityViewInfo view) throws BOSException;
    public ShareSellerCollection getShareSellerCollection(String oql) throws BOSException;
}