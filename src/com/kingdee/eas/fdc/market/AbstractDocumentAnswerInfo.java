package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDocumentAnswerInfo extends com.kingdee.eas.framework.BillBaseInfo implements Serializable 
{
    public AbstractDocumentAnswerInfo()
    {
        this("id");
    }
    protected AbstractDocumentAnswerInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:问卷回答's 问卷IDproperty 
     */
    public String getDocumentId()
    {
        return getString("documentId");
    }
    public void setDocumentId(String item)
    {
        setString("documentId", item);
    }
    /**
     * Object:问卷回答's 题目IDproperty 
     */
    public String getSubjectId()
    {
        return getString("subjectId");
    }
    public void setSubjectId(String item)
    {
        setString("subjectId", item);
    }
    /**
     * Object:问卷回答's 分组IDproperty 
     */
    public String getItemId()
    {
        return getString("itemId");
    }
    public void setItemId(String item)
    {
        setString("itemId", item);
    }
    /**
     * Object:问卷回答's 选项IDproperty 
     */
    public String getOptionId()
    {
        return getString("optionId");
    }
    public void setOptionId(String item)
    {
        setString("optionId", item);
    }
    /**
     * Object:问卷回答's 输入的字符串property 
     */
    public String getInputString()
    {
        return getString("inputString");
    }
    public void setInputString(String item)
    {
        setString("inputString", item);
    }
    /**
     * Object:问卷回答's 输入数字property 
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
        return new BOSObjectType("B7F1E0CA");
    }
}