package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrePurchaseRoomAttachmentEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranRoomAttachmentEntryInfo implements Serializable 
{
    public AbstractPrePurchaseRoomAttachmentEntryInfo()
    {
        this("id");
    }
    protected AbstractPrePurchaseRoomAttachmentEntryInfo(String pkField)
    {
        super(pkField);
        put("prePurchaseAgioEntry", new com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection());
    }
    /**
     * Object: 预定附属房产信息分录 's 折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection getPrePurchaseAgioEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection)get("prePurchaseAgioEntry");
    }
    /**
     * Object: 预定附属房产信息分录 's 预定单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FAA358EB");
    }
}