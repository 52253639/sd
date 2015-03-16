package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestAcctPayInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractPayRequestAcctPayInfo()
    {
        this("id");
    }
    protected AbstractPayRequestAcctPayInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:付款申请单关联科目付款's 项目IDproperty 
     */
    public String getProjectId()
    {
        return getString("projectId");
    }
    public void setProjectId(String item)
    {
        setString("projectId", item);
    }
    /**
     * Object:付款申请单关联科目付款's 合同IDproperty 
     */
    public String getContractId()
    {
        return getString("contractId");
    }
    public void setContractId(String item)
    {
        setString("contractId", item);
    }
    /**
     * Object:付款申请单关联科目付款's 本次申请金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: 付款申请单关联科目付款 's 付款申请单 property 
     */
    public com.kingdee.eas.fdc.contract.PayRequestBillInfo getPayRequestBill()
    {
        return (com.kingdee.eas.fdc.contract.PayRequestBillInfo)get("payRequestBill");
    }
    public void setPayRequestBill(com.kingdee.eas.fdc.contract.PayRequestBillInfo item)
    {
        put("payRequestBill", item);
    }
    /**
     * Object: 付款申请单关联科目付款 's 成本科目 property 
     */
    public com.kingdee.eas.fdc.basedata.CostAccountInfo getCostAccount()
    {
        return (com.kingdee.eas.fdc.basedata.CostAccountInfo)get("costAccount");
    }
    public void setCostAccount(com.kingdee.eas.fdc.basedata.CostAccountInfo item)
    {
        put("costAccount", item);
    }
    /**
     * Object: 付款申请单关联科目付款 's 期间 property 
     */
    public com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo getPeriod()
    {
        return (com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo)get("period");
    }
    public void setPeriod(com.kingdee.eas.fdc.finance.FDCBudgetPeriodInfo item)
    {
        put("period", item);
    }
    /**
     * Object:付款申请单关联科目付款's 已请款金额property 
     */
    public java.math.BigDecimal getLstAllAmount()
    {
        return getBigDecimal("lstAllAmount");
    }
    public void setLstAllAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lstAllAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CB024B9F");
    }
}