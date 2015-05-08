package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRESchTemplateTaskInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractRESchTemplateTaskInfo()
    {
        this("id");
    }
    protected AbstractRESchTemplateTaskInfo(String pkField)
    {
        super(pkField);
        put("businessType", new com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection());
        put("predecessors", new com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection());
    }
    /**
     * Object: 房地产计划模板任务 's 模板 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.schedule.RESchTemplateInfo item)
    {
        put("template", item);
    }
    /**
     * Object: 房地产计划模板任务 's 上级任务 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 房地产计划模板任务 's 标准任务指引 property 
     */
    public com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo getStandardTask()
    {
        return (com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo)get("standardTask");
    }
    public void setStandardTask(com.kingdee.eas.fdc.schedule.StandardTaskGuideInfo item)
    {
        put("standardTask", item);
    }
    /**
     * Object:房地产计划模板任务's 任务类别property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum getTaskType()
    {
        return com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum.getEnum(getString("taskType"));
    }
    public void setTaskType(com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum item)
    {
		if (item != null) {
        setString("taskType", item.getValue());
		}
    }
    /**
     * Object: 房地产计划模板任务 's 成果类别 property 
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
     * Object: 房地产计划模板任务 's 业务类型 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection getBusinessType()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection)get("businessType");
    }
    /**
     * Object: 房地产计划模板任务 's 前置任务 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection getPredecessors()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection)get("predecessors");
    }
    /**
     * Object:房地产计划模板任务's 参考工期property 
     */
    public int getReferenceDay()
    {
        return getInt("referenceDay");
    }
    public void setReferenceDay(int item)
    {
        setInt("referenceDay", item);
    }
    /**
     * Object:房地产计划模板任务's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:房地产计划模板任务's 前置任务描述property 
     */
    public String getPredecessorDesc()
    {
        return getString("predecessorDesc");
    }
    public void setPredecessorDesc(String item)
    {
        setString("predecessorDesc", item);
    }
    /**
     * Object:房地产计划模板任务's 业务类型描述property 
     */
    public String getBusinessTypeDesc()
    {
        return getString("businessTypeDesc");
    }
    public void setBusinessTypeDesc(String item)
    {
        setString("businessTypeDesc", item);
    }
    /**
     * Object:房地产计划模板任务's 来源于集团管控节点property 
     */
    public String getSrcGroupNode()
    {
        return getString("srcGroupNode");
    }
    public void setSrcGroupNode(String item)
    {
        setString("srcGroupNode", item);
    }
    /**
     * Object:房地产计划模板任务's 是否楼栋节点property 
     */
    public boolean isIsBuildingNode()
    {
        return getBoolean("isBuildingNode");
    }
    public void setIsBuildingNode(boolean item)
    {
        setBoolean("isBuildingNode", item);
    }
    /**
     * Object:房地产计划模板任务's 是否经营分析节点property 
     */
    public boolean isIsOperationNode()
    {
        return getBoolean("isOperationNode");
    }
    public void setIsOperationNode(boolean item)
    {
        setBoolean("isOperationNode", item);
    }
    /**
     * Object: 房地产计划模板任务 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("adminPerson");
    }
    public void setAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("adminPerson", item);
    }
    /**
     * Object: 房地产计划模板任务 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("198DF1AE");
    }
}