package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDynCostSnapShotInfo extends com.kingdee.eas.framework.ObjectBaseInfo implements Serializable 
{
    public AbstractDynCostSnapShotInfo()
    {
        this("id");
    }
    protected AbstractDynCostSnapShotInfo(String pkField)
    {
        super(pkField);
        put("settEntries", new com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryCollection());
        put("proTypEntries", new com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection());
    }
    /**
     * Object:动态成本快照's 工程项目IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getProjectId()
    {
        return getBOSUuid("projectId");
    }
    public void setProjectId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("projectId", item);
    }
    /**
     * Object:动态成本快照's 成本科目Idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getCostAccountId()
    {
        return getBOSUuid("costAccountId");
    }
    public void setCostAccountId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("costAccountId", item);
    }
    /**
     * Object:动态成本快照's 快照日期property 
     */
    public java.util.Date getSnapShotDate()
    {
        return getDate("snapShotDate");
    }
    public void setSnapShotDate(java.util.Date item)
    {
        setDate("snapShotDate", item);
    }
    /**
     * Object:动态成本快照's 未结算合同签约金额property 
     */
    public java.math.BigDecimal getUnSettSignAmt()
    {
        return getBigDecimal("unSettSignAmt");
    }
    public void setUnSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettSignAmt", item);
    }
    /**
     * Object:动态成本快照's 已结算合同签约金额property 
     */
    public java.math.BigDecimal getSettSignAmt()
    {
        return getBigDecimal("settSignAmt");
    }
    public void setSettSignAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settSignAmt", item);
    }
    /**
     * Object:动态成本快照's 结算调整property 
     */
    public java.math.BigDecimal getSettAdjustAmt()
    {
        return getBigDecimal("settAdjustAmt");
    }
    public void setSettAdjustAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settAdjustAmt", item);
    }
    /**
     * Object:动态成本快照's 非合同性成本property 
     */
    public java.math.BigDecimal getUnContractCostAmt()
    {
        return getBigDecimal("unContractCostAmt");
    }
    public void setUnContractCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unContractCostAmt", item);
    }
    /**
     * Object:动态成本快照's 目前已发生property 
     */
    public java.math.BigDecimal getSoFarHasAmt()
    {
        return getBigDecimal("soFarHasAmt");
    }
    public void setSoFarHasAmt(java.math.BigDecimal item)
    {
        setBigDecimal("soFarHasAmt", item);
    }
    /**
     * Object:动态成本快照's 目前待发生property 
     */
    public java.math.BigDecimal getSoFarToAmt()
    {
        return getBigDecimal("soFarToAmt");
    }
    public void setSoFarToAmt(java.math.BigDecimal item)
    {
        setBigDecimal("soFarToAmt", item);
    }
    /**
     * Object:动态成本快照's 动态成本property 
     */
    public java.math.BigDecimal getDynCostAmt()
    {
        return getBigDecimal("dynCostAmt");
    }
    public void setDynCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("dynCostAmt", item);
    }
    /**
     * Object:动态成本快照's 目标成本property 
     */
    public java.math.BigDecimal getAimCostAmt()
    {
        return getBigDecimal("aimCostAmt");
    }
    public void setAimCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("aimCostAmt", item);
    }
    /**
     * Object:动态成本快照's 差异property 
     */
    public java.math.BigDecimal getDiffAmt()
    {
        return getBigDecimal("diffAmt");
    }
    public void setDiffAmt(java.math.BigDecimal item)
    {
        setBigDecimal("diffAmt", item);
    }
    /**
     * Object:动态成本快照's 可售单方property 
     */
    public java.math.BigDecimal getSalableAmt()
    {
        return getBigDecimal("salableAmt");
    }
    public void setSalableAmt(java.math.BigDecimal item)
    {
        setBigDecimal("salableAmt", item);
    }
    /**
     * Object:动态成本快照's 建筑单方property 
     */
    public java.math.BigDecimal getConstrAmt()
    {
        return getBigDecimal("constrAmt");
    }
    public void setConstrAmt(java.math.BigDecimal item)
    {
        setBigDecimal("constrAmt", item);
    }
    /**
     * Object:动态成本快照's 分摊类型IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getApprotionTypeId()
    {
        return getBOSUuid("approtionTypeId");
    }
    public void setApprotionTypeId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("approtionTypeId", item);
    }
    /**
     * Object: 动态成本快照 's 结算分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryCollection getSettEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostSnapShotSettEntryCollection)get("settEntries");
    }
    /**
     * Object: 动态成本快照 's 产品类型分录 property 
     */
    public com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection getProTypEntries()
    {
        return (com.kingdee.eas.fdc.aimcost.DynCostSnapShotProTypEntryCollection)get("proTypEntries");
    }
    /**
     * Object:动态成本快照's 成本科目长编码property 
     */
    public String getCostAcctLgNumber()
    {
        return getString("costAcctLgNumber");
    }
    public void setCostAcctLgNumber(String item)
    {
        setString("costAcctLgNumber", item);
    }
    /**
     * Object:动态成本快照's 是否月结保存property 
     */
    public boolean isIsMonthSave()
    {
        return getBoolean("isMonthSave");
    }
    public void setIsMonthSave(boolean item)
    {
        setBoolean("isMonthSave", item);
    }
    /**
     * Object: 动态成本快照 's 期间 property 
     */
    public com.kingdee.eas.basedata.assistant.PeriodInfo getPeriod()
    {
        return (com.kingdee.eas.basedata.assistant.PeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.basedata.assistant.PeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object:动态成本快照's 是否已使用property 
     */
    public boolean isIsUsed()
    {
        return getBoolean("isUsed");
    }
    public void setIsUsed(boolean item)
    {
        setBoolean("isUsed", item);
    }
    /**
     * Object:动态成本快照's 快照保存类型property 
     */
    public com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum getSavedType()
    {
        return com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum.getEnum(getString("savedType"));
    }
    public void setSavedType(com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum item)
    {
		if (item != null) {
        setString("savedType", item.getValue());
		}
    }
    /**
     * Object:动态成本快照's 已实现产值property 
     */
    public java.math.BigDecimal getRealizedValue()
    {
        return getBigDecimal("realizedValue");
    }
    public void setRealizedValue(java.math.BigDecimal item)
    {
        setBigDecimal("realizedValue", item);
    }
    /**
     * Object:动态成本快照's 已实现成本property 
     */
    public java.math.BigDecimal getCostPayedAmt()
    {
        return getBigDecimal("costPayedAmt");
    }
    public void setCostPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costPayedAmt", item);
    }
    /**
     * Object:动态成本快照's 已付款金额property 
     */
    public java.math.BigDecimal getPayedAmt()
    {
        return getBigDecimal("payedAmt");
    }
    public void setPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmt", item);
    }
    /**
     * Object:动态成本快照's 未结算变更金额property 
     */
    public java.math.BigDecimal getUnSettleChangeAmt()
    {
        return getBigDecimal("unSettleChangeAmt");
    }
    public void setUnSettleChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("unSettleChangeAmt", item);
    }
    /**
     * Object:动态成本快照's 已结算变更金额property 
     */
    public java.math.BigDecimal getSettleChangeAmt()
    {
        return getBigDecimal("settleChangeAmt");
    }
    public void setSettleChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleChangeAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C2CE2BB3");
    }
}