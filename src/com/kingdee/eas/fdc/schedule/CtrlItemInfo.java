package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

public class CtrlItemInfo extends AbstractCtrlItemInfo implements Serializable 
{
    public CtrlItemInfo()
    {
        super();
    }
    protected CtrlItemInfo(String pkField)
    {
        super(pkField);
    }
}