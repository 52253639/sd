package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractProjectMonthPlanProInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractProjectMonthPlanProInfo()
    {
        this("id");
    }
    protected AbstractProjectMonthPlanProInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection());
    }
    /**
     * Object: 待签合同&&无合同费用付款计划 's 工程项目 property 
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
     * Object:待签合同&&无合同费用付款计划's 版本号property 
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
     * Object: 待签合同&&无合同费用付款计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.ProjectMonthPlanProEntryCollection)get("entry");
    }
    /**
     * Object:待签合同&&无合同费用付款计划's 是否最新property 
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
     * Object: 待签合同&&无合同费用付款计划 's 计划周期 property 
     */
    public com.kingdee.eas.fdc.basedata.PayPlanCycleInfo getCycle()
    {
        return (com.kingdee.eas.fdc.basedata.PayPlanCycleInfo)get("cycle");
    }
    public void setCycle(com.kingdee.eas.fdc.basedata.PayPlanCycleInfo item)
    {
        put("cycle", item);
    }
    /**
     * Object: 待签合同&&无合同费用付款计划 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getRespDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("respDept");
    }
    public void setRespDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("respDept", item);
    }
    /**
     * Object: 待签合同&&无合同费用付款计划 's 责任人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A900C46E");
    }
}