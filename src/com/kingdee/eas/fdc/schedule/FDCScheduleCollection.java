package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCScheduleCollection extends AbstractObjectCollection 
{
    public FDCScheduleCollection()
    {
        super(FDCScheduleInfo.class);
    }
    public boolean add(FDCScheduleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCScheduleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCScheduleInfo item)
    {
        return removeObject(item);
    }
    public FDCScheduleInfo get(int index)
    {
        return(FDCScheduleInfo)getObject(index);
    }
    public FDCScheduleInfo get(Object key)
    {
        return(FDCScheduleInfo)getObject(key);
    }
    public void set(int index, FDCScheduleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCScheduleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCScheduleInfo item)
    {
        return super.indexOf(item);
    }
}