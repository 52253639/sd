package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvitePlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInvitePlanInfo()
    {
        this("id");
    }
    protected AbstractInvitePlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InvitePlanEntrysCollection());
    }
    /**
     * Object: 项目采购招标计划 's 所属项目 property 
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
     * Object:项目采购招标计划's 版本号property 
     */
    public java.math.BigDecimal getVersion()
    {
        return getBigDecimal("version");
    }
    public void setVersion(java.math.BigDecimal item)
    {
        setBigDecimal("version", item);
    }
    /**
     * Object: 项目采购招标计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.InvitePlanEntrysCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InvitePlanEntrysCollection)get("entry");
    }
    /**
     * Object: 项目采购招标计划 's 项目阶段 property 
     */
    public com.kingdee.eas.fdc.basedata.MeasureStageInfo getMeasureStage()
    {
        return (com.kingdee.eas.fdc.basedata.MeasureStageInfo)get("measureStage");
    }
    public void setMeasureStage(com.kingdee.eas.fdc.basedata.MeasureStageInfo item)
    {
        put("measureStage", item);
    }
    /**
     * Object:项目采购招标计划's 计划开始日期property 
     */
    public java.util.Date getPlanBeginDate()
    {
        return getDate("planBeginDate");
    }
    public void setPlanBeginDate(java.util.Date item)
    {
        setDate("planBeginDate", item);
    }
    /**
     * Object:项目采购招标计划's 计划结束日期property 
     */
    public java.util.Date getPlanEndDate()
    {
        return getDate("planEndDate");
    }
    public void setPlanEndDate(java.util.Date item)
    {
        setDate("planEndDate", item);
    }
    /**
     * Object: 项目采购招标计划 's 合约规划 property 
     */
    public com.kingdee.eas.fdc.contract.programming.ProgrammingInfo getProgramming()
    {
        return (com.kingdee.eas.fdc.contract.programming.ProgrammingInfo)get("programming");
    }
    public void setProgramming(com.kingdee.eas.fdc.contract.programming.ProgrammingInfo item)
    {
        put("programming", item);
    }
    /**
     * Object:项目采购招标计划's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E9667F6");
    }
}