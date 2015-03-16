package com.kingdee.eas.fdc.aimcost.app;

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

import com.kingdee.eas.basedata.assistant.PeriodInfo;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import java.math.BigDecimal;
import java.util.Map;
import com.kingdee.eas.fdc.aimcost.ProductDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.ProductAimCostMap;
import com.kingdee.eas.fdc.aimcost.AimCostMap;
import com.kingdee.eas.fdc.aimcost.DynamicCostMap;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;



public abstract class AbstractFDCCostRptFacadeControllerBean extends AbstractBizControllerBean implements FDCCostRptFacadeController
{
    protected AbstractFDCCostRptFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("B118BCD5");
    }

    public AimCostMap getAimCost(Context ctx, String orgOrPrjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9b5e803e-0118-1000-e000-00e8c0a812a0"), new Object[]{ctx, orgOrPrjId, period});
            invokeServiceBefore(svcCtx);
            AimCostMap retValue = (AimCostMap)_getAimCost(ctx, orgOrPrjId, period);
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
    protected abstract AimCostMap _getAimCost(Context ctx, String orgOrPrjId, IObjectValue period) throws BOSException, EASBizException;

    public ProductAimCostMap getProductAimCost(Context ctx, String prjId, ProjectStageEnum indexProjectStage, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9b5e803e-0118-1000-e000-00f2c0a812a0"), new Object[]{ctx, prjId, indexProjectStage, period});
            invokeServiceBefore(svcCtx);
            ProductAimCostMap retValue = (ProductAimCostMap)_getProductAimCost(ctx, prjId, indexProjectStage, period);
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
    protected abstract ProductAimCostMap _getProductAimCost(Context ctx, String prjId, ProjectStageEnum indexProjectStage, IObjectValue period) throws BOSException, EASBizException;

    public FullDynamicCostMap getFullDynamicCost(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9b5e803e-0118-1000-e000-00f3c0a812a0"), new Object[]{ctx, prjId, period});
            invokeServiceBefore(svcCtx);
            FullDynamicCostMap retValue = (FullDynamicCostMap)_getFullDynamicCost(ctx, prjId, period);
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
    protected abstract FullDynamicCostMap _getFullDynamicCost(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException;

    public ProductDynamicCostMap getProductDynamicCost(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9b5e803e-0118-1000-e000-00f4c0a812a0"), new Object[]{ctx, prjId, period});
            invokeServiceBefore(svcCtx);
            ProductDynamicCostMap retValue = (ProductDynamicCostMap)_getProductDynamicCost(ctx, prjId, period);
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
    protected abstract ProductDynamicCostMap _getProductDynamicCost(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException;

    public Map submitAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a1ffe9aa-0118-1000-e000-0004c0a812a0"), new Object[]{ctx, costEntryMap});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_submitAimProductCost(ctx, costEntryMap);
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
    protected abstract Map _submitAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException;

    public Map submitAIMAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a1ffe9aa-0118-1000-e000-0007c0a812a0"), new Object[]{ctx, costEntryMap});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_submitAIMAimProductCost(ctx, costEntryMap);
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
    protected abstract Map _submitAIMAimProductCost(Context ctx, Map costEntryMap) throws BOSException, EASBizException;

    public Map submitDynProductCost(Context ctx, Map dynCostMap) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("abc45f97-0118-1000-e000-0006c0a812a0"), new Object[]{ctx, dynCostMap});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_submitDynProductCost(ctx, dynCostMap);
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
    protected abstract Map _submitDynProductCost(Context ctx, Map dynCostMap) throws BOSException, EASBizException;

    public FullDynamicCostMap getFullPrjDynamicCost(Context ctx, String projOrgId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("abc45f97-0118-1000-e000-000ac0a812a0"), new Object[]{ctx, projOrgId, period});
            invokeServiceBefore(svcCtx);
            FullDynamicCostMap retValue = (FullDynamicCostMap)_getFullPrjDynamicCost(ctx, projOrgId, period);
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
    protected abstract FullDynamicCostMap _getFullPrjDynamicCost(Context ctx, String projOrgId, IObjectValue period) throws BOSException, EASBizException;

    public void saveSnapShot(Context ctx, String prjId, CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("bb371e5c-0118-1000-e000-003fc0a812a0"), new Object[]{ctx, prjId, savedType});
            invokeServiceBefore(svcCtx);
            _saveSnapShot(ctx, prjId, savedType);
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
    protected abstract void _saveSnapShot(Context ctx, String prjId, CostMonthlySaveTypeEnum savedType) throws BOSException, EASBizException;

    public void autoSaveSnapShot(Context ctx) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c9d2dff6-0118-1000-e000-002fc0a812a0"), new Object[]{ctx});
            invokeServiceBefore(svcCtx);
            _autoSaveSnapShot(ctx);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _autoSaveSnapShot(Context ctx) throws BOSException;

    public DynamicCostMap getDynamicCost(Context ctx, String prjId, PeriodInfo period, boolean isLeaf) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0da1cb4f-0119-1000-e000-0001c0a812a0"), new Object[]{ctx, prjId, period, new Boolean(isLeaf)});
            invokeServiceBefore(svcCtx);
            DynamicCostMap retValue = (DynamicCostMap)_getDynamicCost(ctx, prjId, period, isLeaf);
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
    protected abstract DynamicCostMap _getDynamicCost(Context ctx, String prjId, IObjectValue period, boolean isLeaf) throws BOSException, EASBizException;

    public void reverseCostMonthSettled(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("27ed0d05-0119-1000-e000-0004c0a812a0"), new Object[]{ctx, prjId, period});
            invokeServiceBefore(svcCtx);
            _reverseCostMonthSettled(ctx, prjId, period);
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
    protected abstract void _reverseCostMonthSettled(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException;

    public void reverseFinanceMonthSettled(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("27ed0d05-0119-1000-e000-0008c0a812a0"), new Object[]{ctx, prjId, period});
            invokeServiceBefore(svcCtx);
            _reverseFinanceMonthSettled(ctx, prjId, period);
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
    protected abstract void _reverseFinanceMonthSettled(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException;

    public void deleteDynSnapShot(Context ctx, String prjId, PeriodInfo period) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("27ed0d05-0119-1000-e000-000bc0a812a0"), new Object[]{ctx, prjId, period});
            invokeServiceBefore(svcCtx);
            _deleteDynSnapShot(ctx, prjId, period);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _deleteDynSnapShot(Context ctx, String prjId, IObjectValue period) throws BOSException, EASBizException;

    public BigDecimal getAimCost(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70a41d48-0119-1000-e000-0007c0a812a0"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getAimCost(ctx, prjId);
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
    protected abstract BigDecimal _getAimCost(Context ctx, String prjId) throws BOSException, EASBizException;

    public BigDecimal getDynCost(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70a41d48-0119-1000-e000-0008c0a812a0"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getDynCost(ctx, prjId);
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
    protected abstract BigDecimal _getDynCost(Context ctx, String prjId) throws BOSException, EASBizException;

    public BigDecimal getHappendCost(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("70a41d48-0119-1000-e000-0009c0a812a0"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getHappendCost(ctx, prjId);
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
    protected abstract BigDecimal _getHappendCost(Context ctx, String prjId) throws BOSException, EASBizException;

    public BigDecimal getAdjustCost(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7413e99d-0119-1000-e000-0009c0a812a0"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            BigDecimal retValue = (BigDecimal)_getAdjustCost(ctx, prjId);
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
    protected abstract BigDecimal _getAdjustCost(Context ctx, String prjId) throws BOSException, EASBizException;

    public Map getCostAccountAimCost(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("97afff8d-0119-1000-e000-0003c0a812a0"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getCostAccountAimCost(ctx, prjId);
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
    protected abstract Map _getCostAccountAimCost(Context ctx, String prjId) throws BOSException, EASBizException;

    public Map getCostAccountDynCost(Context ctx, String prjId) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("97afff8d-0119-1000-e000-0006c0a812a0"), new Object[]{ctx, prjId});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getCostAccountDynCost(ctx, prjId);
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
    protected abstract Map _getCostAccountDynCost(Context ctx, String prjId) throws BOSException, EASBizException;

    public Map getCostMap(Context ctx, Map param) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3b29d284-79fc-4a90-abb4-782d081eede9"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getCostMap(ctx, param);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract Map _getCostMap(Context ctx, Map param) throws BOSException;

    public void updatePeriodCostPayedAmt(Context ctx, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("dcfc693a-011d-1000-e000-0012c0a810d7"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            _updatePeriodCostPayedAmt(ctx, param);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } catch (EASBizException ex0) {
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updatePeriodCostPayedAmt(Context ctx, Map param) throws BOSException, EASBizException;

    public Map getPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9bf3a3c6-e038-4489-9c9a-9ce149cb7747"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getPaymentCostStatistics(ctx, param);
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
    protected abstract Map _getPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException;

    public Map getNonLeafPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b62659da-58e2-4bae-921c-40ceab33b0f9"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getNonLeafPaymentCostStatistics(ctx, param);
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
    protected abstract Map _getNonLeafPaymentCostStatistics(Context ctx, Map param) throws BOSException, EASBizException;

    public Map getProjectAnalysisCost(Context ctx, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("7972035d-fd8b-407e-9693-713b874b853c"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getProjectAnalysisCost(ctx, param);
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
    protected abstract Map _getProjectAnalysisCost(Context ctx, Map param) throws BOSException, EASBizException;

    public Map getCostCloseData(Context ctx, boolean isCost, Map param) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9496ca5d-b76e-49eb-baa2-d7143d862d6b"), new Object[]{ctx, new Boolean(isCost), param});
            invokeServiceBefore(svcCtx);
            Map retValue = (Map)_getCostCloseData(ctx, isCost, param);
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
    protected abstract Map _getCostCloseData(Context ctx, boolean isCost, Map param) throws BOSException, EASBizException;

}