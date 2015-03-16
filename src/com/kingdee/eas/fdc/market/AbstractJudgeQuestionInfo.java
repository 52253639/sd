package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgeQuestionInfo extends com.kingdee.eas.fdc.market.QuestionBaseInfo implements Serializable 
{
    public AbstractJudgeQuestionInfo()
    {
        this("id");
    }
    protected AbstractJudgeQuestionInfo(String pkField)
    {
        super(pkField);
        put("Answer", new com.kingdee.eas.fdc.market.JudgeQuestionAnswerCollection());
    }
    /**
     * Object: ÅÐ¶ÏÌâ 's ´ð°¸ property 
     */
    public com.kingdee.eas.fdc.market.JudgeQuestionAnswerCollection getAnswer()
    {
        return (com.kingdee.eas.fdc.market.JudgeQuestionAnswerCollection)get("Answer");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("767C5DEC");
    }
}