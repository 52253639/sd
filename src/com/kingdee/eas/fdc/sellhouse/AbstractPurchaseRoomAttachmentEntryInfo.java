package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseRoomAttachmentEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractPurchaseRoomAttachmentEntryInfo()
    {
        this("id");
    }
    protected AbstractPurchaseRoomAttachmentEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 认购单上的绑定房间分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 认购单上的绑定房间分录 's 认购附属房产 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo getAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo)get("attachmentEntry");
    }
    public void setAttachmentEntry(com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryInfo item)
    {
        put("attachmentEntry", item);
    }
    /**
     * Object:认购单上的绑定房间分录's 并入金额property 
     */
    public java.math.BigDecimal getMergeAmount()
    {
        return getBigDecimal("mergeAmount");
    }
    public void setMergeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("mergeAmount", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6668A438");
    }
}