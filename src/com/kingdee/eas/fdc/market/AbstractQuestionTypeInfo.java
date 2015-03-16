package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractQuestionTypeInfo()
    {
        this("id");
    }
    protected AbstractQuestionTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 问卷类别 's 父节点 property 
     */
    public com.kingdee.eas.fdc.market.QuestionTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.QuestionTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.QuestionTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("65381091");
    }
}