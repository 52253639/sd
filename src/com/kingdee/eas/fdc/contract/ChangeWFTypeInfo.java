package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ChangeWFTypeInfo extends AbstractChangeWFTypeInfo implements Serializable 
{
    public ChangeWFTypeInfo()
    {
        super();
    }
    protected ChangeWFTypeInfo(String pkField)
    {
        super(pkField);
    }
}