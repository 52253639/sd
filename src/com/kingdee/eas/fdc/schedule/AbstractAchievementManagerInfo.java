package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAchievementManagerInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractAchievementManagerInfo()
    {
        this("id");
    }
    protected AbstractAchievementManagerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 阶段性成果管理 's 成果类别 property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementTypeInfo getAchievementType()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementTypeInfo)get("achievementType");
    }
    public void setAchievementType(com.kingdee.eas.fdc.schedule.AchievementTypeInfo item)
    {
        put("achievementType", item);
    }
    /**
     * Object: 阶段性成果管理 's 对应任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getRelateTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("relateTask");
    }
    public void setRelateTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("relateTask", item);
    }
    /**
     * Object:阶段性成果管理's 协同文档IDproperty 
     */
    public String getDocID()
    {
        return getString("docID");
    }
    public void setDocID(String item)
    {
        setString("docID", item);
    }
    /**
     * Object: 阶段性成果管理 's 归档目录 property 
     */
    public com.kingdee.eas.cp.dm.CategoryInfo getDocPath()
    {
        return (com.kingdee.eas.cp.dm.CategoryInfo)get("docPath");
    }
    public void setDocPath(com.kingdee.eas.cp.dm.CategoryInfo item)
    {
        put("docPath", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CF097D4");
    }
}