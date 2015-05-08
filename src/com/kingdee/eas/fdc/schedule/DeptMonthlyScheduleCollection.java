package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeptMonthlyScheduleCollection extends AbstractObjectCollection 
{
    public DeptMonthlyScheduleCollection()
    {
        super(DeptMonthlyScheduleInfo.class);
    }
    public boolean add(DeptMonthlyScheduleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeptMonthlyScheduleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeptMonthlyScheduleInfo item)
    {
        return removeObject(item);
    }
    public DeptMonthlyScheduleInfo get(int index)
    {
        return(DeptMonthlyScheduleInfo)getObject(index);
    }
    public DeptMonthlyScheduleInfo get(Object key)
    {
        return(DeptMonthlyScheduleInfo)getObject(key);
    }
    public void set(int index, DeptMonthlyScheduleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeptMonthlyScheduleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeptMonthlyScheduleInfo item)
    {
        return super.indexOf(item);
    }
}