package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionBaseInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractQuestionBaseInfo()
    {
        this("id");
    }
    protected AbstractQuestionBaseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:试题基类's 试题类型property 
     */
    public com.kingdee.eas.fdc.market.QuestionStyle getQuestionStyle()
    {
        return com.kingdee.eas.fdc.market.QuestionStyle.getEnum(getString("questionStyle"));
    }
    public void setQuestionStyle(com.kingdee.eas.fdc.market.QuestionStyle item)
    {
		if (item != null) {
        setString("questionStyle", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("652F8828");
    }
}