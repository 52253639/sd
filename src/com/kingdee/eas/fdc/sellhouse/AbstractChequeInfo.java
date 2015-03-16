package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChequeInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractChequeInfo()
    {
        this("id");
    }
    protected AbstractChequeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:票据's 票据类型property 
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
     * Object: 票据 's 币别 property 
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
     * Object: 票据 's 保管人 property 
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
     * Object: 票据 's 保管组织 property 
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
     * Object:票据's 票据状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum getStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum.getEnum(getInt("status"));
    }
    public void setStatus(com.kingdee.eas.fdc.sellhouse.ChequeStatusEnum item)
    {
		if (item != null) {
        setInt("status", item.getValue());
		}
    }
    /**
     * Object:票据's 付款人property 
     */
    public String getPayer()
    {
        return getString("payer");
    }
    public void setPayer(String item)
    {
        setString("payer", item);
    }
    /**
     * Object:票据's 付款日期property 
     */
    public java.sql.Timestamp getPayTime()
    {
        return getTimestamp("payTime");
    }
    public void setPayTime(java.sql.Timestamp item)
    {
        setTimestamp("payTime", item);
    }
    /**
     * Object:票据's 金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:票据's 大写金额property 
     */
    public String getCapitalization()
    {
        return getString("capitalization");
    }
    public void setCapitalization(String item)
    {
        setString("capitalization", item);
    }
    /**
     * Object:票据's 票据限额property 
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
     * Object: 票据 's 填开人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getWrittenOffer()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("writtenOffer");
    }
    public void setWrittenOffer(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("writtenOffer", item);
    }
    /**
     * Object:票据's 填开日期property 
     */
    public java.sql.Timestamp getWrittenOffTime()
    {
        return getTimestamp("writtenOffTime");
    }
    public void setWrittenOffTime(java.sql.Timestamp item)
    {
        setTimestamp("writtenOffTime", item);
    }
    /**
     * Object:票据's 核销状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum getVerifyStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum.getEnum(getInt("verifyStatus"));
    }
    public void setVerifyStatus(com.kingdee.eas.fdc.sellhouse.VerifyStatusEnum item)
    {
		if (item != null) {
        setInt("verifyStatus", item.getValue());
		}
    }
    /**
     * Object: 票据 's 核销组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getVerifyOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("verifyOrgUnit");
    }
    public void setVerifyOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("verifyOrgUnit", item);
    }
    /**
     * Object: 票据 's 核销人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getVerifier()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("verifier");
    }
    public void setVerifier(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("verifier", item);
    }
    /**
     * Object:票据's 核销日期property 
     */
    public java.sql.Timestamp getVerifyTime()
    {
        return getTimestamp("verifyTime");
    }
    public void setVerifyTime(java.sql.Timestamp item)
    {
        setTimestamp("verifyTime", item);
    }
    /**
     * Object:票据's 摘要property 
     */
    public String getResume()
    {
        return getString("resume");
    }
    public void setResume(String item)
    {
        setString("resume", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5EC688BC");
    }
}