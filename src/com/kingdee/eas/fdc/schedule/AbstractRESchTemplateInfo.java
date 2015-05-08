package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRESchTemplateInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractRESchTemplateInfo()
    {
        this("id");
    }
    protected AbstractRESchTemplateInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection());
    }
    /**
     * Object:房地产计划模板's 是否系统预设property 
     */
    public boolean isIsSystem()
    {
        return getBoolean("isSystem");
    }
    public void setIsSystem(boolean item)
    {
        setBoolean("isSystem", item);
    }
    /**
     * Object: 房地产计划模板 's 上级模板 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.RESchTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:房地产计划模板's 模板类型property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum getTemplateType()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum.getEnum(getString("templateType"));
    }
    public void setTemplateType(com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum item)
    {
		if (item != null) {
        setString("templateType", item.getValue());
		}
    }
    /**
     * Object: 房地产计划模板 's 维护组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object:房地产计划模板's 单据状态property 
     */
    public com.kingdee.eas.fdc.basedata.FDCBillStateEnum getState()
    {
        return com.kingdee.eas.fdc.basedata.FDCBillStateEnum.getEnum(getString("state"));
    }
    public void setState(com.kingdee.eas.fdc.basedata.FDCBillStateEnum item)
    {
		if (item != null) {
        setString("state", item.getValue());
		}
    }
    /**
     * Object: 房地产计划模板 's 审批人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getAuditor()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("auditor");
    }
    public void setAuditor(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("auditor", item);
    }
    /**
     * Object:房地产计划模板's 审批时间property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object: 房地产计划模板 's 模板任务分录 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection getEntry()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection)get("entry");
    }
    /**
     * Object: 房地产计划模板 's 模板分类 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo getCatagory()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo)get("catagory");
    }
    public void setCatagory(com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo item)
    {
        put("catagory", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("1BD5B489");
    }
}