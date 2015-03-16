package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractXQEntryInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractXQEntryInfo()
    {
        this("id");
    }
    protected AbstractXQEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 需求信息分录 's 客户信息头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 需求信息分录 's 产品类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:需求信息分录's 面积大小property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:需求信息分录's 层高property 
     */
    public java.math.BigDecimal getHigh()
    {
        return getBigDecimal("high");
    }
    public void setHigh(java.math.BigDecimal item)
    {
        setBigDecimal("high", item);
    }
    /**
     * Object:需求信息分录's 楼地面负载property 
     */
    public java.math.BigDecimal getFz()
    {
        return getBigDecimal("fz");
    }
    public void setFz(java.math.BigDecimal item)
    {
        setBigDecimal("fz", item);
    }
    /**
     * Object:需求信息分录's 用电量property 
     */
    public java.math.BigDecimal getYdl()
    {
        return getBigDecimal("ydl");
    }
    public void setYdl(java.math.BigDecimal item)
    {
        setBigDecimal("ydl", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A7E9A5DE");
    }
}