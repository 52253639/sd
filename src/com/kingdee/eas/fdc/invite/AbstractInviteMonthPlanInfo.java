package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteMonthPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractInviteMonthPlanInfo()
    {
        this("id");
    }
    protected AbstractInviteMonthPlanInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection());
    }
    /**
     * Object: 月度采购招标计划 's 所属项目 property 
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
     * Object:月度采购招标计划's 版本号property 
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
     * Object: 月度采购招标计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.InviteMonthPlanEntrysCollection)get("entry");
    }
    /**
     * Object: 月度采购招标计划 's 项目阶段 property 
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
     * Object:月度采购招标计划's 计划月份property 
     */
    public int getPlanMonth()
    {
        return getInt("planMonth");
    }
    public void setPlanMonth(int item)
    {
        setInt("planMonth", item);
    }
    /**
     * Object:月度采购招标计划's 计划月份property 
     */
    public int getPlanYear()
    {
        return getInt("planYear");
    }
    public void setPlanYear(int item)
    {
        setInt("planYear", item);
    }
    /**
     * Object: 月度采购招标计划 's 合约规划 property 
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
     * Object:月度采购招标计划's 备注property 
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
     * Object:月度采购招标计划's 是否最新property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F0BDFD1C");
    }
}