package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractHelpDeptEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractHelpDeptEntryInfo()
    {
        this("id");
    }
    protected AbstractHelpDeptEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 协助部门分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 协助部门分录 's 协助部门 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getDept()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("dept");
    }
    public void setDept(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("dept", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0ECA66D6");
    }
}