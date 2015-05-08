package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeptMonthlyScheduleTaskCollection extends AbstractObjectCollection 
{
    public DeptMonthlyScheduleTaskCollection()
    {
        super(DeptMonthlyScheduleTaskInfo.class);
    }
    public boolean add(DeptMonthlyScheduleTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeptMonthlyScheduleTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeptMonthlyScheduleTaskInfo item)
    {
        return removeObject(item);
    }
    public DeptMonthlyScheduleTaskInfo get(int index)
    {
        return(DeptMonthlyScheduleTaskInfo)getObject(index);
    }
    public DeptMonthlyScheduleTaskInfo get(Object key)
    {
        return(DeptMonthlyScheduleTaskInfo)getObject(key);
    }
    public void set(int index, DeptMonthlyScheduleTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeptMonthlyScheduleTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeptMonthlyScheduleTaskInfo item)
    {
        return super.indexOf(item);
    }
}