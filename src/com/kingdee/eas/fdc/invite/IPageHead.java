package com.kingdee.eas.fdc.invite;

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

public interface IPageHead extends IDataBase
{
    public PageHeadInfo getPageHeadInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PageHeadInfo getPageHeadInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PageHeadInfo getPageHeadInfo(String oql) throws BOSException, EASBizException;
    public PageHeadCollection getPageHeadCollection() throws BOSException;
    public PageHeadCollection getPageHeadCollection(EntityViewInfo view) throws BOSException;
    public PageHeadCollection getPageHeadCollection(String oql) throws BOSException;
}