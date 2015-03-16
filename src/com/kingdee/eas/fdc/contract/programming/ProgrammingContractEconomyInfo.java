package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class ProgrammingContractEconomyInfo extends AbstractProgrammingContractEconomyInfo implements Serializable 
{
    public ProgrammingContractEconomyInfo()
    {
        super();
    }
    protected ProgrammingContractEconomyInfo(String pkField)
    {
        super(pkField);
    }
}