package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDirectAccepterResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractDirectAccepterResultEntryInfo()
    {
        this("id");
    }
    protected AbstractDirectAccepterResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 直接委托审批分录 's 中标审批 property 
     */
    public com.kingdee.eas.fdc.invite.DirectAccepterResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.DirectAccepterResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.DirectAccepterResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:直接委托审批分录's 中标property 
     */
    public boolean isHit()
    {
        return getBoolean("hit");
    }
    public void setHit(boolean item)
    {
        setBoolean("hit", item);
    }
    /**
     * Object: 直接委托审批分录 's 供应商 property 
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
     * Object:直接委托审批分录's 中标价property 
     */
    public java.math.BigDecimal getBidAmount()
    {
        return getBigDecimal("bidAmount");
    }
    public void setBidAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bidAmount", item);
    }
    /**
     * Object:直接委托审批分录's 选择该单位理由property 
     */
    public String getChooseReason()
    {
        return getString("chooseReason");
    }
    public void setChooseReason(String item)
    {
        setString("chooseReason", item);
    }
    /**
     * Object:直接委托审批分录's 第一次报价property 
     */
    public java.math.BigDecimal getFirstAmount()
    {
        return getBigDecimal("firstAmount");
    }
    public void setFirstAmount(java.math.BigDecimal item)
    {
        setBigDecimal("firstAmount", item);
    }
    /**
     * Object:直接委托审批分录's 第二次报价property 
     */
    public java.math.BigDecimal getSecondAmount()
    {
        return getBigDecimal("secondAmount");
    }
    public void setSecondAmount(java.math.BigDecimal item)
    {
        setBigDecimal("secondAmount", item);
    }
    /**
     * Object:直接委托审批分录's 已完成采购的单方造价property 
     */
    public java.math.BigDecimal getPurchaseDoneAmountEx()
    {
        return getBigDecimal("purchaseDoneAmountEx");
    }
    public void setPurchaseDoneAmountEx(java.math.BigDecimal item)
    {
        setBigDecimal("purchaseDoneAmountEx", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7BED6613");
    }
}