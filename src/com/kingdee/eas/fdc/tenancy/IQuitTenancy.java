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

public interface IQuitTenancy extends ITenBillBase
{
    public QuitTenancyInfo getQuitTenancyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public QuitTenancyInfo getQuitTenancyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public QuitTenancyInfo getQuitTenancyInfo(String oql) throws BOSException, EASBizException;
    public QuitTenancyCollection getQuitTenancyCollection() throws BOSException;
    public QuitTenancyCollection getQuitTenancyCollection(EntityViewInfo view) throws BOSException;
    public QuitTenancyCollection getQuitTenancyCollection(String oql) throws BOSException;
}