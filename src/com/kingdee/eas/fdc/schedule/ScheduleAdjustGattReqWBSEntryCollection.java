package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleAdjustGattReqWBSEntryCollection extends AbstractObjectCollection 
{
    public ScheduleAdjustGattReqWBSEntryCollection()
    {
        super(ScheduleAdjustGattReqWBSEntryInfo.class);
    }
    public boolean add(ScheduleAdjustGattReqWBSEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleAdjustGattReqWBSEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleAdjustGattReqWBSEntryInfo item)
    {
        return removeObject(item);
    }
    public ScheduleAdjustGattReqWBSEntryInfo get(int index)
    {
        return(ScheduleAdjustGattReqWBSEntryInfo)getObject(index);
    }
    public ScheduleAdjustGattReqWBSEntryInfo get(Object key)
    {
        return(ScheduleAdjustGattReqWBSEntryInfo)getObject(key);
    }
    public void set(int index, ScheduleAdjustGattReqWBSEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleAdjustGattReqWBSEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleAdjustGattReqWBSEntryInfo item)
    {
        return super.indexOf(item);
    }
}