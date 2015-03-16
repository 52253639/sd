package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class ProgrammingContractInfo extends AbstractProgrammingContractInfo implements Serializable 
{
    public ProgrammingContractInfo()
    {
        super();
    }
    protected ProgrammingContractInfo(String pkField)
    {
        super(pkField);
    }
}