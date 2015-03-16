package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMoneyDefineInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractMoneyDefineInfo()
    {
        this("id");
    }
    protected AbstractMoneyDefineInfo(String pkField)
    {
        super(pkField);
        put("settlementTypeEntry", new com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryCollection());
    }
    /**
     * Object:收费项目's 款项类别property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum getMoneyType()
    {
        return com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum.getEnum(getString("moneyType"));
    }
    public void setMoneyType(com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum item)
    {
		if (item != null) {
        setString("moneyType", item.getValue());
		}
    }
    /**
     * Object: 收费项目 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: 收费项目 's 收款类型 property 
     */
    public com.kingdee.eas.fi.cas.ReceivingBillTypeInfo getRevBillType()
    {
        return (com.kingdee.eas.fi.cas.ReceivingBillTypeInfo)get("revBillType");
    }
    public void setRevBillType(com.kingdee.eas.fi.cas.ReceivingBillTypeInfo item)
    {
        put("revBillType", item);
    }
    /**
     * Object:收费项目's 所属系统属性property 
     */
    public com.kingdee.eas.fdc.basedata.MoneySysTypeEnum getSysType()
    {
        return com.kingdee.eas.fdc.basedata.MoneySysTypeEnum.getEnum(getString("sysType"));
    }
    public void setSysType(com.kingdee.eas.fdc.basedata.MoneySysTypeEnum item)
    {
		if (item != null) {
        setString("sysType", item.getValue());
		}
    }
    /**
     * Object:收费项目's 是否使用仪表property 
     */
    public boolean isIsMeterItem()
    {
        return getBoolean("isMeterItem");
    }
    public void setIsMeterItem(boolean item)
    {
        setBoolean("isMeterItem", item);
    }
    /**
     * Object: 收费项目 's 结算方式分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryCollection getSettlementTypeEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SettlementTypeEntryCollection)get("settlementTypeEntry");
    }
    /**
     * Object: 收费项目 's 父款项 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getParentMoneyDefine()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("parentMoneyDefine");
    }
    public void setParentMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("parentMoneyDefine", item);
    }
    /**
     * Object:收费项目's 是否是集团property 
     */
    public boolean isIsGroup()
    {
        return getBoolean("isGroup");
    }
    public void setIsGroup(boolean item)
    {
        setBoolean("isGroup", item);
    }
    /**
     * Object:收费项目's 长名称property 
     */
    public String getLongName()
    {
        return getString("longName");
    }
    public void setLongName(String item)
    {
        setString("longName", item);
    }
    /**
     * Object:收费项目's 是否可冲抵property 
     */
    public boolean isIsAmount()
    {
        return getBoolean("isAmount");
    }
    public void setIsAmount(boolean item)
    {
        setBoolean("isAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B8B0A8E0");
    }
}