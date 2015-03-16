package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.finance.ContractPayPlanInfo;
import com.kingdee.eas.framework.app.BillBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ContractPayPlanController extends BillBaseController
{
    public ContractPayPlanInfo getContractPayPlanInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ContractPayPlanInfo getContractPayPlanInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ContractPayPlanInfo getContractPayPlanInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ContractPayPlanCollection getContractPayPlanCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ContractPayPlanCollection getContractPayPlanCollection(Context ctx) throws BOSException, RemoteException;
    public ContractPayPlanCollection getContractPayPlanCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean submitCol(Context ctx, String contractId, IObjectCollection planCol) throws BOSException, EASBizException, RemoteException;
}