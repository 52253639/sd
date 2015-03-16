package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomJoinApproachEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractRoomJoinApproachEntryInfo()
    {
        this("id");
    }
    protected AbstractRoomJoinApproachEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 入伙办理进程 's 入伙单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomJoinInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomJoinInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomJoinInfo item)
    {
        put("head", item);
    }
    /**
     * Object:入伙办理进程's 进程名称property 
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
     * Object:入伙办理进程's 应完成时间property 
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
     * Object:入伙办理进程's 是否完成property 
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
     * Object:入伙办理进程's 实际完成日期property 
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
     * Object: 入伙办理进程 's 经办人 property 
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
     * Object:入伙办理进程's 说明property 
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
     * Object:入伙办理进程's 单据办理结束标志property 
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
        return new BOSObjectType("7FEF410A");
    }
}