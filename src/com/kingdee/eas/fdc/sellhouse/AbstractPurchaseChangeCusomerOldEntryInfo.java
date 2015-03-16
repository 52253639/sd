package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseChangeCusomerOldEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPurchaseChangeCusomerOldEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseChangeCusomerOldEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: null 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: null 's 认购客户变更单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo item)
    {
        put("head", item);
    }
    /**
     * Object:null's 产权比例property 
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
     * Object:null's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7CA9B62B");
    }
}