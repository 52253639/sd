package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectYearPlanInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectYearPlanInfo()
    {
        this("id");
    }
    protected AbstractProjectYearPlanInfo(String pkField)
    {
        super(pkField);
        put("totalBgEntry", new com.kingdee.eas.fdc.finance.ProjectYearPlanTotalBgItemEntryCollection());
        put("entry", new com.kingdee.eas.fdc.finance.ProjectYearPlanEntryCollection());
        put("totalEntry", new com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection());
    }
    /**
     * Object: 项目年度付款规划 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:项目年度付款规划's 版本号property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    /**
     * Object: 项目年度付款规划 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanEntryCollection)get("entry");
    }
    /**
     * Object:项目年度付款规划's 是否最新property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object: 项目年度付款规划 's 汇总分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection getTotalEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanTotalEntryCollection)get("totalEntry");
    }
    /**
     * Object: 项目年度付款规划 's 预算项目汇总分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectYearPlanTotalBgItemEntryCollection getTotalBgEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectYearPlanTotalBgItemEntryCollection)get("totalBgEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9F9C8250");
    }
}