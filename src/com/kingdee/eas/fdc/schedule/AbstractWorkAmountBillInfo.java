package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractWorkAmountBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractWorkAmountBillInfo()
    {
        this("id");
    }
    protected AbstractWorkAmountBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection());
    }
    /**
     * Object: 任务工程量填报 's 填报部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getBizDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("bizDept");
    }
    public void setBizDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("bizDept", item);
    }
    /**
     * Object: 任务工程量填报 's 分录 property 
     */
    public com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection)get("entry");
    }
    /**
     * Object: 任务工程量填报 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 任务工程量填报 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAdminPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("adminPerson");
    }
    public void setAdminPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("adminPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("75D27BC6");
    }
}