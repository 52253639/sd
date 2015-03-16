package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenderAccepterResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTenderAccepterResultEntryInfo()
    {
        this("id");
    }
    protected AbstractTenderAccepterResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 中标审批分录 's 中标审批 property 
     */
    public com.kingdee.eas.fdc.invite.TenderAccepterResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.TenderAccepterResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.TenderAccepterResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:中标审批分录's 中标property 
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
     * Object: 中标审批分录 's 供应商 property 
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
     * Object:中标审批分录's 中标价property 
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
     * Object:中标审批分录's 总价排名property 
     */
    public int getTotalSeq()
    {
        return getInt("totalSeq");
    }
    public void setTotalSeq(int item)
    {
        setInt("totalSeq", item);
    }
    /**
     * Object:中标审批分录's 票数property 
     */
    public int getVotes()
    {
        return getInt("votes");
    }
    public void setVotes(int item)
    {
        setInt("votes", item);
    }
    /**
     * Object: 中标审批分录 's 修正系数 property 
     */
    public com.kingdee.eas.fdc.invite.InviteFixInfo getInviteFix()
    {
        return (com.kingdee.eas.fdc.invite.InviteFixInfo)get("inviteFix");
    }
    public void setInviteFix(com.kingdee.eas.fdc.invite.InviteFixInfo item)
    {
        put("inviteFix", item);
    }
    /**
     * Object:中标审批分录's 修正后报价property 
     */
    public java.math.BigDecimal getAfterFixAmount()
    {
        return getBigDecimal("afterFixAmount");
    }
    public void setAfterFixAmount(java.math.BigDecimal item)
    {
        setBigDecimal("afterFixAmount", item);
    }
    /**
     * Object:中标审批分录's 最终报价property 
     */
    public java.math.BigDecimal getLastAmount()
    {
        return getBigDecimal("lastAmount");
    }
    public void setLastAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lastAmount", item);
    }
    /**
     * Object:中标审批分录's 是否最低价中标property 
     */
    public boolean isIsLowest()
    {
        return getBoolean("isLowest");
    }
    public void setIsLowest(boolean item)
    {
        setBoolean("isLowest", item);
    }
    /**
     * Object:中标审批分录's 已完成采购的单方造价property 
     */
    public java.math.BigDecimal getCostOfContruction()
    {
        return getBigDecimal("costOfContruction");
    }
    public void setCostOfContruction(java.math.BigDecimal item)
    {
        setBigDecimal("costOfContruction", item);
    }
    /**
     * Object:中标审批分录's 标段property 
     */
    public int getRecordSeq()
    {
        return getInt("recordSeq");
    }
    public void setRecordSeq(int item)
    {
        setInt("recordSeq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("698E1948");
    }
}