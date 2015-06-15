package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractEvaluationInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractContractEvaluationInfo()
    {
        this("id");
    }
    protected AbstractContractEvaluationInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ContractEvaluationEntryCollection());
    }
    /**
     * Object: 合同执行情况评估表 's 合同 property 
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
     * Object: 合同执行情况评估表 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ContractEvaluationEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ContractEvaluationEntryCollection)get("entry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A45ACF69");
    }
}