package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSchTaskWithContractInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCSchTaskWithContractInfo()
    {
        this("id");
    }
    protected AbstractFDCSchTaskWithContractInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 任务与合同关联表 's 合同 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getContract()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("contract");
    }
    public void setContract(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("contract", item);
    }
    /**
     * Object: 任务与合同关联表 's 进度任务 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo getTask()
    {
        return (com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo)get("task");
    }
    public void setTask(com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo item)
    {
        put("task", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CAF0B606");
    }
}