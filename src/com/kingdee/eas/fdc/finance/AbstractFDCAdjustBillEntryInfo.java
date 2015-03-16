package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCAdjustBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractFDCAdjustBillEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCAdjustBillEntryInfo(String pkField)
    {
        super(pkField);
        put("productEntrys", new com.kingdee.eas.fdc.finance.FDCAdjustProductEntryCollection());
    }
    /**
     * Object:调整单分录's nullproperty 
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
     * Object: 调整单分录 's 父结点 property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCAdjustBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 调整单分录 's 会计科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getAccountView()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("accountView");
    }
    public void setAccountView(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("accountView", item);
    }
    /**
     * Object: 调整单分录 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.finance.FDCAdjustProductEntryCollection getProductEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCAdjustProductEntryCollection)get("productEntrys");
    }
    /**
     * Object:调整单分录's 调整前归属成本金额property 
     */
    public java.math.BigDecimal getPreHappenCostAmt()
    {
        return getBigDecimal("preHappenCostAmt");
    }
    public void setPreHappenCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("preHappenCostAmt", item);
    }
    /**
     * Object:调整单分录's 归属成本金额property 
     */
    public java.math.BigDecimal getHappenCostAmt()
    {
        return getBigDecimal("happenCostAmt");
    }
    public void setHappenCostAmt(java.math.BigDecimal item)
    {
        setBigDecimal("happenCostAmt", item);
    }
    /**
     * Object:调整单分录's 成本拆分金额property 
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
     * Object:调整单分录's 调整前归属付款金额property 
     */
    public java.math.BigDecimal getPrePayedAmt()
    {
        return getBigDecimal("prePayedAmt");
    }
    public void setPrePayedAmt(java.math.BigDecimal item)
    {
        setBigDecimal("prePayedAmt", item);
    }
    /**
     * Object:调整单分录's 归属付款金额property 
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
     * Object:调整单分录's 合同拆分金额property 
     */
    public java.math.BigDecimal getContractSplitAmt()
    {
        return getBigDecimal("contractSplitAmt");
    }
    public void setContractSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("contractSplitAmt", item);
    }
    /**
     * Object:调整单分录's 变更拆分金额property 
     */
    public java.math.BigDecimal getChangeSplitAmt()
    {
        return getBigDecimal("changeSplitAmt");
    }
    public void setChangeSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("changeSplitAmt", item);
    }
    /**
     * Object:调整单分录's 保修金拆分金额property 
     */
    public java.math.BigDecimal getGrtSplitAmt()
    {
        return getBigDecimal("grtSplitAmt");
    }
    public void setGrtSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("grtSplitAmt", item);
    }
    /**
     * Object:调整单分录's 成本金额差额property 
     */
    public java.math.BigDecimal getCostDifAmt()
    {
        return getBigDecimal("costDifAmt");
    }
    public void setCostDifAmt(java.math.BigDecimal item)
    {
        setBigDecimal("costDifAmt", item);
    }
    /**
     * Object:调整单分录's 付款金额差额property 
     */
    public java.math.BigDecimal getPayedDifAmt()
    {
        return getBigDecimal("payedDifAmt");
    }
    public void setPayedDifAmt(java.math.BigDecimal item)
    {
        setBigDecimal("payedDifAmt", item);
    }
    /**
     * Object:调整单分录's 结算拆分金额property 
     */
    public java.math.BigDecimal getSettleSplitAmt()
    {
        return getBigDecimal("settleSplitAmt");
    }
    public void setSettleSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("settleSplitAmt", item);
    }
    /**
     * Object: 调整单分录 's 成本科目 property 
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
     * Object: 调整单分录 's 工程项目 property 
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
     * Object:调整单分录's 是否页子节点property 
     */
    public boolean isIsLeaf()
    {
        return getBoolean("isLeaf");
    }
    public void setIsLeaf(boolean item)
    {
        setBoolean("isLeaf", item);
    }
    /**
     * Object:调整单分录's 调整前保修金拆分金额property 
     */
    public java.math.BigDecimal getPreGrtSplitAmt()
    {
        return getBigDecimal("preGrtSplitAmt");
    }
    public void setPreGrtSplitAmt(java.math.BigDecimal item)
    {
        setBigDecimal("preGrtSplitAmt", item);
    }
    /**
     * Object:调整单分录's 调整前归属保修金金额property 
     */
    public java.math.BigDecimal getPreQualityAmt()
    {
        return getBigDecimal("preQualityAmt");
    }
    public void setPreQualityAmt(java.math.BigDecimal item)
    {
        setBigDecimal("preQualityAmt", item);
    }
    /**
     * Object:调整单分录's 归属保修金金额property 
     */
    public java.math.BigDecimal getQualityAmt()
    {
        return getBigDecimal("qualityAmt");
    }
    public void setQualityAmt(java.math.BigDecimal item)
    {
        setBigDecimal("qualityAmt", item);
    }
    /**
     * Object:调整单分录's 保修金金额差额property 
     */
    public java.math.BigDecimal getQualityDifAmt()
    {
        return getBigDecimal("qualityDifAmt");
    }
    public void setQualityDifAmt(java.math.BigDecimal item)
    {
        setBigDecimal("qualityDifAmt", item);
    }
    /**
     * Object:调整单分录's 应付进度款差额property 
     */
    public java.math.BigDecimal getShouldPayDifAmt()
    {
        return getBigDecimal("shouldPayDifAmt");
    }
    public void setShouldPayDifAmt(java.math.BigDecimal item)
    {
        setBigDecimal("shouldPayDifAmt", item);
    }
    /**
     * Object:调整单分录's 应付保修款差额property 
     */
    public java.math.BigDecimal getShouldQualityDifAmt()
    {
        return getBigDecimal("shouldQualityDifAmt");
    }
    public void setShouldQualityDifAmt(java.math.BigDecimal item)
    {
        setBigDecimal("shouldQualityDifAmt", item);
    }
    /**
     * Object: 调整单分录 's 发票科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getInvoiceAcct()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("invoiceAcct");
    }
    public void setInvoiceAcct(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("invoiceAcct", item);
    }
    /**
     * Object:调整单分录's 发票金额property 
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
     * Object:调整单分录's 发票金额差额property 
     */
    public java.math.BigDecimal getInvoiceDifAmt()
    {
        return getBigDecimal("invoiceDifAmt");
    }
    public void setInvoiceDifAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceDifAmt", item);
    }
    /**
     * Object:调整单分录's 调整前发票金额property 
     */
    public java.math.BigDecimal getPreInvoiceAmt()
    {
        return getBigDecimal("preInvoiceAmt");
    }
    public void setPreInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("preInvoiceAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("18143306");
    }
}