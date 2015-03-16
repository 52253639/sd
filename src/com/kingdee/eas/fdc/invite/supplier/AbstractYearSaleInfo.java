package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractYearSaleInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractYearSaleInfo()
    {
        this("id");
    }
    protected AbstractYearSaleInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:近三年销售额's 年份property 
     */
    public String getYear()
    {
        return getString("year");
    }
    public void setYear(String item)
    {
        setString("year", item);
    }
    /**
     * Object:近三年销售额's 销售额property 
     */
    public java.math.BigDecimal getSaleCount()
    {
        return getBigDecimal("saleCount");
    }
    public void setSaleCount(java.math.BigDecimal item)
    {
        setBigDecimal("saleCount", item);
    }
    /**
     * Object: 近三年销售额 's 所属单据体 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A790C122");
    }
}