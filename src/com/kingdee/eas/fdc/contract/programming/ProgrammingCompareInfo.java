package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class ProgrammingCompareInfo extends AbstractProgrammingCompareInfo implements Serializable 
{
    public ProgrammingCompareInfo()
    {
        super();
    }
    protected ProgrammingCompareInfo(String pkField)
    {
        super(pkField);
    }
}