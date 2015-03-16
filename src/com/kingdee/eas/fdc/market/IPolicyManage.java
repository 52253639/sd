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

public interface IPolicyManage extends ICoreBillBase
{
    public PolicyManageCollection getPolicyManageCollection() throws BOSException;
    public PolicyManageCollection getPolicyManageCollection(EntityViewInfo view) throws BOSException;
    public PolicyManageCollection getPolicyManageCollection(String oql) throws BOSException;
    public PolicyManageInfo getPolicyManageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PolicyManageInfo getPolicyManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PolicyManageInfo getPolicyManageInfo(String oql) throws BOSException, EASBizException;
}