package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DefaultWeekendEntryCollection extends AbstractObjectCollection 
{
    public DefaultWeekendEntryCollection()
    {
        super(DefaultWeekendEntryInfo.class);
    }
    public boolean add(DefaultWeekendEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DefaultWeekendEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DefaultWeekendEntryInfo item)
    {
        return removeObject(item);
    }
    public DefaultWeekendEntryInfo get(int index)
    {
        return(DefaultWeekendEntryInfo)getObject(index);
    }
    public DefaultWeekendEntryInfo get(Object key)
    {
        return(DefaultWeekendEntryInfo)getObject(key);
    }
    public void set(int index, DefaultWeekendEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DefaultWeekendEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DefaultWeekendEntryInfo item)
    {
        return super.indexOf(item);
    }
}