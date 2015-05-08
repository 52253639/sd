package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskBaseInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractScheduleTaskBaseInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:进度任务's 颜色property 
     */
    public String getColor()
    {
        return getString("color");
    }
    public void setColor(String item)
    {
        setString("color", item);
    }
    /**
     * Object:进度任务's 里程碑property 
     */
    public boolean isMeeting()
    {
        return getBoolean("meeting");
    }
    public void setMeeting(boolean item)
    {
        setBoolean("meeting", item);
    }
    /**
     * Object:进度任务's 开始时间property 
     */
    public java.util.Date getStart()
    {
        return getDate("start");
    }
    public void setStart(java.util.Date item)
    {
        setDate("start", item);
    }
    /**
     * Object:进度任务's 持续时间property 
     */
    public int getDuration()
    {
        return getInt("duration");
    }
    public void setDuration(int item)
    {
        setInt("duration", item);
    }
    /**
     * Object:进度任务's 完成情况property 
     */
    public java.math.BigDecimal getComplete()
    {
        return getBigDecimal("complete");
    }
    public void setComplete(java.math.BigDecimal item)
    {
        setBigDecimal("complete", item);
    }
    /**
     * Object:进度任务's 优先级property 
     */
    public int getPriority()
    {
        return getInt("priority");
    }
    public void setPriority(int item)
    {
        setInt("priority", item);
    }
    /**
     * Object:进度任务's expandproperty 
     */
    public boolean isExpand()
    {
        return getBoolean("expand");
    }
    public void setExpand(boolean item)
    {
        setBoolean("expand", item);
    }
    /**
     * Object:进度任务's 备注property 
     */
    public String getNotes()
    {
        return getString("notes");
    }
    public void setNotes(String item)
    {
        setString("notes", item);
    }
    /**
     * Object:进度任务's 固定开始property 
     */
    public boolean isFixedStart()
    {
        return getBoolean("fixedStart");
    }
    public void setFixedStart(boolean item)
    {
        setBoolean("fixedStart", item);
    }
    /**
     * Object:进度任务's 链接property 
     */
    public String getWebLink()
    {
        return getString("webLink");
    }
    public void setWebLink(String item)
    {
        setString("webLink", item);
    }
    /**
     * Object:进度任务's 结束日期property 
     */
    public java.util.Date getEnd()
    {
        return getDate("end");
    }
    public void setEnd(java.util.Date item)
    {
        setDate("end", item);
    }
    /**
     * Object:进度任务's 形状property 
     */
    public String getShape()
    {
        return getString("shape");
    }
    public void setShape(String item)
    {
        setString("shape", item);
    }
    /**
     * Object:进度任务's thirdDateproperty 
     */
    public java.util.Date getThirdDate()
    {
        return getDate("thirdDate");
    }
    public void setThirdDate(java.util.Date item)
    {
        setDate("thirdDate", item);
    }
    /**
     * Object:进度任务's thirdDateConstraintproperty 
     */
    public int getThirdDateConstraint()
    {
        return getInt("thirdDateConstraint");
    }
    public void setThirdDateConstraint(int item)
    {
        setInt("thirdDateConstraint", item);
    }
    /**
     * Object:进度任务's 锁定property 
     */
    public boolean isLocked()
    {
        return getBoolean("locked");
    }
    public void setLocked(boolean item)
    {
        setBoolean("locked", item);
    }
    /**
     * Object:进度任务's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("794B7433");
    }
}