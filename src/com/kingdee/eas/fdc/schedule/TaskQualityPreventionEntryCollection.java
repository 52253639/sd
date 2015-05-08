package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskQualityPreventionEntryCollection extends AbstractObjectCollection 
{
    public TaskQualityPreventionEntryCollection()
    {
        super(TaskQualityPreventionEntryInfo.class);
    }
    public boolean add(TaskQualityPreventionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskQualityPreventionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskQualityPreventionEntryInfo item)
    {
        return removeObject(item);
    }
    public TaskQualityPreventionEntryInfo get(int index)
    {
        return(TaskQualityPreventionEntryInfo)getObject(index);
    }
    public TaskQualityPreventionEntryInfo get(Object key)
    {
        return(TaskQualityPreventionEntryInfo)getObject(key);
    }
    public void set(int index, TaskQualityPreventionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskQualityPreventionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskQualityPreventionEntryInfo item)
    {
        return super.indexOf(item);
    }
}