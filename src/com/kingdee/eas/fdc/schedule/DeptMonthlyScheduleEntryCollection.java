package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DeptMonthlyScheduleEntryCollection extends AbstractObjectCollection 
{
    public DeptMonthlyScheduleEntryCollection()
    {
        super(DeptMonthlyScheduleEntryInfo.class);
    }
    public boolean add(DeptMonthlyScheduleEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DeptMonthlyScheduleEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DeptMonthlyScheduleEntryInfo item)
    {
        return removeObject(item);
    }
    public DeptMonthlyScheduleEntryInfo get(int index)
    {
        return(DeptMonthlyScheduleEntryInfo)getObject(index);
    }
    public DeptMonthlyScheduleEntryInfo get(Object key)
    {
        return(DeptMonthlyScheduleEntryInfo)getObject(key);
    }
    public void set(int index, DeptMonthlyScheduleEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DeptMonthlyScheduleEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DeptMonthlyScheduleEntryInfo item)
    {
        return super.indexOf(item);
    }
}