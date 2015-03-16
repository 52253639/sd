package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class ProgrammingTemplateInfo extends AbstractProgrammingTemplateInfo implements Serializable 
{
    public ProgrammingTemplateInfo()
    {
        super();
    }
    protected ProgrammingTemplateInfo(String pkField)
    {
        super(pkField);
    }
}