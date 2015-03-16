package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IPropertyNotice extends IBillBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public PropertyNoticeInfo getPropertyNoticeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PropertyNoticeInfo getPropertyNoticeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PropertyNoticeInfo getPropertyNoticeInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(PropertyNoticeInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, PropertyNoticeInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, PropertyNoticeInfo model) throws BOSException, EASBizException;
    public void updatePartial(PropertyNoticeInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, PropertyNoticeInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public PropertyNoticeCollection getPropertyNoticeCollection() throws BOSException;
    public PropertyNoticeCollection getPropertyNoticeCollection(EntityViewInfo view) throws BOSException;
    public PropertyNoticeCollection getPropertyNoticeCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}