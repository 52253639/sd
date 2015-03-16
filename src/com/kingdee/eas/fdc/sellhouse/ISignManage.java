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
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface ISignManage extends IBaseTransaction
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public SignManageInfo getSignManageInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SignManageInfo getSignManageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SignManageInfo getSignManageInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(SignManageInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, SignManageInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, SignManageInfo model) throws BOSException, EASBizException;
    public void updatePartial(SignManageInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, SignManageInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public SignManageCollection getSignManageCollection() throws BOSException;
    public SignManageCollection getSignManageCollection(EntityViewInfo view) throws BOSException;
    public SignManageCollection getSignManageCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void onRecord(BOSUuid id, Date date, String contractNumber) throws BOSException, EASBizException;
    public void unOnRecord(BOSUuid id) throws BOSException, EASBizException;
}