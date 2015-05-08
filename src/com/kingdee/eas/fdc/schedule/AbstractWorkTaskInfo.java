package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkTaskInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractWorkTaskInfo()
    {
        this("id");
    }
    protected AbstractWorkTaskInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工作任务's 预计工期property 
     */
    public int getEstimateDays()
    {
        return getInt("estimateDays");
    }
    public void setEstimateDays(int item)
    {
        setInt("estimateDays", item);
    }
    /**
     * Object:工作任务's 预计工期是否锁定property 
     */
    public boolean isIsEstDaysLocked()
    {
        return getBoolean("isEstDaysLocked");
    }
    public void setIsEstDaysLocked(boolean item)
    {
        setBoolean("isEstDaysLocked", item);
    }
    /**
     * Object:工作任务's 是否启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: 工作任务 's 父节点 property 
     */
    public com.kingdee.eas.fdc.schedule.WorkTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.WorkTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.WorkTaskInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2E05760C");
    }
}