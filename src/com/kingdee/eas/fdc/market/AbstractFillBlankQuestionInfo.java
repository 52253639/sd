package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFillBlankQuestionInfo extends com.kingdee.eas.fdc.market.QuestionBaseInfo implements Serializable 
{
    public AbstractFillBlankQuestionInfo()
    {
        this("id");
    }
    protected AbstractFillBlankQuestionInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2389FAE6");
    }
}