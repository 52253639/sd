package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BusinessOverViewInfo extends AbstractBusinessOverViewInfo implements Serializable 
{
    public BusinessOverViewInfo()
    {
        super();
    }
    protected BusinessOverViewInfo(String pkField)
    {
        super(pkField);
    }
}