package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteSupplierEnterBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteSupplierEnterBillInfo()
    {
        this("id");
    }
    protected AbstractInviteSupplierEnterBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:供应商入围标准确认单's 经办日期property 
     */
    public java.util.Date getResponsibleDate()
    {
        return getDate("responsibleDate");
    }
    public void setResponsibleDate(java.util.Date item)
    {
        setDate("responsibleDate", item);
    }
    /**
     * Object:供应商入围标准确认单's 备注property 
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
     * Object:供应商入围标准确认单's nullproperty 
     */
    public String getEnterCriterion()
    {
        return getString("enterCriterion");
    }
    public void setEnterCriterion(String item)
    {
        setString("enterCriterion", item);
    }
    /**
     * Object: 供应商入围标准确认单 's 经办人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getResponsiblePerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("responsiblePerson");
    }
    public void setResponsiblePerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("responsiblePerson", item);
    }
    /**
     * Object: 供应商入围标准确认单 's 经办部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getResponsibleDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("responsibleDept");
    }
    public void setResponsibleDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("responsibleDept", item);
    }
    /**
     * Object: 供应商入围标准确认单 's 招标立项 property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getInviteProject()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("inviteProject");
    }
    public void setInviteProject(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("inviteProject", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4675F6E6");
    }
}