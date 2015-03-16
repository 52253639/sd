package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractSettlementBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractSettlementBillInfo()
    {
        this("id");
    }
    protected AbstractContractSettlementBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:合同结算单's 是否生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object:合同结算单's 结算造价property 
     */
    public java.math.BigDecimal getSettlePrice()
    {
        return getBigDecimal("settlePrice");
    }
    public void setSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("settlePrice", item);
    }
    /**
     * Object:合同结算单's 建筑面积property 
     */
    public java.math.BigDecimal getBuildArea()
    {
        return getBigDecimal("buildArea");
    }
    public void setBuildArea(java.math.BigDecimal item)
    {
        setBigDecimal("buildArea", item);
    }
    /**
     * Object:合同结算单's 取费标准property 
     */
    public String getGetFeeCriteria()
    {
        return getString("getFeeCriteria");
    }
    public void setGetFeeCriteria(String item)
    {
        setString("getFeeCriteria", item);
    }
    /**
     * Object:合同结算单's 单位造价property 
     */
    public java.math.BigDecimal getUnitPrice()
    {
        return getBigDecimal("unitPrice");
    }
    public void setUnitPrice(java.math.BigDecimal item)
    {
        setBigDecimal("unitPrice", item);
    }
    /**
     * Object:合同结算单's 信息价property 
     */
    public String getInfoPrice()
    {
        return getString("infoPrice");
    }
    public void setInfoPrice(String item)
    {
        setString("infoPrice", item);
    }
    /**
     * Object:合同结算单's 质保金property 
     */
    public java.math.BigDecimal getQualityGuarante()
    {
        return getBigDecimal("qualityGuarante");
    }
    public void setQualityGuarante(java.math.BigDecimal item)
    {
        setBigDecimal("qualityGuarante", item);
    }
    /**
     * Object:合同结算单's 是否最终结算property 
     */
    public com.kingdee.eas.base.commonquery.BooleanEnum getIsFinalSettle()
    {
        return com.kingdee.eas.base.commonquery.BooleanEnum.getEnum(getInt("isFinalSettle"));
    }
    public void setIsFinalSettle(com.kingdee.eas.base.commonquery.BooleanEnum item)
    {
		if (item != null) {
        setInt("isFinalSettle", item.getValue());
		}
    }
    /**
     * Object: 合同结算单 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContractBill()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contractBill");
    }
    public void setContractBill(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contractBill", item);
    }
    /**
     * Object: 合同结算单 's 凭证 property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object:合同结算单's 合同编码property 
     */
    public String getContractBillNumber()
    {
        return getString("contractBillNumber");
    }
    public void setContractBillNumber(String item)
    {
        setString("contractBillNumber", item);
    }
    /**
     * Object:合同结算单's 保修金比例property 
     */
    public java.math.BigDecimal getQualityGuaranteRate()
    {
        return getBigDecimal("qualityGuaranteRate");
    }
    public void setQualityGuaranteRate(java.math.BigDecimal item)
    {
        setBigDecimal("qualityGuaranteRate", item);
    }
    /**
     * Object:合同结算单's 保修期限property 
     */
    public String getQualityTime()
    {
        return getString("qualityTime");
    }
    public void setQualityTime(String item)
    {
        setString("qualityTime", item);
    }
    /**
     * Object: 合同结算单 's  property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: 合同结算单 's 币别 property 
     */
    public com.kingdee.eas.basedata.assistant.CurrencyInfo getCurrency()
    {
        return (com.kingdee.eas.basedata.assistant.CurrencyInfo)get("currency");
    }
    public void setCurrency(com.kingdee.eas.basedata.assistant.CurrencyInfo item)
    {
        put("currency", item);
    }
    /**
     * Object:合同结算单's 汇率property 
     */
    public java.math.BigDecimal getExchangeRate()
    {
        return getBigDecimal("exchangeRate");
    }
    public void setExchangeRate(java.math.BigDecimal item)
    {
        setBigDecimal("exchangeRate", item);
    }
    /**
     * Object:合同结算单's 单据来源方式property 
     */
    public com.kingdee.eas.fdc.basedata.SourceTypeEnum getSourceType()
    {
        return com.kingdee.eas.fdc.basedata.SourceTypeEnum.getEnum(getInt("sourceType"));
    }
    public void setSourceType(com.kingdee.eas.fdc.basedata.SourceTypeEnum item)
    {
		if (item != null) {
        setInt("sourceType", item.getValue());
		}
    }
    /**
     * Object:合同结算单's nullproperty 
     */
    public com.kingdee.eas.fdc.basedata.CostSplitStateEnum getSplitState()
    {
        return com.kingdee.eas.fdc.basedata.CostSplitStateEnum.getEnum(getString("splitState"));
    }
    public void setSplitState(com.kingdee.eas.fdc.basedata.CostSplitStateEnum item)
    {
		if (item != null) {
        setString("splitState", item.getValue());
		}
    }
    /**
     * Object:合同结算单's 是否进入动态成本property 
     */
    public boolean isIsCostSplit()
    {
        return getBoolean("isCostSplit");
    }
    public void setIsCostSplit(boolean item)
    {
        setBoolean("isCostSplit", item);
    }
    /**
     * Object:合同结算单's 累计结算原币property 
     */
    public java.math.BigDecimal getTotalOriginalAmount()
    {
        return getBigDecimal("totalOriginalAmount");
    }
    public void setTotalOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("totalOriginalAmount", item);
    }
    /**
     * Object:合同结算单's 累计结算本位币property 
     */
    public java.math.BigDecimal getTotalSettlePrice()
    {
        return getBigDecimal("totalSettlePrice");
    }
    public void setTotalSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("totalSettlePrice", item);
    }
    /**
     * Object:合同结算单's 当前结算价property 
     */
    public java.math.BigDecimal getCurOriginalAmount()
    {
        return getBigDecimal("curOriginalAmount");
    }
    public void setCurOriginalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("curOriginalAmount", item);
    }
    /**
     * Object:合同结算单's 当前结算价property 
     */
    public java.math.BigDecimal getCurSettlePrice()
    {
        return getBigDecimal("curSettlePrice");
    }
    public void setCurSettlePrice(java.math.BigDecimal item)
    {
        setBigDecimal("curSettlePrice", item);
    }
    /**
     * Object:合同结算单's 合同已结算property 
     */
    public boolean isIsSettled()
    {
        return getBoolean("isSettled");
    }
    public void setIsSettled(boolean item)
    {
        setBoolean("isSettled", item);
    }
    /**
     * Object:合同结算单's 保修金property 
     */
    public java.math.BigDecimal getGuaranteAmt()
    {
        return getBigDecimal("guaranteAmt");
    }
    public void setGuaranteAmt(java.math.BigDecimal item)
    {
        setBigDecimal("guaranteAmt", item);
    }
    /**
     * Object:合同结算单's 施工方报审价property 
     */
    public java.math.BigDecimal getConstructPrice()
    {
        return getBigDecimal("constructPrice");
    }
    public void setConstructPrice(java.math.BigDecimal item)
    {
        setBigDecimal("constructPrice", item);
    }
    /**
     * Object:合同结算单's 造价咨询机构审核造价property 
     */
    public java.math.BigDecimal getOrgAuditCost()
    {
        return getBigDecimal("orgAuditCost");
    }
    public void setOrgAuditCost(java.math.BigDecimal item)
    {
        setBigDecimal("orgAuditCost", item);
    }
    /**
     * Object:合同结算单's 成本审核部审核造价property 
     */
    public java.math.BigDecimal getCostDeptAuditCost()
    {
        return getBigDecimal("costDeptAuditCost");
    }
    public void setCostDeptAuditCost(java.math.BigDecimal item)
    {
        setBigDecimal("costDeptAuditCost", item);
    }
    /**
     * Object:合同结算单's 合同结算类型property 
     */
    public com.kingdee.eas.fdc.contract.ContractSettleTypeEnum getContractSettleType()
    {
        return com.kingdee.eas.fdc.contract.ContractSettleTypeEnum.getEnum(getString("contractSettleType"));
    }
    public void setContractSettleType(com.kingdee.eas.fdc.contract.ContractSettleTypeEnum item)
    {
		if (item != null) {
        setString("contractSettleType", item.getValue());
		}
    }
    /**
     * Object:合同结算单's 甲供材费用property 
     */
    public java.math.BigDecimal getJGC()
    {
        return getBigDecimal("JGC");
    }
    public void setJGC(java.math.BigDecimal item)
    {
        setBigDecimal("JGC", item);
    }
    /**
     * Object:合同结算单's 造价指标归集property 
     */
    public String getGJ()
    {
        return getString("GJ");
    }
    public void setGJ(String item)
    {
        setString("GJ", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1D604E7D");
    }
}