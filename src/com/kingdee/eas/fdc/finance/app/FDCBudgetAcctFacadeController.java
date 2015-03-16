package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryCollection;
import java.util.Set;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCBudgetAcctFacadeController extends BizController
{
    public void autoUpdateContractPayPlan(Context ctx, String budgetId) throws BOSException, EASBizException, RemoteException;
    public Map getContractInfos(Context ctx, Set idSet) throws BOSException, EASBizException, RemoteException;
    public void bindToContract(Context ctx, String contractId, FDCBudgetAcctEntryCollection budgetAcctEntrys) throws BOSException, RemoteException;
    public Map getAssociateAcctPlan(Context ctx, String prjId, String contractId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public Map getAssociateAcctPay(Context ctx, String payReqId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public Map getAssociateBudget(Context ctx, String payReqID, FDCBudgetPeriodInfo period) throws BOSException, EASBizException, RemoteException;
    public Map invokeBudgetCtrl(Context ctx, PayRequestBillInfo payReqInfo, String state) throws BOSException, EASBizException, RemoteException;
    public void updateMonthBudget(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
}