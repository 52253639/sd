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
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IInviteMonthPlan extends IFDCBill
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public InviteMonthPlanInfo getInviteMonthPlanInfo(IObjectPK pk) throws BOSException, EASBizException;
    public InviteMonthPlanInfo getInviteMonthPlanInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public InviteMonthPlanInfo getInviteMonthPlanInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(InviteMonthPlanInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, InviteMonthPlanInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, InviteMonthPlanInfo model) throws BOSException, EASBizException;
    public void updatePartial(InviteMonthPlanInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, InviteMonthPlanInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public InviteMonthPlanCollection getInviteMonthPlanCollection() throws BOSException;
    public InviteMonthPlanCollection getInviteMonthPlanCollection(EntityViewInfo view) throws BOSException;
    public InviteMonthPlanCollection getInviteMonthPlanCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public Map checkLeaf(BOSUuid billId) throws BOSException, EASBizException;
}