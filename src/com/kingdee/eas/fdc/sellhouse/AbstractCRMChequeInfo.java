package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCRMChequeInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractCRMChequeInfo()
    {
        this("id");
    }
    protected AbstractCRMChequeInfo(String pkField)
    {
        super(pkField);
        put("chequeDetailEntry", new com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection());
        put("sellProjectEntry", new com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection());
    }
    /**
     * Object:票据管理's 票据批次号property 
     */
    public String getChequeBathNumber()
    {
        return getString("chequeBathNumber");
    }
    public void setChequeBathNumber(String item)
    {
        setString("chequeBathNumber", item);
    }
    /**
     * Object: 票据管理 's 所属项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection getSellProjectEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeSellProjectEntryCollection)get("sellProjectEntry");
    }
    /**
     * Object:票据管理's 票据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum getChequeType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.getEnum(getString("chequeType"));
    }
    public void setChequeType(com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum item)
    {
		if (item != null) {
        setString("chequeType", item.getValue());
		}
    }
    /**
     * Object: 票据管理 's 币别 property 
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
     * Object: 票据管理 's 保管人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getKeeper()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("keeper");
    }
    public void setKeeper(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("keeper", item);
    }
    /**
     * Object: 票据管理 's 保管组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getKeepOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("keepOrgUnit");
    }
    public void setKeepOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("keepOrgUnit", item);
    }
    /**
     * Object:票据管理's 票据限额property 
     */
    public java.math.BigDecimal getLimitAmount()
    {
        return getBigDecimal("limitAmount");
    }
    public void setLimitAmount(java.math.BigDecimal item)
    {
        setBigDecimal("limitAmount", item);
    }
    /**
     * Object:票据管理's 摘要property 
     */
    public String getResume()
    {
        return getString("resume");
    }
    public void setResume(String item)
    {
        setString("resume", item);
    }
    /**
     * Object:票据管理's 张数property 
     */
    public int getNumberOfCheque()
    {
        return getInt("numberOfCheque");
    }
    public void setNumberOfCheque(int item)
    {
        setInt("numberOfCheque", item);
    }
    /**
     * Object:票据管理's 起始流水号property 
     */
    public int getSerialNumber()
    {
        return getInt("serialNumber");
    }
    public void setSerialNumber(int item)
    {
        setInt("serialNumber", item);
    }
    /**
     * Object:票据管理's 票据编码规则property 
     */
    public String getCodeRule()
    {
        return getString("codeRule");
    }
    public void setCodeRule(String item)
    {
        setString("codeRule", item);
    }
    /**
     * Object: 票据管理 's 票据明细  property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection getChequeDetailEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeDetailEntryCollection)get("chequeDetailEntry");
    }
    /**
     * Object:票据管理's 项目组合字段property 
     */
    public String getSellProjectNumbers()
    {
        return getString("sellProjectNumbers");
    }
    public void setSellProjectNumbers(String item)
    {
        setString("sellProjectNumbers", item);
    }
    /**
     * Object:票据管理's 项目名称property 
     */
    public String getSellProjectNames()
    {
        return getString("sellProjectNames");
    }
    public void setSellProjectNames(String item)
    {
        setString("sellProjectNames", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EF7E1D24");
    }
}