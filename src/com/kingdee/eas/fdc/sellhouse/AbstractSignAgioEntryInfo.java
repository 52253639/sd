package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSignAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractSignAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractSignAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 签约折扣信息分录 's 签约单id property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getSignManage()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("signManage");
    }
    public void setSignManage(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("signManage", item);
    }
    /**
     * Object: 签约折扣信息分录 's 附属房产分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryInfo getSignRoomAttEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryInfo)get("signRoomAttEntry");
    }
    public void setSignRoomAttEntry(com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryInfo item)
    {
        put("signRoomAttEntry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E230ABCE");
    }
}