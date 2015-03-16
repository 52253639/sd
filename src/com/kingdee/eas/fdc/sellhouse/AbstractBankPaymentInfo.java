package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBankPaymentInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractBankPaymentInfo()
    {
        this("id");
    }
    protected AbstractBankPaymentInfo(String pkField)
    {
        super(pkField);
        put("bankPaymentEntry", new com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection());
    }
    /**
     * Object: 银行放款 's 项目 property 
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
     * Object: 银行放款 's 贷款银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getPaymentBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("paymentBank");
    }
    public void setPaymentBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("paymentBank", item);
    }
    /**
     * Object: 银行放款 's 贷款银行账户 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getPaymentAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("paymentAccountBank");
    }
    public void setPaymentAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("paymentAccountBank", item);
    }
    /**
     * Object: 银行放款 's 结算方式 property 
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
     * Object:银行放款's 结算单号property 
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
     * Object: 银行放款 's 入账银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getRevBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("revBank");
    }
    public void setRevBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("revBank", item);
    }
    /**
     * Object: 银行放款 's 入账银行账户 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getRevAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("revAccountBank");
    }
    public void setRevAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("revAccountBank", item);
    }
    /**
     * Object:银行放款's 放款日期property 
     */
    public java.util.Date getPaymentDate()
    {
        return getDate("paymentDate");
    }
    public void setPaymentDate(java.util.Date item)
    {
        setDate("paymentDate", item);
    }
    /**
     * Object:银行放款's 放款总额property 
     */
    public java.math.BigDecimal getPaymentAmout()
    {
        return getBigDecimal("paymentAmout");
    }
    public void setPaymentAmout(java.math.BigDecimal item)
    {
        setBigDecimal("paymentAmout", item);
    }
    /**
     * Object: 银行放款 's 收款科目 property 
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
     * Object: 银行放款 's 对方科目 property 
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
     * Object: 银行放款 's 放款明细分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection getBankPaymentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.BankPaymentEntryCollection)get("bankPaymentEntry");
    }
    /**
     * Object: 银行放款 's 出纳汇总单 property 
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
     * Object: 银行放款 's 放款款项 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyDefine");
    }
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyDefine", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1FD9DA6F");
    }
}