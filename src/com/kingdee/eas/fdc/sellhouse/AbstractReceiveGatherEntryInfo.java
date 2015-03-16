package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReceiveGatherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractReceiveGatherEntryInfo()
    {
        this("id");
    }
    protected AbstractReceiveGatherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 出纳汇总单明细 's 出纳汇总单头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo getReceiveGather()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo)get("receiveGather");
    }
    public void setReceiveGather(com.kingdee.eas.fdc.sellhouse.ReceiveGatherInfo item)
    {
        put("receiveGather", item);
    }
    /**
     * Object: 出纳汇总单明细 's 款项类型 property 
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
     * Object: 出纳汇总单明细 's 房间 property 
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
     * Object:出纳汇总单明细's 客户名称property 
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
     * Object: 出纳汇总单明细 's 结算方式 property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:出纳汇总单明细's 结算单号property 
     */
    public String getSettlementNumber()
    {
        return getString("settlementNumber");
    }
    public void setSettlementNumber(String item)
    {
        setString("settlementNumber", item);
    }
    /**
     * Object:出纳汇总单明细's 收付金额property 
     */
    public java.math.BigDecimal getRevAmount()
    {
        return getBigDecimal("revAmount");
    }
    public void setRevAmount(java.math.BigDecimal item)
    {
        setBigDecimal("revAmount", item);
    }
    /**
     * Object: 出纳汇总单明细 's 客户银行账户 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getCusAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("cusAccountBank");
    }
    public void setCusAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("cusAccountBank", item);
    }
    /**
     * Object:出纳汇总单明细's 客户银行账号property 
     */
    public String getCusAccountBankNumber()
    {
        return getString("cusAccountBankNumber");
    }
    public void setCusAccountBankNumber(String item)
    {
        setString("cusAccountBankNumber", item);
    }
    /**
     * Object: 出纳汇总单明细 's 收款明细 property 
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
     * Object: 出纳汇总单明细 's 出纳收款单分录 property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillEntryInfo getReceivingBillEntry()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillEntryInfo)get("receivingBillEntry");
    }
    public void setReceivingBillEntry(com.kingdee.eas.fi.cas.ReceivingBillEntryInfo item)
    {
        put("receivingBillEntry", item);
    }
    /**
     * Object: 出纳汇总单明细 's 对方科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getOppAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("oppAccount");
    }
    public void setOppAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("oppAccount", item);
    }
    /**
     * Object:出纳汇总单明细's 收据号property 
     */
    public String getReceiptNumber()
    {
        return getString("receiptNumber");
    }
    public void setReceiptNumber(String item)
    {
        setString("receiptNumber", item);
    }
    /**
     * Object:出纳汇总单明细's 发票号property 
     */
    public String getInvoiceNumber()
    {
        return getString("invoiceNumber");
    }
    public void setInvoiceNumber(String item)
    {
        setString("invoiceNumber", item);
    }
    /**
     * Object:出纳汇总单明细's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EF155E6F");
    }
}