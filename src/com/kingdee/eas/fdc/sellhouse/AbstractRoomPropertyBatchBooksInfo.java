package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBatchBooksInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRoomPropertyBatchBooksInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBatchBooksInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 批量产权分录 's 产权管理 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo getBook()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo)get("book");
    }
    public void setBook(com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo item)
    {
        put("book", item);
    }
    /**
     * Object: 批量产权分录 's 批量产权 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FF98E01B");
    }
}