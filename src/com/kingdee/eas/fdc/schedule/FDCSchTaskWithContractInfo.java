package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class FDCSchTaskWithContractInfo extends AbstractFDCSchTaskWithContractInfo implements Serializable 
{
    public FDCSchTaskWithContractInfo()
    {
        super();
    }
    protected FDCSchTaskWithContractInfo(String pkField)
    {
        super(pkField);
    }
}