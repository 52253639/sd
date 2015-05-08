package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCScheduleTaskCollection extends AbstractObjectCollection 
{
    public FDCScheduleTaskCollection()
    {
        super(FDCScheduleTaskInfo.class);
    }
    public boolean add(FDCScheduleTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCScheduleTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCScheduleTaskInfo item)
    {
        return removeObject(item);
    }
    public FDCScheduleTaskInfo get(int index)
    {
        return(FDCScheduleTaskInfo)getObject(index);
    }
    public FDCScheduleTaskInfo get(Object key)
    {
        return(FDCScheduleTaskInfo)getObject(key);
    }
    public void set(int index, FDCScheduleTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCScheduleTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCScheduleTaskInfo item)
    {
        return super.indexOf(item);
    }
}