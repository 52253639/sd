package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import java.util.Map;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.Set;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;

public class FDCBudgetAcctFacade extends AbstractBizCtrl implements IFDCBudgetAcctFacade
{
    public FDCBudgetAcctFacade()
    {
        super();
        registerInterface(IFDCBudgetAcctFacade.class, this);
    }
    public FDCBudgetAcctFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBudgetAcctFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("94DADFC8");
    }
    private FDCBudgetAcctFacadeController getController() throws BOSException
    {
        return (FDCBudgetAcctFacadeController)getBizController();
    }
    /**
     *同步合同付款-User defined method
     *@param budgetId 月度申报表
     */
    public void autoUpdateContractPayPlan(String budgetId) throws BOSException, EASBizException
    {
        try {
            getController().autoUpdateContractPayPlan(getContext(), budgetId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同信息-User defined method
     *@param idSet 合同ID
     *@return
     */
    public Map getContractInfos(Set idSet) throws BOSException, EASBizException
    {
        try {
            return getController().getContractInfos(getContext(), idSet);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *预算条目绑定到合同-User defined method
     *@param contractId 合同ID
     *@param budgetAcctEntrys 预算条目
     */
    public void bindToContract(String contractId, FDCBudgetAcctEntryCollection budgetAcctEntrys) throws BOSException
    {
        try {
            getController().bindToContract(getContext(), contractId, budgetAcctEntrys);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *与合同拆分关联的预算条目-User defined method
     *@param prjId 项目ID
     *@param contractId 合同ID
     *@param period 房地产预算期间
     *@return
     */
    public Map getAssociateAcctPlan(String prjId, String contractId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAssociateAcctPlan(getContext(), prjId, contractId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *付款申请单关联科目取数方法-User defined method
     *@param payReqId 付款申请单ID
     *@param period 期间
     *@return
     */
    public Map getAssociateAcctPay(String payReqId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAssociateAcctPay(getContext(), payReqId, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *关联签定与待签定计划-User defined method
     *@param payReqID 付款申请单ID
     *@param period 预算期间
     *@return
     */
    public Map getAssociateBudget(String payReqID, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            return getController().getAssociateBudget(getContext(), payReqID, period);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *进行预算控制-User defined method
     *@param payReqInfo 付款申请单
     *@param state 新增/提交/审批/反审批/删除等状态
     *@return
     */
    public Map invokeBudgetCtrl(PayRequestBillInfo payReqInfo, String state) throws BOSException, EASBizException
    {
        try {
            return getController().invokeBudgetCtrl(getContext(), payReqInfo, state);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新项目月度申报-User defined method
     *@param billId billId
     */
    public void updateMonthBudget(BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            getController().updateMonthBudget(getContext(), billId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}