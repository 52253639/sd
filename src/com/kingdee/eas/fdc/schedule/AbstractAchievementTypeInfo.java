package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAchievementTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractAchievementTypeInfo()
    {
        this("id");
    }
    protected AbstractAchievementTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 阶段性成果类别 's 成果阶段 property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementStageInfo getStage()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementStageInfo)get("stage");
    }
    public void setStage(com.kingdee.eas.fdc.schedule.AchievementStageInfo item)
    {
        put("stage", item);
    }
    /**
     * Object: 阶段性成果类别 's 成果专业 property 
     */
    public com.kingdee.eas.fdc.schedule.AchievementProfessionInfo getProfession()
    {
        return (com.kingdee.eas.fdc.schedule.AchievementProfessionInfo)get("profession");
    }
    public void setProfession(com.kingdee.eas.fdc.schedule.AchievementProfessionInfo item)
    {
        put("profession", item);
    }
    /**
     * Object: 阶段性成果类别 's 归档目录 property 
     */
    public com.kingdee.eas.cp.dm.CategoryInfo getDocDirectory()
    {
        return (com.kingdee.eas.cp.dm.CategoryInfo)get("docDirectory");
    }
    public void setDocDirectory(com.kingdee.eas.cp.dm.CategoryInfo item)
    {
        put("docDirectory", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2E255073");
    }
}