package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurCustomerInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractPurCustomerInfo()
    {
        this("id");
    }
    protected AbstractPurCustomerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:认购交易客户信息's 认购单property 
     */
    public com.kingdee.bos.util.BOSUuid getPurchase()
    {
        return getBOSUuid("purchase");
    }
    public void setPurchase(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("purchase", item);
    }
    /**
     * Object:认购交易客户信息's 旧客户信息property 
     */
    public com.kingdee.bos.util.BOSUuid getOldPurCustomer()
    {
        return getBOSUuid("oldPurCustomer");
    }
    public void setOldPurCustomer(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("oldPurCustomer", item);
    }
    /**
     * Object:认购交易客户信息's 产权比例property 
     */
    public java.math.BigDecimal getPropertyPercent()
    {
        return getBigDecimal("propertyPercent");
    }
    public void setPropertyPercent(java.math.BigDecimal item)
    {
        setBigDecimal("propertyPercent", item);
    }
    /**
     * Object:认购交易客户信息's nullproperty 
     */
    public String getCustomerName()
    {
        return getString("customerName");
    }
    public void setCustomerName(String item)
    {
        setString("customerName", item);
    }
    /**
     * Object:认购交易客户信息's property 
     */
    public com.kingdee.bos.util.BOSUuid getCustomerID()
    {
        return getBOSUuid("customerID");
    }
    public void setCustomerID(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("customerID", item);
    }
    /**
     * Object:认购交易客户信息's 联系电话property 
     */
    public String getTel()
    {
        return getString("tel");
    }
    public void setTel(String item)
    {
        setString("tel", item);
    }
    /**
     * Object:认购交易客户信息's 证件号码property 
     */
    public String getCertificateNumber()
    {
        return getString("certificateNumber");
    }
    public void setCertificateNumber(String item)
    {
        setString("certificateNumber", item);
    }
    /**
     * Object:认购交易客户信息's 通信地址property 
     */
    public String getMailAddress()
    {
        return getString("mailAddress");
    }
    public void setMailAddress(String item)
    {
        setString("mailAddress", item);
    }
    /**
     * Object:认购交易客户信息's nullproperty 
     */
    public String getPostalcode()
    {
        return getString("postalcode");
    }
    public void setPostalcode(String item)
    {
        setString("postalcode", item);
    }
    /**
     * Object:认购交易客户信息's 性别property 
     */
    public com.kingdee.eas.fdc.sellhouse.SexEnum getSex()
    {
        return com.kingdee.eas.fdc.sellhouse.SexEnum.getEnum(getString("sex"));
    }
    public void setSex(com.kingdee.eas.fdc.sellhouse.SexEnum item)
    {
		if (item != null) {
        setString("sex", item.getValue());
		}
    }
    /**
     * Object:认购交易客户信息's 是否主客户property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object: 认购交易客户信息 's 认购单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 认购交易客户信息 's 证件名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CertificateInfo getCertificateName()
    {
        return (com.kingdee.eas.fdc.sellhouse.CertificateInfo)get("certificateName");
    }
    public void setCertificateName(com.kingdee.eas.fdc.sellhouse.CertificateInfo item)
    {
        put("certificateName", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("33B38C50");
    }
}