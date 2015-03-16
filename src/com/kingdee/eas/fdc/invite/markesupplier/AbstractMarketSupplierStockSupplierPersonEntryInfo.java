package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierStockSupplierPersonEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierStockSupplierPersonEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierStockSupplierPersonEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:职员构成分录's 类别编码property 
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
     * Object:职员构成分录's 类别名称property 
     */
    public String getName()
    {
        return getName((Locale)null);
    }
    public void setName(String item)
    {
		setName(item,(Locale)null);
    }
    public String getName(Locale local)
    {
        return TypeConversionUtils.objToString(get("name", local));
    }
    public void setName(String item, Locale local)
    {
        put("name", item, local);
    }
    /**
     * Object:职员构成分录's 人数property 
     */
    public int getAmount()
    {
        return getInt("amount");
    }
    public void setAmount(int item)
    {
        setInt("amount", item);
    }
    /**
     * Object: 职员构成分录 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("38C1EB11");
    }
}