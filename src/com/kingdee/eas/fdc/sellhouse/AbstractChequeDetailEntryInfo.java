package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChequeDetailEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractChequeDetailEntryInfo()
    {
        this("id");
    }
    protected AbstractChequeDetailEntryInfo(String pkField)
    {
        super(pkField);
        put("customerEntry", new com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryCollection());
        put("revListEntry", new com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryCollection());
    }
    /**
     * Object:票据明细's 票据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum getCheQueType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.getEnum(getString("cheQueType"));
    }
    public void setCheQueType(com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum item)
    {
		if (item != null) {
        setString("cheQueType", item.getValue());
		}
    }
    /**
     * Object: 票据明细 's 票据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CRMChequeInfo getCheque()
    {
        return (com.kingdee.eas.fdc.sellhouse.CRMChequeInfo)get("cheque");
    }
    public void setCheque(com.kingdee.eas.fdc.sellhouse.CRMChequeInfo item)
    {
        put("cheque", item);
    }
    /**
     * Object: 票据明细 's 关联收款单明细 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryCollection getRevListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeRevListEntryCollection)get("revListEntry");
    }
    /**
     * Object:票据明细's 开票客户property 
     */
    public String getChequeCustomer()
    {
        return getString("chequeCustomer");
    }
    public void setChequeCustomer(String item)
    {
        setString("chequeCustomer", item);
    }
    /**
     * Object:票据明细's 开票金额property 
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
     * Object:票据明细's 大写金额property 
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
     * Object: 票据明细 's 填开人 property 
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
     * Object:票据明细's 填开日期property 
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
     * Object:票据明细's 票据状态property 
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
     * Object:票据明细's 核销状态property 
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
     * Object: 票据明细 's 核销组织 property 
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
     * Object: 票据明细 's 核销人 property 
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
     * Object:票据明细's 核销日期property 
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
     * Object:票据明细's 票据编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object: 票据明细 's 房间 property 
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
     * Object: 票据明细 's 客户分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryCollection getCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChequeCustomerEntryCollection)get("customerEntry");
    }
    /**
     * Object:票据明细's 备注property 
     */
    public String getDes()
    {
        return getString("des");
    }
    public void setDes(String item)
    {
        setString("des", item);
    }
    /**
     * Object: 票据明细 's 领用人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPicker()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("picker");
    }
    public void setPicker(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("picker", item);
    }
    /**
     * Object:票据明细's 领用日期property 
     */
    public java.sql.Timestamp getPikeDate()
    {
        return getTimestamp("pikeDate");
    }
    public void setPikeDate(java.sql.Timestamp item)
    {
        setTimestamp("pikeDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B7911C45");
    }
}