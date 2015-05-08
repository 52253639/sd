package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleAdjustGattReqCollection extends AbstractObjectCollection 
{
    public ScheduleAdjustGattReqCollection()
    {
        super(ScheduleAdjustGattReqInfo.class);
    }
    public boolean add(ScheduleAdjustGattReqInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleAdjustGattReqCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleAdjustGattReqInfo item)
    {
        return removeObject(item);
    }
    public ScheduleAdjustGattReqInfo get(int index)
    {
        return(ScheduleAdjustGattReqInfo)getObject(index);
    }
    public ScheduleAdjustGattReqInfo get(Object key)
    {
        return(ScheduleAdjustGattReqInfo)getObject(key);
    }
    public void set(int index, ScheduleAdjustGattReqInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleAdjustGattReqInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleAdjustGattReqInfo item)
    {
        return super.indexOf(item);
    }
}