package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class HeadColumnInfo extends AbstractHeadColumnInfo implements Serializable 
{
    public HeadColumnInfo()
    {
        super();
    }
    protected HeadColumnInfo(String pkField)
    {
        super(pkField);
    }
}