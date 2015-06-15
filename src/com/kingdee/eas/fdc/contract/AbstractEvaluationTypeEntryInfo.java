package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationTypeEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEvaluationTypeEntryInfo()
    {
        this("id");
    }
    protected AbstractEvaluationTypeEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评估类型分录 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.EvaluationTypeInfo getHead()
    {
        return (com.kingdee.eas.fdc.contract.EvaluationTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.contract.EvaluationTypeInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 评估类型分录 's 评审结果 property 
     */
    public com.kingdee.eas.fdc.contract.EvaluationResultInfo getResult()
    {
        return (com.kingdee.eas.fdc.contract.EvaluationResultInfo)get("result");
    }
    public void setResult(com.kingdee.eas.fdc.contract.EvaluationResultInfo item)
    {
        put("result", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D2B51F61");
    }
}