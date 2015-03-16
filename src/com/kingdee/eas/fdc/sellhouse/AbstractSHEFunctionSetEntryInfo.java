package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEFunctionSetEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHEFunctionSetEntryInfo()
    {
        this("id");
    }
    protected AbstractSHEFunctionSetEntryInfo(String pkField)
    {
        super(pkField);
        put("productTypeEntry", new com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection());
    }
    /**
     * Object: 新售楼设置分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEFunctionSetInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 新售楼设置分录 's 项目 property 
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
     * Object:新售楼设置分录's 预约单失效时限property 
     */
    public int getAppointInvalidLimit()
    {
        return getInt("appointInvalidLimit");
    }
    public void setAppointInvalidLimit(int item)
    {
        setInt("appointInvalidLimit", item);
    }
    /**
     * Object:新售楼设置分录's 预订单失效时限property 
     */
    public int getBookingInvalidLimit()
    {
        return getInt("bookingInvalidLimit");
    }
    public void setBookingInvalidLimit(int item)
    {
        setInt("bookingInvalidLimit", item);
    }
    /**
     * Object:新售楼设置分录's 认购单失效时限property 
     */
    public int getPurchaseInvalidLimit()
    {
        return getInt("purchaseInvalidLimit");
    }
    public void setPurchaseInvalidLimit(int item)
    {
        setInt("purchaseInvalidLimit", item);
    }
    /**
     * Object:新售楼设置分录's 认购单默认签约时限property 
     */
    public int getPurchaseSignLimit()
    {
        return getInt("purchaseSignLimit");
    }
    public void setPurchaseSignLimit(int item)
    {
        setInt("purchaseSignLimit", item);
    }
    /**
     * Object:新售楼设置分录's 签约单默认失效时限property 
     */
    public int getSignInvalidLimit()
    {
        return getInt("signInvalidLimit");
    }
    public void setSignInvalidLimit(int item)
    {
        setInt("signInvalidLimit", item);
    }
    /**
     * Object:新售楼设置分录's 诚意金property 
     */
    public java.math.BigDecimal getSincerAmount()
    {
        return getBigDecimal("sincerAmount");
    }
    public void setSincerAmount(java.math.BigDecimal item)
    {
        setBigDecimal("sincerAmount", item);
    }
    /**
     * Object:新售楼设置分录's 预定金property 
     */
    public java.math.BigDecimal getPurBookingAmount()
    {
        return getBigDecimal("purBookingAmount");
    }
    public void setPurBookingAmount(java.math.BigDecimal item)
    {
        setBigDecimal("purBookingAmount", item);
    }
    /**
     * Object:新售楼设置分录's 定金property 
     */
    public java.math.BigDecimal getBookingAmount()
    {
        return getBigDecimal("bookingAmount");
    }
    public void setBookingAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bookingAmount", item);
    }
    /**
     * Object: 新售楼设置分录 's 产品类型分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection getProductTypeEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEFunProductTypeEntryCollection)get("productTypeEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0871233D");
    }
}