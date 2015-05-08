package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractAndTaskRelEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractContractAndTaskRelEntryInfo()
    {
        this("id");
    }
    protected AbstractContractAndTaskRelEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同与任务关系分录 's 任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("task", item);
    }
    /**
     * Object:合同与任务关系分录's 备注property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 合同与任务关系分录 's 合同与任务 property 
     */
    public com.kingdee.eas.fdc.schedule.ContractAndTaskRelInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.ContractAndTaskRelInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.ContractAndTaskRelInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 合同与任务关系分录 's wbs property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getWbs()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("wbs");
    }
    public void setWbs(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("wbs", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EB59E36D");
    }
}