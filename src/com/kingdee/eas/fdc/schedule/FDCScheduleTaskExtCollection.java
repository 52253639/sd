package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCScheduleTaskExtCollection extends AbstractObjectCollection 
{
    public FDCScheduleTaskExtCollection()
    {
        super(FDCScheduleTaskExtInfo.class);
    }
    public boolean add(FDCScheduleTaskExtInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCScheduleTaskExtCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCScheduleTaskExtInfo item)
    {
        return removeObject(item);
    }
    public FDCScheduleTaskExtInfo get(int index)
    {
        return(FDCScheduleTaskExtInfo)getObject(index);
    }
    public FDCScheduleTaskExtInfo get(Object key)
    {
        return(FDCScheduleTaskExtInfo)getObject(key);
    }
    public void set(int index, FDCScheduleTaskExtInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCScheduleTaskExtInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCScheduleTaskExtInfo item)
    {
        return super.indexOf(item);
    }
}