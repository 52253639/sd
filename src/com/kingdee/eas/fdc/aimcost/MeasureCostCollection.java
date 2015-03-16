package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureCostCollection extends AbstractObjectCollection 
{
    public MeasureCostCollection()
    {
        super(MeasureCostInfo.class);
    }
    public boolean add(MeasureCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureCostInfo item)
    {
        return removeObject(item);
    }
    public MeasureCostInfo get(int index)
    {
        return(MeasureCostInfo)getObject(index);
    }
    public MeasureCostInfo get(Object key)
    {
        return(MeasureCostInfo)getObject(key);
    }
    public void set(int index, MeasureCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureCostInfo item)
    {
        return super.indexOf(item);
    }
}