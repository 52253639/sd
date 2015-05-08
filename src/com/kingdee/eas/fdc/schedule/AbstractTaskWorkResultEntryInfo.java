package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskWorkResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractTaskWorkResultEntryInfo()
    {
        this("id");
    }
    protected AbstractTaskWorkResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:工作成果分录's 成果名称property 
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
     * Object:工作成果分录's 成果编号property 
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
     * Object:工作成果分录's 提交时间property 
     */
    public java.util.Date getCreateTime()
    {
        return getDate("createTime");
    }
    public void setCreateTime(java.util.Date item)
    {
        setDate("createTime", item);
    }
    /**
     * Object: 工作成果分录 's 提交人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getCreator()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("creator");
    }
    public void setCreator(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("creator", item);
    }
    /**
     * Object:工作成果分录's 成果类型property 
     */
    public String getResultType()
    {
        return getString("resultType");
    }
    public void setResultType(String item)
    {
        setString("resultType", item);
    }
    /**
     * Object: 工作成果分录 's 归属部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getAdminDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("adminDept");
    }
    public void setAdminDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("adminDept", item);
    }
    /**
     * Object: 工作成果分录 's 表头 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskWorkResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskWorkResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskWorkResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 工作成果分录 's 附件 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileInfo getFile()
    {
        return (com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileInfo)get("file");
    }
    public void setFile(com.kingdee.eas.fdc.schedule.TaskWorkResultEntryFileInfo item)
    {
        put("file", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6E3EA6A9");
    }
}