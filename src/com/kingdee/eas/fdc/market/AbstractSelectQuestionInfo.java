package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSelectQuestionInfo extends com.kingdee.eas.fdc.market.QuestionBaseInfo implements Serializable 
{
    public AbstractSelectQuestionInfo()
    {
        this("id");
    }
    protected AbstractSelectQuestionInfo(String pkField)
    {
        super(pkField);
        put("Item", new com.kingdee.eas.fdc.market.SelectQuestionItemCollection());
    }
    /**
     * Object: 选择题类型 's 选项 property 
     */
    public com.kingdee.eas.fdc.market.SelectQuestionItemCollection getItem()
    {
        return (com.kingdee.eas.fdc.market.SelectQuestionItemCollection)get("Item");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6D037D13");
    }
}