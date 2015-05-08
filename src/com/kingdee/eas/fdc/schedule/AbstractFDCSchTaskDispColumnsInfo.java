package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSchTaskDispColumnsInfo extends com.kingdee.eas.fdc.schedule.framework.ScheduleTaskdisplaycolumnsInfo implements Serializable 
{
    public AbstractFDCSchTaskDispColumnsInfo()
    {
        this("id");
    }
    protected AbstractFDCSchTaskDispColumnsInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房地产进度列显示 's 进度 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCScheduleInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59E9380D");
    }
}