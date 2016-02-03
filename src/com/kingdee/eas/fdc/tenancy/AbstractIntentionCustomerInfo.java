package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractIntentionCustomerInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractIntentionCustomerInfo()
    {
        this("id");
    }
    protected AbstractIntentionCustomerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:意向客户's 手机/联系电话property 
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
     * Object:意向客户's 性别property 
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
     * Object:意向客户's 已付佣金property 
     */
    public java.math.BigDecimal getPayedAmount()
    {
        return getBigDecimal("payedAmount");
    }
    public void setPayedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payedAmount", item);
    }
    /**
     * Object:意向客户's 信息费property 
     */
    public java.math.BigDecimal getInfoAmount()
    {
        return getBigDecimal("infoAmount");
    }
    public void setInfoAmount(java.math.BigDecimal item)
    {
        setBigDecimal("infoAmount", item);
    }
    /**
     * Object:意向客户's 已付信息费property 
     */
    public java.math.BigDecimal getPayedInfoAmount()
    {
        return getBigDecimal("payedInfoAmount");
    }
    public void setPayedInfoAmount(java.math.BigDecimal item)
    {
        setBigDecimal("payedInfoAmount", item);
    }
    /**
     * Object: 意向客户 's 意向楼盘/产业园区 property 
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
     * Object:意向客户's 城市property 
     */
    public String getCity()
    {
        return getString("city");
    }
    public void setCity(String item)
    {
        setString("city", item);
    }
    /**
     * Object: 意向客户 's 经纪人 property 
     */
    public com.kingdee.eas.fdc.tenancy.BrokerInfo getBroker()
    {
        return (com.kingdee.eas.fdc.tenancy.BrokerInfo)get("broker");
    }
    public void setBroker(com.kingdee.eas.fdc.tenancy.BrokerInfo item)
    {
        put("broker", item);
    }
    /**
     * Object:意向客户's 已支付property 
     */
    public boolean isIsPayed()
    {
        return getBoolean("isPayed");
    }
    public void setIsPayed(boolean item)
    {
        setBoolean("isPayed", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("75527961");
    }
}