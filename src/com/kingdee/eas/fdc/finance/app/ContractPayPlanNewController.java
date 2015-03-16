package com.kingdee.eas.fdc.finance.app;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.app.BillBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractPayPlanNewController extends BillBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ContractPayPlanNewInfo getContractPayPlanNewInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractPayPlanNewInfo getContractPayPlanNewInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractPayPlanNewInfo getContractPayPlanNewInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, ContractPayPlanNewInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, ContractPayPlanNewInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, ContractPayPlanNewInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, ContractPayPlanNewInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, ContractPayPlanNewInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public ContractPayPlanNewCollection getContractPayPlanNewCollection(Context ctx) throws BOSException, RemoteException;
    public ContractPayPlanNewCollection getContractPayPlanNewCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractPayPlanNewCollection getContractPayPlanNewCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public String approveSucc(Context ctx, ContractPayPlanNewInfo model) throws BOSException, RemoteException;
    public boolean submitBill(Context ctx, ContractPayPlanNewInfo model) throws BOSException, RemoteException;
    public boolean auditBill(Context ctx, ContractPayPlanNewInfo model) throws BOSException, RemoteException;
    public boolean unauditBill(Context ctx, ContractPayPlanNewInfo model) throws BOSException, RemoteException;
    public boolean isFinal(Context ctx, BOSUuid bosId) throws BOSException, RemoteException;
}