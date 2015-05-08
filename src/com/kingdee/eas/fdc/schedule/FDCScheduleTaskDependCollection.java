package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCScheduleTaskDependCollection extends AbstractObjectCollection 
{
    public FDCScheduleTaskDependCollection()
    {
        super(FDCScheduleTaskDependInfo.class);
    }
    public boolean add(FDCScheduleTaskDependInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCScheduleTaskDependCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCScheduleTaskDependInfo item)
    {
        return removeObject(item);
    }
    public FDCScheduleTaskDependInfo get(int index)
    {
        return(FDCScheduleTaskDependInfo)getObject(index);
    }
    public FDCScheduleTaskDependInfo get(Object key)
    {
        return(FDCScheduleTaskDependInfo)getObject(key);
    }
    public void set(int index, FDCScheduleTaskDependInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCScheduleTaskDependInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCScheduleTaskDependInfo item)
    {
        return super.indexOf(item);
    }
}