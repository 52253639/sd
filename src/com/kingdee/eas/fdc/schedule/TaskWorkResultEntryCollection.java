package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskWorkResultEntryCollection extends AbstractObjectCollection 
{
    public TaskWorkResultEntryCollection()
    {
        super(TaskWorkResultEntryInfo.class);
    }
    public boolean add(TaskWorkResultEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskWorkResultEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskWorkResultEntryInfo item)
    {
        return removeObject(item);
    }
    public TaskWorkResultEntryInfo get(int index)
    {
        return(TaskWorkResultEntryInfo)getObject(index);
    }
    public TaskWorkResultEntryInfo get(Object key)
    {
        return(TaskWorkResultEntryInfo)getObject(key);
    }
    public void set(int index, TaskWorkResultEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskWorkResultEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskWorkResultEntryInfo item)
    {
        return super.indexOf(item);
    }
}