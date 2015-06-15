package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractEvaluationEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractEvaluationEntryInfo()
    {
        this("id");
    }
    protected AbstractContractEvaluationEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同执行情况评估表分录 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractEvaluationInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.ContractEvaluationInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.ContractEvaluationInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 合同执行情况评估表分录 's 评估结果 property 
     */
    public com.kingdee.eas.fdc.contract.EvaluationResultInfo getResult()
    {
        return (com.kingdee.eas.fdc.contract.EvaluationResultInfo)get("result");
    }
    public void setResult(com.kingdee.eas.fdc.contract.EvaluationResultInfo item)
    {
        put("result", item);
    }
    /**
     * Object: 合同执行情况评估表分录 's 评估类型 property 
     */
    public com.kingdee.eas.fdc.contract.EvaluationTypeInfo getType()
    {
        return (com.kingdee.eas.fdc.contract.EvaluationTypeInfo)get("type");
    }
    public void setType(com.kingdee.eas.fdc.contract.EvaluationTypeInfo item)
    {
        put("type", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F63B8289");
    }
}