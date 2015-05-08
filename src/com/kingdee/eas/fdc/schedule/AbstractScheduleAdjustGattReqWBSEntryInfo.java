package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleAdjustGattReqWBSEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractScheduleAdjustGattReqWBSEntryInfo()
    {
        this("id");
    }
    protected AbstractScheduleAdjustGattReqWBSEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:调整单WBS影响分录's WBS影响类型property 
     */
    public com.kingdee.eas.fdc.schedule.AdjustReqGattWBSEnum getAdjustWbsType()
    {
        return com.kingdee.eas.fdc.schedule.AdjustReqGattWBSEnum.getEnum(getString("adjustWbsType"));
    }
    public void setAdjustWbsType(com.kingdee.eas.fdc.schedule.AdjustReqGattWBSEnum item)
    {
		if (item != null) {
        setString("adjustWbsType", item.getValue());
		}
    }
    /**
     * Object: 调整单WBS影响分录 's 被影响的WBS property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getWbs()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("wbs");
    }
    public void setWbs(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("wbs", item);
    }
    /**
     * Object: 调整单WBS影响分录 's 调整单 property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EE2091D2");
    }
}