package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class BatchRoomEntryInfo extends AbstractBatchRoomEntryInfo implements Serializable 
{
    public BatchRoomEntryInfo()
    {
        super();
    }
    protected BatchRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
}