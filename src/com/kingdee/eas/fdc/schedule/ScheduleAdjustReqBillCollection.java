package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleAdjustReqBillCollection extends AbstractObjectCollection 
{
    public ScheduleAdjustReqBillCollection()
    {
        super(ScheduleAdjustReqBillInfo.class);
    }
    public boolean add(ScheduleAdjustReqBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleAdjustReqBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleAdjustReqBillInfo item)
    {
        return removeObject(item);
    }
    public ScheduleAdjustReqBillInfo get(int index)
    {
        return(ScheduleAdjustReqBillInfo)getObject(index);
    }
    public ScheduleAdjustReqBillInfo get(Object key)
    {
        return(ScheduleAdjustReqBillInfo)getObject(key);
    }
    public void set(int index, ScheduleAdjustReqBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleAdjustReqBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleAdjustReqBillInfo item)
    {
        return super.indexOf(item);
    }
}