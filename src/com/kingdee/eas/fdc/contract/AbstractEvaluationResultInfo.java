package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEvaluationResultInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractEvaluationResultInfo()
    {
        this("id");
    }
    protected AbstractEvaluationResultInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("78E20AB4");
    }
}