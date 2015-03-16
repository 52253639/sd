package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class MediaInfo extends AbstractMediaInfo implements Serializable 
{
    public MediaInfo()
    {
        super();
    }
    protected MediaInfo(String pkField)
    {
        super(pkField);
    }
}