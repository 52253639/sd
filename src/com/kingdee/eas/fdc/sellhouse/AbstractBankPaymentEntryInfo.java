package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBankPaymentEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBankPaymentEntryInfo()
    {
        this("id");
    }
    protected AbstractBankPaymentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 银行放款明细 's 放款单头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BankPaymentInfo getPayment()
    {
        return (com.kingdee.eas.fdc.sellhouse.BankPaymentInfo)get("payment");
    }
    public void setPayment(com.kingdee.eas.fdc.sellhouse.BankPaymentInfo item)
    {
        put("payment", item);
    }
    /**
     * Object: 银行放款明细 's 款项名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    /**
     * Object: 银行放款明细 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object:银行放款明细's 客户名称property 
     */
    public String getCustomerDisName()
    {
        return getString("customerDisName");
    }
    public void setCustomerDisName(String item)
    {
        setString("customerDisName", item);
    }
    /**
     * Object:银行放款明细's 贷款金额property 
     */
    public java.math.BigDecimal getAppAmount()
    {
        return getBigDecimal("appAmount");
    }
    public void setAppAmount(java.math.BigDecimal item)
    {
        setBigDecimal("appAmount", item);
    }
    /**
     * Object:银行放款明细's 放款金额property 
     */
    public java.math.BigDecimal getPaymentAmount()
    {
        return getBigDecimal("paymentAmount");
    }
    public void setPaymentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("paymentAmount", item);
    }
    /**
     * Object: 银行放款明细 's 签约合同 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getSignManager()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("signManager");
    }
    public void setSignManager(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("signManager", item);
    }
    /**
     * Object:银行放款明细's 收据号property 
     */
    public String getReceiptDisName()
    {
        return getString("receiptDisName");
    }
    public void setReceiptDisName(String item)
    {
        setString("receiptDisName", item);
    }
    /**
     * Object:银行放款明细's 发票号property 
     */
    public String getInvoiceDisName()
    {
        return getString("invoiceDisName");
    }
    public void setInvoiceDisName(String item)
    {
        setString("invoiceDisName", item);
    }
    /**
     * Object: 银行放款明细 's 售楼收款单明细 property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo getSheRevBill()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo)get("sheRevBill");
    }
    public void setSheRevBill(com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo item)
    {
        put("sheRevBill", item);
    }
    /**
     * Object: 银行放款明细 's 签约单明细 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo getSignPayList()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo)get("signPayList");
    }
    public void setSignPayList(com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo item)
    {
        put("signPayList", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A1A86B43");
    }
}