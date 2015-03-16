package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class QuestionBaseInfo extends AbstractQuestionBaseInfo implements Serializable 
{
    public QuestionBaseInfo()
    {
        super();
    }
    protected QuestionBaseInfo(String pkField)
    {
        super(pkField);
    }
}