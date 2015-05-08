package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskLoadEntryCollection extends AbstractObjectCollection 
{
    public TaskLoadEntryCollection()
    {
        super(TaskLoadEntryInfo.class);
    }
    public boolean add(TaskLoadEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskLoadEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskLoadEntryInfo item)
    {
        return removeObject(item);
    }
    public TaskLoadEntryInfo get(int index)
    {
        return(TaskLoadEntryInfo)getObject(index);
    }
    public TaskLoadEntryInfo get(Object key)
    {
        return(TaskLoadEntryInfo)getObject(key);
    }
    public void set(int index, TaskLoadEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskLoadEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskLoadEntryInfo item)
    {
        return super.indexOf(item);
    }
}