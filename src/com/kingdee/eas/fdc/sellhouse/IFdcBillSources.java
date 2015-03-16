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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public interface IFdcBillSources extends IDataBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FdcBillSourcesInfo getFdcBillSourcesInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FdcBillSourcesInfo getFdcBillSourcesInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FdcBillSourcesInfo getFdcBillSourcesInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FdcBillSourcesInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FdcBillSourcesInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FdcBillSourcesInfo model) throws BOSException, EASBizException;
    public void updatePartial(FdcBillSourcesInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FdcBillSourcesInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FdcBillSourcesCollection getFdcBillSourcesCollection() throws BOSException;
    public FdcBillSourcesCollection getFdcBillSourcesCollection(EntityViewInfo view) throws BOSException;
    public FdcBillSourcesCollection getFdcBillSourcesCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}