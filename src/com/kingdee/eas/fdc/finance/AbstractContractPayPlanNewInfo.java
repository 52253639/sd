package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractPayPlanNewInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractContractPayPlanNewInfo()
    {
        this("id");
    }
    protected AbstractContractPayPlanNewInfo(String pkField)
    {
        super(pkField);
        put("PayByStage", new com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageCollection());
        put("Plam", new com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamCollection());
        put("PlanMingxi", new com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiCollection());
    }
    /**
     * Object:合同付款计划(新版)'s nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    /**
     * Object:合同付款计划(新版)'s 版本名称property 
     */
    public String getCopyName()
    {
        return getString("CopyName");
    }
    public void setCopyName(String item)
    {
        setString("CopyName", item);
    }
    /**
     * Object:合同付款计划(新版)'s 合同签约金额property 
     */
    public java.math.BigDecimal getSignAmount()
    {
        return getBigDecimal("SignAmount");
    }
    public void setSignAmount(java.math.BigDecimal item)
    {
        setBigDecimal("SignAmount", item);
    }
    /**
     * Object:合同付款计划(新版)'s 合同最新造价property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("ContractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("ContractPrice", item);
    }
    /**
     * Object:合同付款计划(新版)'s 审批人property 
     */
    public String getAuditPerson()
    {
        return getString("AuditPerson");
    }
    public void setAuditPerson(String item)
    {
        setString("AuditPerson", item);
    }
    /**
     * Object:合同付款计划(新版)'s 审批时间property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("AuditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("AuditTime", item);
    }
    /**
     * Object:合同付款计划(新版)'s 保修比例property 
     */
    public java.math.BigDecimal getMadeRate()
    {
        return getBigDecimal("MadeRate");
    }
    public void setMadeRate(java.math.BigDecimal item)
    {
        setBigDecimal("MadeRate", item);
    }
    /**
     * Object:合同付款计划(新版)'s 进度款比例property 
     */
    public java.math.BigDecimal getPayRate()
    {
        return getBigDecimal("PayRate");
    }
    public void setPayRate(java.math.BigDecimal item)
    {
        setBigDecimal("PayRate", item);
    }
    /**
     * Object:合同付款计划(新版)'s 是否按形象进度付款property 
     */
    public boolean isProgressPayout()
    {
        return getBoolean("ProgressPayout");
    }
    public void setProgressPayout(boolean item)
    {
        setBoolean("ProgressPayout", item);
    }
    /**
     * Object:合同付款计划(新版)'s 合同编号property 
     */
    public String getContractCd()
    {
        return getString("ContractCd");
    }
    public void setContractCd(String item)
    {
        setString("ContractCd", item);
    }
    /**
     * Object:合同付款计划(新版)'s 单据状态property 
     */
    public String getStatus()
    {
        return getString("Status");
    }
    public void setStatus(String item)
    {
        setString("Status", item);
    }
    /**
     * Object: 合同付款计划(新版) 's  property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageCollection getPayByStage()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageCollection)get("PayByStage");
    }
    /**
     * Object: 合同付款计划(新版) 's null property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamCollection getPlam()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewPlamCollection)get("Plam");
    }
    /**
     * Object: 合同付款计划(新版) 's 计划明细 property 
     */
    public com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiCollection getPlanMingxi()
    {
        return (com.kingdee.eas.fdc.finance.ContractPayPlanNewPlanMingxiCollection)get("PlanMingxi");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("736E01F0");
    }
}