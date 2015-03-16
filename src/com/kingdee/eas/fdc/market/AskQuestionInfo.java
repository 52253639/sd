package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class AskQuestionInfo extends AbstractAskQuestionInfo implements Serializable 
{
    public AskQuestionInfo()
    {
        super();
    }
    protected AskQuestionInfo(String pkField)
    {
        super(pkField);
    }
}