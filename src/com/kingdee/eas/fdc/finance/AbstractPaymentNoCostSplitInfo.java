package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPaymentNoCostSplitInfo extends com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillInfo implements Serializable 
{
    public AbstractPaymentNoCostSplitInfo()
    {
        this("id");
    }
    protected AbstractPaymentNoCostSplitInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection());
        put("voucherEntrys", new com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherCollection());
    }
    /**
     * Object: 付款非成本科目拆分 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.PaymentNoCostSplitEntryCollection)get("entrys");
    }
    /**
     * Object: 付款非成本科目拆分 's 付款单 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    /**
     * Object:付款非成本科目拆分's 已完工工程量金额property 
     */
    public java.math.BigDecimal getCompletePrjAmt()
    {
        return getBigDecimal("completePrjAmt");
    }
    public void setCompletePrjAmt(java.math.BigDecimal item)
    {
        setBigDecimal("completePrjAmt", item);
    }
    /**
     * Object:付款非成本科目拆分's 是否是无文本合同property 
     */
    public boolean isIsConWithoutText()
    {
        return getBoolean("isConWithoutText");
    }
    public void setIsConWithoutText(boolean item)
    {
        setBoolean("isConWithoutText", item);
    }
    /**
     * Object:付款非成本科目拆分's 是否第一次结算property 
     */
    public boolean isIsProgress()
    {
        return getBoolean("isProgress");
    }
    public void setIsProgress(boolean item)
    {
        setBoolean("isProgress", item);
    }
    /**
     * Object: 付款非成本科目拆分 's 预提科目 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beAccount");
    }
    public void setBeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beAccount", item);
    }
    /**
     * Object: 付款非成本科目拆分 's 历史工程项目状态 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectStatusInfo getHisStatus()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectStatusInfo)get("hisStatus");
    }
    public void setHisStatus(com.kingdee.eas.fdc.basedata.ProjectStatusInfo item)
    {
        put("hisStatus", item);
    }
    /**
     * Object: 付款非成本科目拆分 's 凭证 property 
     */
    public com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherCollection getVoucherEntrys()
    {
        return (com.kingdee.eas.fdc.finance.PayNoCostSplit4VoucherCollection)get("voucherEntrys");
    }
    /**
     * Object:付款非成本科目拆分's 生成凭证次数property 
     */
    public int getVoucherTimes()
    {
        return getInt("voucherTimes");
    }
    public void setVoucherTimes(int item)
    {
        setInt("voucherTimes", item);
    }
    /**
     * Object: 付款非成本科目拆分 's 历史凭证 property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getHisVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("hisVoucher");
    }
    public void setHisVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("hisVoucher", item);
    }
    /**
     * Object:付款非成本科目拆分's 保修款property 
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
     * Object:付款非成本科目拆分's 应付金额property 
     */
    public java.math.BigDecimal getPayAmount()
    {
        return getBigDecimal("payAmount");
    }
    public void setPayAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payAmount", item);
    }
    /**
     * Object: 付款非成本科目拆分 's 合同基础资料 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBaseDataInfo getContractBaseData()
    {
        return (com.kingdee.eas.fdc.contract.ContractBaseDataInfo)get("contractBaseData");
    }
    public void setContractBaseData(com.kingdee.eas.fdc.contract.ContractBaseDataInfo item)
    {
        put("contractBaseData", item);
    }
    /**
     * Object:付款非成本科目拆分's 是否已经红冲property 
     */
    public boolean isIsRedVouchered()
    {
        return getBoolean("isRedVouchered");
    }
    public void setIsRedVouchered(boolean item)
    {
        setBoolean("isRedVouchered", item);
    }
    /**
     * Object:付款非成本科目拆分's 是否需要中转property 
     */
    public boolean isIsNeedTransit()
    {
        return getBoolean("isNeedTransit");
    }
    public void setIsNeedTransit(boolean item)
    {
        setBoolean("isNeedTransit", item);
    }
    /**
     * Object: 付款非成本科目拆分 's 中转科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getTransitAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("transitAccount");
    }
    public void setTransitAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("transitAccount", item);
    }
    /**
     * Object: 付款非成本科目拆分 's 无文本合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWithoutTextInfo getConWithoutText()
    {
        return (com.kingdee.eas.fdc.contract.ContractWithoutTextInfo)get("conWithoutText");
    }
    public void setConWithoutText(com.kingdee.eas.fdc.contract.ContractWithoutTextInfo item)
    {
        put("conWithoutText", item);
    }
    /**
     * Object:付款非成本科目拆分's 是否被付款单引用作凭证property 
     */
    public boolean isIsUsedByPayment()
    {
        return getBoolean("isUsedByPayment");
    }
    public void setIsUsedByPayment(boolean item)
    {
        setBoolean("isUsedByPayment", item);
    }
    /**
     * Object:付款非成本科目拆分's 生成凭证的引用单据property 
     */
    public com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum getVoucherRefer()
    {
        return com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum.getEnum(getString("voucherRefer"));
    }
    public void setVoucherRefer(com.kingdee.eas.fdc.finance.PaySplitVoucherRefersEnum item)
    {
		if (item != null) {
        setString("voucherRefer", item.getValue());
		}
    }
    /**
     * Object:付款非成本科目拆分's 凭证引用的源单IDproperty 
     */
    public String getVoucherReferId()
    {
        return getString("voucherReferId");
    }
    public void setVoucherReferId(String item)
    {
        setString("voucherReferId", item);
    }
    /**
     * Object:付款非成本科目拆分's 是否是出调整单前的付款拆分property 
     */
    public boolean isIsBeforeAdjust()
    {
        return getBoolean("isBeforeAdjust");
    }
    public void setIsBeforeAdjust(boolean item)
    {
        setBoolean("isBeforeAdjust", item);
    }
    /**
     * Object:付款非成本科目拆分's 是否关闭property 
     */
    public boolean isIsClosed()
    {
        return getBoolean("isClosed");
    }
    public void setIsClosed(boolean item)
    {
        setBoolean("isClosed", item);
    }
    /**
     * Object:付款非成本科目拆分's 发票金额property 
     */
    public java.math.BigDecimal getInvoiceAmt()
    {
        return getBigDecimal("invoiceAmt");
    }
    public void setInvoiceAmt(java.math.BigDecimal item)
    {
        setBigDecimal("invoiceAmt", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD7F63F5");
    }
}