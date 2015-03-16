package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBatchMaterialsInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRoomPropertyBatchMaterialsInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBatchMaterialsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 批量产权办理资料 's 批量产权 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.RoomPropertyBatchInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:批量产权办理资料's 编码property 
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
     * Object:批量产权办理资料's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:批量产权办理资料's 说明property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object:批量产权办理资料's 是否完成property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:批量产权办理资料's 备齐日期property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78AADD1D");
    }
}