package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskQualityPlanEventEntryCollection extends AbstractObjectCollection 
{
    public TaskQualityPlanEventEntryCollection()
    {
        super(TaskQualityPlanEventEntryInfo.class);
    }
    public boolean add(TaskQualityPlanEventEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskQualityPlanEventEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskQualityPlanEventEntryInfo item)
    {
        return removeObject(item);
    }
    public TaskQualityPlanEventEntryInfo get(int index)
    {
        return(TaskQualityPlanEventEntryInfo)getObject(index);
    }
    public TaskQualityPlanEventEntryInfo get(Object key)
    {
        return(TaskQualityPlanEventEntryInfo)getObject(key);
    }
    public void set(int index, TaskQualityPlanEventEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskQualityPlanEventEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskQualityPlanEventEntryInfo item)
    {
        return super.indexOf(item);
    }
}