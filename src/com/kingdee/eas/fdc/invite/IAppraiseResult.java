package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IAppraiseResult extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public AppraiseResultInfo getAppraiseResultInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AppraiseResultInfo getAppraiseResultInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AppraiseResultInfo getAppraiseResultInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(AppraiseResultInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, AppraiseResultInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, AppraiseResultInfo model) throws BOSException, EASBizException;
    public void updatePartial(AppraiseResultInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, AppraiseResultInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public AppraiseResultCollection getAppraiseResultCollection() throws BOSException;
    public AppraiseResultCollection getAppraiseResultCollection(EntityViewInfo view) throws BOSException;
    public AppraiseResultCollection getAppraiseResultCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public Map fetchInitData(Map param) throws BOSException, EASBizException;
    public Set getInviteProjectId() throws BOSException, EASBizException;
}