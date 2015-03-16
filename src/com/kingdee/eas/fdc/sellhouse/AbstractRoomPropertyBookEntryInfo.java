package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomPropertyBookEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractRoomPropertyBookEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomPropertyBookEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第1个表体 's  property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:第1个表体's 编码property 
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
     * Object:第1个表体's 名称property 
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
     * Object:第1个表体's 说明property 
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
     * Object:第1个表体's 办理日期property 
     */
    public java.util.Date getProcessDate()
    {
        return getDate("processDate");
    }
    public void setProcessDate(java.util.Date item)
    {
        setDate("processDate", item);
    }
    /**
     * Object:第1个表体's 是否完成property 
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
     * Object:第1个表体's 承诺完成日期property 
     */
    public java.util.Date getPromiseFinishDate()
    {
        return getDate("promiseFinishDate");
    }
    public void setPromiseFinishDate(java.util.Date item)
    {
        setDate("promiseFinishDate", item);
    }
    /**
     * Object:第1个表体's 实际完成日期property 
     */
    public java.util.Date getActualFinishDate()
    {
        return getDate("actualFinishDate");
    }
    public void setActualFinishDate(java.util.Date item)
    {
        setDate("actualFinishDate", item);
    }
    /**
     * Object: 第1个表体 's 产权进程经办人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTransactor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("transactor");
    }
    public void setTransactor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("transactor", item);
    }
    /**
     * Object:第1个表体's 单据办理结束标志property 
     */
    public boolean isIsFinishFlag()
    {
        return getBoolean("isFinishFlag");
    }
    public void setIsFinishFlag(boolean item)
    {
        setBoolean("isFinishFlag", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A117791E");
    }
}