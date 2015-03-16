package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IFeesWarrant extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FeesWarrantInfo getFeesWarrantInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FeesWarrantInfo getFeesWarrantInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FeesWarrantInfo getFeesWarrantInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FeesWarrantInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FeesWarrantInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FeesWarrantInfo model) throws BOSException, EASBizException;
    public void updatePartial(FeesWarrantInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FeesWarrantInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FeesWarrantCollection getFeesWarrantCollection() throws BOSException;
    public FeesWarrantCollection getFeesWarrantCollection(EntityViewInfo view) throws BOSException;
    public FeesWarrantCollection getFeesWarrantCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public boolean account(BOSUuid tenBillOtherPayID) throws BOSException, EASBizException;
}