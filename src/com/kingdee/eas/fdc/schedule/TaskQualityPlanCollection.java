package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskQualityPlanCollection extends AbstractObjectCollection 
{
    public TaskQualityPlanCollection()
    {
        super(TaskQualityPlanInfo.class);
    }
    public boolean add(TaskQualityPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskQualityPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskQualityPlanInfo item)
    {
        return removeObject(item);
    }
    public TaskQualityPlanInfo get(int index)
    {
        return(TaskQualityPlanInfo)getObject(index);
    }
    public TaskQualityPlanInfo get(Object key)
    {
        return(TaskQualityPlanInfo)getObject(key);
    }
    public void set(int index, TaskQualityPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskQualityPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskQualityPlanInfo item)
    {
        return super.indexOf(item);
    }
}