package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractJudgeQuestionAnswerInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractJudgeQuestionAnswerInfo()
    {
        this("id");
    }
    protected AbstractJudgeQuestionAnswerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: ´ð°¸ 's null property 
     */
    public com.kingdee.eas.fdc.market.JudgeQuestionInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.JudgeQuestionInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.JudgeQuestionInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:´ð°¸'s ´ð°¸property 
     */
    public String getAnswer()
    {
        return getString("answer");
    }
    public void setAnswer(String item)
    {
        setString("answer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("867068CA");
    }
}