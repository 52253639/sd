package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class HabitationRecordInfo extends AbstractHabitationRecordInfo implements Serializable 
{
    public HabitationRecordInfo()
    {
        super();
    }
    protected HabitationRecordInfo(String pkField)
    {
        super(pkField);
    }
}