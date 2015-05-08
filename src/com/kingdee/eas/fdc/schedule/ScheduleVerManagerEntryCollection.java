package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleVerManagerEntryCollection extends AbstractObjectCollection 
{
    public ScheduleVerManagerEntryCollection()
    {
        super(ScheduleVerManagerEntryInfo.class);
    }
    public boolean add(ScheduleVerManagerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleVerManagerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleVerManagerEntryInfo item)
    {
        return removeObject(item);
    }
    public ScheduleVerManagerEntryInfo get(int index)
    {
        return(ScheduleVerManagerEntryInfo)getObject(index);
    }
    public ScheduleVerManagerEntryInfo get(Object key)
    {
        return(ScheduleVerManagerEntryInfo)getObject(key);
    }
    public void set(int index, ScheduleVerManagerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleVerManagerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleVerManagerEntryInfo item)
    {
        return super.indexOf(item);
    }
}