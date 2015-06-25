package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanGatherPayEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractProjectMonthPlanGatherPayEntryInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanGatherPayEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 未完成支付数据分录 's 项目月度付款计划汇总 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 未完成支付数据分录 's 付款申请单 property 
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
     * Object: 未完成支付数据分录 's 无文本合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getContractWithoutText()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("contractWithoutText");
    }
    public void setContractWithoutText(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("contractWithoutText", item);
    }
    /**
     * Object:未完成支付数据分录's 金额property 
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
     * Object: 未完成支付数据分录 's 预算项目 property 
     */
    public com.kingdee.eas.ma.budget.BgItemInfo getBgItem()
    {
        return (com.kingdee.eas.ma.budget.BgItemInfo)get("bgItem");
    }
    public void setBgItem(com.kingdee.eas.ma.budget.BgItemInfo item)
    {
        put("bgItem", item);
    }
    /**
     * Object:未完成支付数据分录's 已付金额property 
     */
    public java.math.BigDecimal getActPayAmount()
    {
        return getBigDecimal("actPayAmount");
    }
    public void setActPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("actPayAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9F5B2364");
    }
}