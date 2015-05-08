package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HolidayEntryCollection extends AbstractObjectCollection 
{
    public HolidayEntryCollection()
    {
        super(HolidayEntryInfo.class);
    }
    public boolean add(HolidayEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HolidayEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HolidayEntryInfo item)
    {
        return removeObject(item);
    }
    public HolidayEntryInfo get(int index)
    {
        return(HolidayEntryInfo)getObject(index);
    }
    public HolidayEntryInfo get(Object key)
    {
        return(HolidayEntryInfo)getObject(key);
    }
    public void set(int index, HolidayEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HolidayEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HolidayEntryInfo item)
    {
        return super.indexOf(item);
    }
}