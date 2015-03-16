package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BirdEyeInfo extends AbstractBirdEyeInfo implements Serializable 
{
    public BirdEyeInfo()
    {
        super();
    }
    protected BirdEyeInfo(String pkField)
    {
        super(pkField);
    }
}