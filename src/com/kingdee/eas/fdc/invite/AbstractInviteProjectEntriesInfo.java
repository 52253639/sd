package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteProjectEntriesInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInviteProjectEntriesInfo()
    {
        this("id");
    }
    protected AbstractInviteProjectEntriesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标立项分录(new) 's 工程项目 property 
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
     * Object: 招标立项分录(new) 's 采购计划（合约规划） property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo getProgrammingContract()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo)get("programmingContract");
    }
    public void setProgrammingContract(com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo item)
    {
        put("programmingContract", item);
    }
    /**
     * Object:招标立项分录(new)'s 计划发标时间property 
     */
    public java.util.Date getIssueDate()
    {
        return getDate("issueDate");
    }
    public void setIssueDate(java.util.Date item)
    {
        setDate("issueDate", item);
    }
    /**
     * Object:招标立项分录(new)'s 计划开工时间property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:招标立项分录(new)'s 总工期property 
     */
    public java.math.BigDecimal getPeriod()
    {
        return getBigDecimal("period");
    }
    public void setPeriod(java.math.BigDecimal item)
    {
        setBigDecimal("period", item);
    }
    /**
     * Object:招标立项分录(new)'s 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object: 招标立项分录(new) 's  property 
     */
    public com.kingdee.eas.fdc.invite.InviteProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteProjectInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2FCFD3C4");
    }
}