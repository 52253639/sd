package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import java.util.Map;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryCollection;
import java.util.Set;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;



public abstract class AbstractFDCBudgetAcctFacadeControllerBean extends AbstractBizControllerBean implements FDCBudgetAcctFacadeController
{
    protected AbstractFDCBudgetAcctFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("94DADFC8");
    }

    public void autoUpdateContractPayPlan(Context ctx, String budgetId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("790cfbee-011c-1000-e000-0038c0a810e4"), new Object[]{ctx, budgetId});
            invokeServiceBefore(svcCtx);
            _autoUpdateContractPayPlan(ctx, budgetId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _autoUpdateContractPayPlan(Context ctx, String budgetId) throws BOSException, EASBizException;

    public Map getContractInfos(Context ctx, Set idSet) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("30f470c4-011c-1000-e000-0023c0a810e4"), new Object[]{ctx, idSet});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getContractInfos(ctx, idSet);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getContractInfos(Context ctx, Set idSet) throws BOSException, EASBizException;

    public void bindToContract(Context ctx, String contractId, FDCBudgetAcctEntryCollection budgetAcctEntrys) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("790cfbee-011c-1000-e000-0039c0a810e4"), new Object[]{ctx, contractId, budgetAcctEntrys});
            invokeServiceBefore(svcCtx);
            _bindToContract(ctx, contractId, budgetAcctEntrys);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _bindToContract(Context ctx, String contractId, IObjectCollection budgetAcctEntrys) throws BOSException;

    public Map getAssociateAcctPlan(Context ctx, String prjId, String contractId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("790cfbee-011c-1000-e000-003ac0a810e4"), new Object[]{ctx, prjId, contractId, period});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAssociateAcctPlan(ctx, prjId, contractId, period);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getAssociateAcctPlan(Context ctx, String prjId, String contractId, IObjectValue period) throws BOSException, EASBizException;

    public Map getAssociateAcctPay(Context ctx, String payReqId, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("790cfbee-011c-1000-e000-003dc0a810e4"), new Object[]{ctx, payReqId, period});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAssociateAcctPay(ctx, payReqId, period);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getAssociateAcctPay(Context ctx, String payReqId, IObjectValue period) throws BOSException, EASBizException;

    public Map getAssociateBudget(Context ctx, String payReqID, FDCBudgetPeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("601f0c95-43c8-4f26-9a25-48cf2e92fb38"), new Object[]{ctx, payReqID, period});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getAssociateBudget(ctx, payReqID, period);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getAssociateBudget(Context ctx, String payReqID, IObjectValue period) throws BOSException, EASBizException;

    public Map invokeBudgetCtrl(Context ctx, PayRequestBillInfo payReqInfo, String state) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("8e603784-011c-1000-e000-0003c0a810e4"), new Object[]{ctx, payReqInfo, state});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_invokeBudgetCtrl(ctx, payReqInfo, state);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _invokeBudgetCtrl(Context ctx, IObjectValue payReqInfo, String state) throws BOSException, EASBizException;

    public void updateMonthBudget(Context ctx, BOSUuid billId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("22b12fe6-3b80-4461-a969-1b2a9739ef88"), new Object[]{ctx, billId});
            invokeServiceBefore(svcCtx);
            _updateMonthBudget(ctx, billId);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateMonthBudget(Context ctx, BOSUuid billId) throws BOSException, EASBizException;

}