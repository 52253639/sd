package com.kingdee.eas.fdc.finance;

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
import java.util.Set;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;

public interface IFDCBudgetAcctFacade extends IBizCtrl
{
    public void autoUpdateContractPayPlan(String budgetId) throws BOSException, EASBizException;
    public Map getContractInfos(Set idSet) throws BOSException, EASBizException;
    public void bindToContract(String contractId, FDCBudgetAcctEntryCollection budgetAcctEntrys) throws BOSException;
    public Map getAssociateAcctPlan(String prjId, String contractId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;
    public Map getAssociateAcctPay(String payReqId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;
    public Map getAssociateBudget(String payReqID, FDCBudgetPeriodInfo period) throws BOSException, EASBizException;
    public Map invokeBudgetCtrl(PayRequestBillInfo payReqInfo, String state) throws BOSException, EASBizException;
    public void updateMonthBudget(BOSUuid billId) throws BOSException, EASBizException;
}