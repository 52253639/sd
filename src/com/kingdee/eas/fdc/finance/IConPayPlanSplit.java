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
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.IFDCSplitBill;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IConPayPlanSplit extends IFDCSplitBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ConPayPlanSplitInfo getConPayPlanSplitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ConPayPlanSplitInfo getConPayPlanSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ConPayPlanSplitInfo getConPayPlanSplitInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(ConPayPlanSplitInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, ConPayPlanSplitInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, ConPayPlanSplitInfo model) throws BOSException, EASBizException;
    public void updatePartial(ConPayPlanSplitInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, ConPayPlanSplitInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public ConPayPlanSplitCollection getConPayPlanSplitCollection() throws BOSException;
    public ConPayPlanSplitCollection getConPayPlanSplitCollection(EntityViewInfo view) throws BOSException;
    public ConPayPlanSplitCollection getConPayPlanSplitCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void autoSplit(String billId) throws BOSException, EASBizException;
    public Map getCostAcctPlan(String prjId, Date date) throws BOSException, EASBizException;
}