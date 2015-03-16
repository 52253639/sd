package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class TrackRecordInfo extends AbstractTrackRecordInfo implements Serializable 
{
    public TrackRecordInfo()
    {
        super();
    }
    protected TrackRecordInfo(String pkField)
    {
        super(pkField);
    }
}