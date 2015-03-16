package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrePurchaseManageInfo extends com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo implements Serializable 
{
    public AbstractPrePurchaseManageInfo()
    {
        this("id");
    }
    protected AbstractPrePurchaseManageInfo(String pkField)
    {
        super(pkField);
        put("prePurchaseAgioEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection());
        put("prePurchaseCustomerEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection());
        put("prePurchasePayListEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection());
        put("prePurchaseSaleManEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection());
        put("prePurchaseRoomAttachmentEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection());
    }
    /**
     * Object: 预定管理 's 付款明细分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection getPrePurchasePayListEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection)get("prePurchasePayListEntry");
    }
    /**
     * Object: 预定管理 's 附属房产分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection getPrePurchaseRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection)get("prePurchaseRoomAttachmentEntry");
    }
    /**
     * Object: 预定管理 's 折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection getPrePurchaseAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection)get("prePurchaseAgioEntry");
    }
    /**
     * Object: 预定管理 's 客户信息分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection getPrePurchaseCustomerEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection)get("prePurchaseCustomerEntry");
    }
    /**
     * Object:预定管理's 预定标准金property 
     */
    public java.math.BigDecimal getPreAmount()
    {
        return getBigDecimal("preAmount");
    }
    public void setPreAmount(java.math.BigDecimal item)
    {
        setBigDecimal("preAmount", item);
    }
    /**
     * Object: 预定管理 's 置业顾问分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection getPrePurchaseSaleManEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection)get("prePurchaseSaleManEntry");
    }
    /**
     * Object:预定管理's 置业顾问property 
     */
    public String getSaleManNames()
    {
        return getString("saleManNames");
    }
    public void setSaleManNames(String item)
    {
        setString("saleManNames", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("10AC79CE");
    }
}