package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskWorkResultEntryFileCollection extends AbstractObjectCollection 
{
    public TaskWorkResultEntryFileCollection()
    {
        super(TaskWorkResultEntryFileInfo.class);
    }
    public boolean add(TaskWorkResultEntryFileInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskWorkResultEntryFileCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskWorkResultEntryFileInfo item)
    {
        return removeObject(item);
    }
    public TaskWorkResultEntryFileInfo get(int index)
    {
        return(TaskWorkResultEntryFileInfo)getObject(index);
    }
    public TaskWorkResultEntryFileInfo get(Object key)
    {
        return(TaskWorkResultEntryFileInfo)getObject(key);
    }
    public void set(int index, TaskWorkResultEntryFileInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskWorkResultEntryFileInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskWorkResultEntryFileInfo item)
    {
        return super.indexOf(item);
    }
}