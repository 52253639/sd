package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierInviteRecordInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractSupplierInviteRecordInfo()
    {
        this("id");
    }
    protected AbstractSupplierInviteRecordInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.SupplierInviteRecordEntryCollection());
    }
    /**
     * Object: 投标记录 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:投标记录's 开标日期property 
     */
    public java.util.Date getOpenDate()
    {
        return getDate("openDate");
    }
    public void setOpenDate(java.util.Date item)
    {
        setDate("openDate", item);
    }
    /**
     * Object:投标记录's 开标地点property 
     */
    public String getPlace()
    {
        return getString("place");
    }
    public void setPlace(String item)
    {
        setString("place", item);
    }
    /**
     * Object:投标记录's 回标次数property 
     */
    public int getTimes()
    {
        return getInt("times");
    }
    public void setTimes(int item)
    {
        setInt("times", item);
    }
    /**
     * Object:投标记录's 供应商报价property 
     */
    public java.math.BigDecimal getPrice()
    {
        return getBigDecimal("price");
    }
    public void setPrice(java.math.BigDecimal item)
    {
        setBigDecimal("price", item);
    }
    /**
     * Object:投标记录's 回标日期property 
     */
    public java.util.Date getReturnDate()
    {
        return getDate("returnDate");
    }
    public void setReturnDate(java.util.Date item)
    {
        setDate("returnDate", item);
    }
    /**
     * Object:投标记录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:投标记录's 是否分标段property 
     */
    public boolean isIsMultiple()
    {
        return getBoolean("isMultiple");
    }
    public void setIsMultiple(boolean item)
    {
        setBoolean("isMultiple", item);
    }
    /**
     * Object: 投标记录 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.SupplierInviteRecordEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.SupplierInviteRecordEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A4E9E1CA");
    }
}