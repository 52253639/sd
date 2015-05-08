package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SchedulePropBaseCollection extends AbstractObjectCollection 
{
    public SchedulePropBaseCollection()
    {
        super(SchedulePropBaseInfo.class);
    }
    public boolean add(SchedulePropBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SchedulePropBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SchedulePropBaseInfo item)
    {
        return removeObject(item);
    }
    public SchedulePropBaseInfo get(int index)
    {
        return(SchedulePropBaseInfo)getObject(index);
    }
    public SchedulePropBaseInfo get(Object key)
    {
        return(SchedulePropBaseInfo)getObject(key);
    }
    public void set(int index, SchedulePropBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SchedulePropBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SchedulePropBaseInfo item)
    {
        return super.indexOf(item);
    }
}