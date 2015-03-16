package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOrgUnitMonthPlanGatherInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractOrgUnitMonthPlanGatherInfo()
    {
        this("id");
    }
    protected AbstractOrgUnitMonthPlanGatherInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryCollection());
    }
    /**
     * Object:事业部、集团月度资金计划's 版本号property 
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
     * Object: 事业部、集团月度资金计划 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryCollection)get("entry");
    }
    /**
     * Object:事业部、集团月度资金计划's 是否最新property 
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
     * Object: 事业部、集团月度资金计划 's 计划周期 property 
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
     * Object:事业部、集团月度资金计划's 版本类型property 
     */
    public com.kingdee.eas.fdc.finance.VersionTypeEnum getVersionType()
    {
        return com.kingdee.eas.fdc.finance.VersionTypeEnum.getEnum(getString("versionType"));
    }
    public void setVersionType(com.kingdee.eas.fdc.finance.VersionTypeEnum item)
    {
		if (item != null) {
        setString("versionType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2A911D4B");
    }
}