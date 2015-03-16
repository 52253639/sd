package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentNoCostSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo implements Serializable 
{
    public AbstractPaymentNoCostSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractPaymentNoCostSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款非成本科目拆分分录 's 父 property 
     */
    public com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 合同签约金额property 
     */
    public java.math.BigDecimal getContractAmt()
    {
        return getBigDecimal("contractAmt");
    }
    public void setContractAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 合同变更金额property 
     */
    public java.math.BigDecimal getChangeAmt()
    {
        return getBigDecimal("changeAmt");
    }
    public void setChangeAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 成本拆分金额property 
     */
    public java.math.BigDecimal getCostAmt()
    {
        return getBigDecimal("costAmt");
    }
    public void setCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 已拆分成本金额property 
     */
    public java.math.BigDecimal getSplitedCostAmt()
    {
        return getBigDecimal("splitedCostAmt");
    }
    public void setSplitedCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitedCostAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 直接金额property 
     */
    public java.math.BigDecimal getDirectAmt()
    {
        return getBigDecimal("directAmt");
    }
    public void setDirectAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 归属付款金额property 
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
     * Object:付款非成本科目拆分分录's 保修金property 
     */
    public java.math.BigDecimal getQualityAmount()
    {
        return getBigDecimal("qualityAmount");
    }
    public void setQualityAmount(java.math.BigDecimal item)
    {
        setBigDecimal("qualityAmount", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 已拆分付款金额property 
     */
    public java.math.BigDecimal getSplitedPayedAmt()
    {
        return getBigDecimal("splitedPayedAmt");
    }
    public void setSplitedPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitedPayedAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 应付进度款property 
     */
    public java.math.BigDecimal getShouldPayAmt()
    {
        return getBigDecimal("shouldPayAmt");
    }
    public void setShouldPayAmt(java.math.BigDecimal item)
    {
        setBigDecimal("shouldPayAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 已拆分保修金额property 
     */
    public java.math.BigDecimal getSplitQualityAmt()
    {
        return getBigDecimal("splitQualityAmt");
    }
    public void setSplitQualityAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitQualityAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 归属发票金额property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 直接发票金额property 
     */
    public java.math.BigDecimal getDirectInvoiceAmt()
    {
        return getBigDecimal("directInvoiceAmt");
    }
    public void setDirectInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directInvoiceAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 已拆分发票金额property 
     */
    public java.math.BigDecimal getSplitedInvoiceAmt()
    {
        return getBigDecimal("splitedInvoiceAmt");
    }
    public void setSplitedInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("splitedInvoiceAmt", item);
    }
    /**
     * Object:付款非成本科目拆分分录's 直接付款金额property 
     */
    public java.math.BigDecimal getDirectPayedAmt()
    {
        return getBigDecimal("directPayedAmt");
    }
    public void setDirectPayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("directPayedAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6FB9E57D");
    }
}