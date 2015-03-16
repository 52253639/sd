package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRoomLoanAFMEntrysInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractRoomLoanAFMEntrysInfo()
    {
        this("id");
    }
    protected AbstractRoomLoanAFMEntrysInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:null's 办理日期property 
     */
    public java.util.Date getDate()
    {
        return getDate("date");
    }
    public void setDate(java.util.Date item)
    {
        setDate("date", item);
    }
    /**
     * Object:null's 是否完成property 
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
     * Object:null's 步骤/资料property 
     */
    public String getApproach()
    {
        return getString("approach");
    }
    public void setApproach(String item)
    {
        setString("approach", item);
    }
    /**
     * Object:null's 备注property 
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
     * Object:null's 进程or资料property 
     */
    public boolean isIsAOrB()
    {
        return getBoolean("isAOrB");
    }
    public void setIsAOrB(boolean item)
    {
        setBoolean("isAOrB", item);
    }
    /**
     * Object: null 's null property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomLoanInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomLoanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.RoomLoanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:null's 应完成日期property 
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
     * Object:null's 实际完成日期property 
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
     * Object: null 's transactor property 
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
     * Object:null's 单据办理结束标志property 
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
        return new BOSObjectType("9E35F7A3");
    }
}