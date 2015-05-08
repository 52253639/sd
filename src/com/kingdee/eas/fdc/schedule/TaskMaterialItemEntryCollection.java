package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskMaterialItemEntryCollection extends AbstractObjectCollection 
{
    public TaskMaterialItemEntryCollection()
    {
        super(TaskMaterialItemEntryInfo.class);
    }
    public boolean add(TaskMaterialItemEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskMaterialItemEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskMaterialItemEntryInfo item)
    {
        return removeObject(item);
    }
    public TaskMaterialItemEntryInfo get(int index)
    {
        return(TaskMaterialItemEntryInfo)getObject(index);
    }
    public TaskMaterialItemEntryInfo get(Object key)
    {
        return(TaskMaterialItemEntryInfo)getObject(key);
    }
    public void set(int index, TaskMaterialItemEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskMaterialItemEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskMaterialItemEntryInfo item)
    {
        return super.indexOf(item);
    }
}