package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TaskCostInfoCollection extends AbstractObjectCollection 
{
    public TaskCostInfoCollection()
    {
        super(TaskCostInfoInfo.class);
    }
    public boolean add(TaskCostInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TaskCostInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TaskCostInfoInfo item)
    {
        return removeObject(item);
    }
    public TaskCostInfoInfo get(int index)
    {
        return(TaskCostInfoInfo)getObject(index);
    }
    public TaskCostInfoInfo get(Object key)
    {
        return(TaskCostInfoInfo)getObject(key);
    }
    public void set(int index, TaskCostInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TaskCostInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TaskCostInfoInfo item)
    {
        return super.indexOf(item);
    }
}