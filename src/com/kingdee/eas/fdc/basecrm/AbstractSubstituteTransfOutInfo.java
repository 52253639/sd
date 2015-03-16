package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSubstituteTransfOutInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSubstituteTransfOutInfo()
    {
        this("id");
    }
    protected AbstractSubstituteTransfOutInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection());
    }
    /**
     * Object: 代收费用转出 's 分录 property 
     */
    public com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.basecrm.SubstituteTransfOutEntryCollection)get("entrys");
    }
    /**
     * Object: 代收费用转出 's 销售组织 property 
     */
    public com.kingdee.eas.basedata.org.SaleOrgUnitInfo getSaleOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.SaleOrgUnitInfo)get("saleOrgUnit");
    }
    public void setSaleOrgUnit(com.kingdee.eas.basedata.org.SaleOrgUnitInfo item)
    {
        put("saleOrgUnit", item);
    }
    /**
     * Object: 代收费用转出 's 项目 property 
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
     * Object: 代收费用转出 's 款项名称 property 
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
     * Object:代收费用转出's 支付金额property 
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
     * Object:代收费用转出's 收款人property 
     */
    public String getRevUserName()
    {
        return getString("revUserName");
    }
    public void setRevUserName(String item)
    {
        setString("revUserName", item);
    }
    /**
     * Object: 代收费用转出 's 收款银行 property 
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
     * Object: 代收费用转出 's 付款银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getPayBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("payBank");
    }
    public void setPayBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("payBank", item);
    }
    /**
     * Object:代收费用转出's 收款帐号property 
     */
    public String getRevAccountNumber()
    {
        return getString("revAccountNumber");
    }
    public void setRevAccountNumber(String item)
    {
        setString("revAccountNumber", item);
    }
    /**
     * Object: 代收费用转出 's 付款帐号 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getPayAccountNumber()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("payAccountNumber");
    }
    public void setPayAccountNumber(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("payAccountNumber", item);
    }
    /**
     * Object: 代收费用转出 's 收款科目 property 
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
     * Object: 代收费用转出 's 对方科目 property 
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
     * Object: 代收费用转出 's 结算方式 property 
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
     * Object: 代收费用转出 's 币别 property 
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
     * Object:代收费用转出's 汇率property 
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
     * Object:代收费用转出's 审批日期property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object:代收费用转出's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object: 代收费用转出 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getBuilding()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("building");
    }
    public void setBuilding(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("building", item);
    }
    /**
     * Object: 代收费用转出 's 付款人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPayUser()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("payUser");
    }
    public void setPayUser(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("payUser", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("57618B5A");
    }
}