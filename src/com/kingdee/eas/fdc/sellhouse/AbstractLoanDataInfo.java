package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractLoanDataInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractLoanDataInfo()
    {
        this("id");
    }
    protected AbstractLoanDataInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection());
    }
    /**
     * Object: 按揭资料 's 按揭银行 property 
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
     * Object: 按揭资料 's 销售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 按揭资料 's 按揭币别 property 
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
     * Object:按揭资料's 按揭年限property 
     */
    public int getLoanFixedYear()
    {
        return getInt("loanFixedYear");
    }
    public void setLoanFixedYear(int item)
    {
        setInt("loanFixedYear", item);
    }
    /**
     * Object:按揭资料's 保证金property 
     */
    public java.math.BigDecimal getDepositAmount()
    {
        return getBigDecimal("depositAmount");
    }
    public void setDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("depositAmount", item);
    }
    /**
     * Object:按揭资料's 按揭额度property 
     */
    public java.math.BigDecimal getLoanLine()
    {
        return getBigDecimal("loanLine");
    }
    public void setLoanLine(java.math.BigDecimal item)
    {
        setBigDecimal("loanLine", item);
    }
    /**
     * Object:按揭资料's 目前使用property 
     */
    public java.math.BigDecimal getUsedAmount()
    {
        return getBigDecimal("usedAmount");
    }
    public void setUsedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("usedAmount", item);
    }
    /**
     * Object:按揭资料's 银行联系人property 
     */
    public String getContactPerson()
    {
        return getString("contactPerson");
    }
    public void setContactPerson(String item)
    {
        setString("contactPerson", item);
    }
    /**
     * Object:按揭资料's 联系电话property 
     */
    public String getContactPhone()
    {
        return getString("contactPhone");
    }
    public void setContactPhone(String item)
    {
        setString("contactPhone", item);
    }
    /**
     * Object: 按揭资料 's 按揭银行利率设置 property 
     */
    public com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection)get("entrys");
    }
    /**
     * Object:按揭资料's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:按揭资料's 是否启用property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D9698C95");
    }
}