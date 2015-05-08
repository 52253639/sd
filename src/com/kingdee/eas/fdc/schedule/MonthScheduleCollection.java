package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MonthScheduleCollection extends AbstractObjectCollection 
{
    public MonthScheduleCollection()
    {
        super(MonthScheduleInfo.class);
    }
    public boolean add(MonthScheduleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MonthScheduleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MonthScheduleInfo item)
    {
        return removeObject(item);
    }
    public MonthScheduleInfo get(int index)
    {
        return(MonthScheduleInfo)getObject(index);
    }
    public MonthScheduleInfo get(Object key)
    {
        return(MonthScheduleInfo)getObject(key);
    }
    public void set(int index, MonthScheduleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MonthScheduleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MonthScheduleInfo item)
    {
        return super.indexOf(item);
    }
}