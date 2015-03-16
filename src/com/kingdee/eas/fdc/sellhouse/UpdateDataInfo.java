package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class UpdateDataInfo extends AbstractUpdateDataInfo implements Serializable 
{
    public UpdateDataInfo()
    {
        super();
    }
    protected UpdateDataInfo(String pkField)
    {
        super(pkField);
    }
}