package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class FDCReceiveBillEntryInfo extends AbstractFDCReceiveBillEntryInfo implements Serializable 
{
    public FDCReceiveBillEntryInfo()
    {
        super();
    }
    protected FDCReceiveBillEntryInfo(String pkField)
    {
        super(pkField);
    } 
}