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
import com.kingdee.eas.framework.ICoreBillBase;

public interface ISpecialDiscount extends ICoreBillBase
{
    public SpecialDiscountCollection getSpecialDiscountCollection() throws BOSException;
    public SpecialDiscountCollection getSpecialDiscountCollection(EntityViewInfo view) throws BOSException;
    public SpecialDiscountCollection getSpecialDiscountCollection(String oql) throws BOSException;
    public SpecialDiscountInfo getSpecialDiscountInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SpecialDiscountInfo getSpecialDiscountInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SpecialDiscountInfo getSpecialDiscountInfo(String oql) throws BOSException, EASBizException;
    public void audit(BOSUuid billID) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billID) throws BOSException, EASBizException;
    public void auditing(BOSUuid billID) throws BOSException, EASBizException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(SpecialDiscountInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, SpecialDiscountInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, SpecialDiscountInfo model) throws BOSException, EASBizException;
    public void updatePartial(SpecialDiscountInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, SpecialDiscountInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}