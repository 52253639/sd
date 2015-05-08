package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import java.util.Locale;

public class FDCWBSInfo extends AbstractFDCWBSInfo implements Serializable 
{
    public FDCWBSInfo()
    {
        super();
    }
    protected FDCWBSInfo(String pkField)
    {
        super(pkField);
    }
    
    public String getDisplayName()
    {
        return null;
    }
}