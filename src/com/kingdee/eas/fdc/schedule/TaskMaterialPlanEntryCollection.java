package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskMaterialPlanEntryCollection extends AbstractObjectCollection 
{
    public TaskMaterialPlanEntryCollection()
    {
        super(TaskMaterialPlanEntryInfo.class);
    }
    public boolean add(TaskMaterialPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskMaterialPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskMaterialPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public TaskMaterialPlanEntryInfo get(int index)
    {
        return(TaskMaterialPlanEntryInfo)getObject(index);
    }
    public TaskMaterialPlanEntryInfo get(Object key)
    {
        return(TaskMaterialPlanEntryInfo)getObject(key);
    }
    public void set(int index, TaskMaterialPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskMaterialPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskMaterialPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}