package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskWorkResultInfo extends com.kingdee.eas.fdc.schedule.SchedulePropBaseExtInfo implements Serializable 
{
    public AbstractTaskWorkResultInfo()
    {
        this("id");
    }
    protected AbstractTaskWorkResultInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.schedule.TaskWorkResultEntryCollection());
    }
    /**
     * Object: 任务工作成果 's 工作成果分录 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskWorkResultEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.schedule.TaskWorkResultEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E9B99749");
    }
}