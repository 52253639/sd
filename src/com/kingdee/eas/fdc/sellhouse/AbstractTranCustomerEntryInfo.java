package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranCustomerEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTranCustomerEntryInfo()
    {
        this("");
    }
    protected AbstractTranCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:客户分录基类's 分录序号property 
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
     * Object:客户分录基类's 百分比property 
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
     * Object: 客户分录基类 's 客户信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object:客户分录基类's 客户名称property 
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
     * Object:客户分录基类's 移动电话property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:客户分录基类's 住宅电话property 
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
     * Object: 客户分录基类 's 证件类型 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo getCertificate()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo)get("certificate");
    }
    public void setCertificate(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataInfo item)
    {
        put("certificate", item);
    }
    /**
     * Object:客户分录基类's 证件号码property 
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
     * Object:客户分录基类's 联系地址property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:客户分录基类's 是否主客户property 
     */
    public boolean isIsMain()
    {
        return getBoolean("isMain");
    }
    public void setIsMain(boolean item)
    {
        setBoolean("isMain", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("912E184E");
    }
}