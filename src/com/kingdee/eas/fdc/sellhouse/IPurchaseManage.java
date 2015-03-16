package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.metadata.entity.FilterInfo;
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

public interface IPurchaseManage extends IBaseTransaction
{
    public PurchaseManageInfo getPurchaseManageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PurchaseManageInfo getPurchaseManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PurchaseManageInfo getPurchaseManageInfo(String oql) throws BOSException, EASBizException;
    public PurchaseManageCollection getPurchaseManageCollection() throws BOSException;
    public PurchaseManageCollection getPurchaseManageCollection(EntityViewInfo view) throws BOSException;
    public PurchaseManageCollection getPurchaseManageCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
}