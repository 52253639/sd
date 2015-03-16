package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractQuestionPaperAnswerEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractQuestionPaperAnswerEntryInfo()
    {
        this("id");
    }
    protected AbstractQuestionPaperAnswerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo)get("Parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo item)
    {
        put("Parent", item);
    }
    /**
     * Object:分录's 选项property 
     */
    public String getOption()
    {
        return getString("option");
    }
    public void setOption(String item)
    {
        setString("option", item);
    }
    /**
     * Object:分录's 输入文本property 
     */
    public String getInputStr()
    {
        return getString("inputStr");
    }
    public void setInputStr(String item)
    {
        setString("inputStr", item);
    }
    /**
     * Object:分录's 输入数字property 
     */
    public java.math.BigDecimal getInputNumber()
    {
        return getBigDecimal("inputNumber");
    }
    public void setInputNumber(java.math.BigDecimal item)
    {
        setBigDecimal("inputNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("63809CFF");
    }
}