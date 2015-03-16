package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractReceiveGatherInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractReceiveGatherInfo()
    {
        this("id");
    }
    protected AbstractReceiveGatherInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryCollection());
    }
    /**
     * Object: 出纳汇总单 's 汇总明细 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ReceiveGatherEntryCollection)get("entry");
    }
    /**
     * Object: 出纳汇总单 's 项目信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 出纳汇总单 's 入账银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("bank");
    }
    public void setBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("bank", item);
    }
    /**
     * Object: 出纳汇总单 's 入账银行账户 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("accountBank");
    }
    public void setAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("accountBank", item);
    }
    /**
     * Object:出纳汇总单's 收款单单据类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RevBillTypeEnum getRevBillType()
    {
        return com.kingdee.eas.fdc.basecrm.RevBillTypeEnum.getEnum(getString("revBillType"));
    }
    public void setRevBillType(com.kingdee.eas.fdc.basecrm.RevBillTypeEnum item)
    {
		if (item != null) {
        setString("revBillType", item.getValue());
		}
    }
    /**
     * Object:出纳汇总单's 汇总金额property 
     */
    public java.math.BigDecimal getSumAmount()
    {
        return getBigDecimal("sumAmount");
    }
    public void setSumAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sumAmount", item);
    }
    /**
     * Object: 出纳汇总单 's 结算方式 property 
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
     * Object:出纳汇总单's 结算单号property 
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
     * Object: 出纳汇总单 's 收款科目 property 
     */
    public com.kingdee.eas.basedata.master.account.AccountViewInfo getRevAccount()
    {
        return (com.kingdee.eas.basedata.master.account.AccountViewInfo)get("revAccount");
    }
    public void setRevAccount(com.kingdee.eas.basedata.master.account.AccountViewInfo item)
    {
        put("revAccount", item);
    }
    /**
     * Object: 出纳汇总单 's 对方科目 property 
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
     * Object:出纳汇总单's 汇总类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.GatherTypeEnum getGatherType()
    {
        return com.kingdee.eas.fdc.sellhouse.GatherTypeEnum.getEnum(getString("gatherType"));
    }
    public void setGatherType(com.kingdee.eas.fdc.sellhouse.GatherTypeEnum item)
    {
		if (item != null) {
        setString("gatherType", item.getValue());
		}
    }
    /**
     * Object:出纳汇总单's 是否生成凭证property 
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
     * Object:出纳汇总单's 是否生成出纳单据property 
     */
    public boolean isFiRevOrPay()
    {
        return getBoolean("fiRevOrPay");
    }
    public void setFiRevOrPay(boolean item)
    {
        setBoolean("fiRevOrPay", item);
    }
    /**
     * Object: 出纳汇总单 's 公司 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompany()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("company");
    }
    public void setCompany(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("company", item);
    }
    /**
     * Object: 出纳汇总单 's 币别 property 
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
     * Object:出纳汇总单's 汇率property 
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
     * Object: 出纳汇总单 's 出纳收款单 property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillInfo getReceivingBill()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillInfo)get("receivingBill");
    }
    public void setReceivingBill(com.kingdee.eas.fi.cas.ReceivingBillInfo item)
    {
        put("receivingBill", item);
    }
    /**
     * Object: 出纳汇总单 's 出纳付款单 property 
     */
    public com.kingdee.eas.fi.cas.PaymentBillInfo getPaymentBill()
    {
        return (com.kingdee.eas.fi.cas.PaymentBillInfo)get("paymentBill");
    }
    public void setPaymentBill(com.kingdee.eas.fi.cas.PaymentBillInfo item)
    {
        put("paymentBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("15549BC3");
    }
}