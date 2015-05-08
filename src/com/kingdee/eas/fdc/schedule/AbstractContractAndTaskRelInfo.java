package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractAndTaskRelInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractAndTaskRelInfo()
    {
        this("id");
    }
    protected AbstractContractAndTaskRelInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection());
    }
    /**
     * Object: 合同与任务关联 's 合同 property 
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
     * Object: 合同与任务关联 's 分录 property 
     */
    public com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryCollection)get("entrys");
    }
    /**
     * Object:合同与任务关联's 是否启用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("68747005");
    }
}