package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class ValueInputInfo extends AbstractValueInputInfo implements Serializable 
{
    public ValueInputInfo()
    {
        super();
    }
    protected ValueInputInfo(String pkField)
    {
        super(pkField);
    }
}