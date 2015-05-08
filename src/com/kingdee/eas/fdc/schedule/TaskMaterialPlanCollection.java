package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskMaterialPlanCollection extends AbstractObjectCollection 
{
    public TaskMaterialPlanCollection()
    {
        super(TaskMaterialPlanInfo.class);
    }
    public boolean add(TaskMaterialPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskMaterialPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskMaterialPlanInfo item)
    {
        return removeObject(item);
    }
    public TaskMaterialPlanInfo get(int index)
    {
        return(TaskMaterialPlanInfo)getObject(index);
    }
    public TaskMaterialPlanInfo get(Object key)
    {
        return(TaskMaterialPlanInfo)getObject(key);
    }
    public void set(int index, TaskMaterialPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskMaterialPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskMaterialPlanInfo item)
    {
        return super.indexOf(item);
    }
}