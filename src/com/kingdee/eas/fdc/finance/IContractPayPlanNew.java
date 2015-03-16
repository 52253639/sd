package com.kingdee.eas.fdc.finance;

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
import com.kingdee.bos.util.BOSUuid;

public interface IContractPayPlanNew extends IBillBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public ContractPayPlanNewInfo getContractPayPlanNewInfo(IObjectPK pk) throws BOSException, EASBizException;
    public ContractPayPlanNewInfo getContractPayPlanNewInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public ContractPayPlanNewInfo getContractPayPlanNewInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(ContractPayPlanNewInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, ContractPayPlanNewInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, ContractPayPlanNewInfo model) throws BOSException, EASBizException;
    public void updatePartial(ContractPayPlanNewInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, ContractPayPlanNewInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public ContractPayPlanNewCollection getContractPayPlanNewCollection() throws BOSException;
    public ContractPayPlanNewCollection getContractPayPlanNewCollection(EntityViewInfo view) throws BOSException;
    public ContractPayPlanNewCollection getContractPayPlanNewCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public String approveSucc(ContractPayPlanNewInfo model) throws BOSException;
    public boolean submitBill(ContractPayPlanNewInfo model) throws BOSException;
    public boolean auditBill(ContractPayPlanNewInfo model) throws BOSException;
    public boolean unauditBill(ContractPayPlanNewInfo model) throws BOSException;
    public boolean isFinal(BOSUuid bosId) throws BOSException;
}