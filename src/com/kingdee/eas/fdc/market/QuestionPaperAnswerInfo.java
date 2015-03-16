package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class QuestionPaperAnswerInfo extends AbstractQuestionPaperAnswerInfo implements Serializable 
{
    public QuestionPaperAnswerInfo()
    {
        super();
    }
    protected QuestionPaperAnswerInfo(String pkField)
    {
        super(pkField);
    }
}