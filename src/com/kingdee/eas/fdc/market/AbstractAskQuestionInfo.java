package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAskQuestionInfo extends com.kingdee.eas.fdc.market.QuestionBaseInfo implements Serializable 
{
    public AbstractAskQuestionInfo()
    {
        this("id");
    }
    protected AbstractAskQuestionInfo(String pkField)
    {
        super(pkField);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("55D20F0E");
    }
}