package com.kingdee.eas.fdc.finance;

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

public interface IFDCBudgetAcct extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public FDCBudgetAcctInfo getFDCBudgetAcctInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCBudgetAcctInfo getFDCBudgetAcctInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCBudgetAcctInfo getFDCBudgetAcctInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(FDCBudgetAcctInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, FDCBudgetAcctInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, FDCBudgetAcctInfo model) throws BOSException, EASBizException;
    public void updatePartial(FDCBudgetAcctInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, FDCBudgetAcctInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public FDCBudgetAcctCollection getFDCBudgetAcctCollection() throws BOSException;
    public FDCBudgetAcctCollection getFDCBudgetAcctCollection(EntityViewInfo view) throws BOSException;
    public FDCBudgetAcctCollection getFDCBudgetAcctCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public Map fetchData(Map param) throws BOSException, EASBizException;
    public Map fetchExecDate(Map param) throws BOSException, EASBizException;
}