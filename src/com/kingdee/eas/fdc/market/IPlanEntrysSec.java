package com.kingdee.eas.fdc.market;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IBillEntryBase;

public interface IPlanEntrysSec extends IBillEntryBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public PlanEntrysSecInfo getPlanEntrysSecInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PlanEntrysSecInfo getPlanEntrysSecInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PlanEntrysSecInfo getPlanEntrysSecInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(PlanEntrysSecInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, PlanEntrysSecInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, PlanEntrysSecInfo model) throws BOSException, EASBizException;
    public void updatePartial(PlanEntrysSecInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, PlanEntrysSecInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public PlanEntrysSecCollection getPlanEntrysSecCollection() throws BOSException;
    public PlanEntrysSecCollection getPlanEntrysSecCollection(EntityViewInfo view) throws BOSException;
    public PlanEntrysSecCollection getPlanEntrysSecCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}