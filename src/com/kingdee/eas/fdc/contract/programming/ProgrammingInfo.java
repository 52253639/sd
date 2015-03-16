package com.kingdee.eas.fdc.contract.programming;

import java.io.Serializable;

public class ProgrammingInfo extends AbstractProgrammingInfo implements Serializable 
{
    public ProgrammingInfo()
    {
        super();
    }
    protected ProgrammingInfo(String pkField)
    {
        super(pkField);
    }
    
    public void setEntries(ProgrammingContractCollection collection) {
    	put("entries", collection);
    }
}