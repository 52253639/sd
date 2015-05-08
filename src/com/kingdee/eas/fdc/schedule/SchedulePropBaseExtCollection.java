package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SchedulePropBaseExtCollection extends AbstractObjectCollection 
{
    public SchedulePropBaseExtCollection()
    {
        super(SchedulePropBaseExtInfo.class);
    }
    public boolean add(SchedulePropBaseExtInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SchedulePropBaseExtCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SchedulePropBaseExtInfo item)
    {
        return removeObject(item);
    }
    public SchedulePropBaseExtInfo get(int index)
    {
        return(SchedulePropBaseExtInfo)getObject(index);
    }
    public SchedulePropBaseExtInfo get(Object key)
    {
        return(SchedulePropBaseExtInfo)getObject(key);
    }
    public void set(int index, SchedulePropBaseExtInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SchedulePropBaseExtInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SchedulePropBaseExtInfo item)
    {
        return super.indexOf(item);
    }
}