package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasureCostCompareCollection extends AbstractObjectCollection 
{
    public MeasureCostCompareCollection()
    {
        super(MeasureCostCompareInfo.class);
    }
    public boolean add(MeasureCostCompareInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasureCostCompareCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasureCostCompareInfo item)
    {
        return removeObject(item);
    }
    public MeasureCostCompareInfo get(int index)
    {
        return(MeasureCostCompareInfo)getObject(index);
    }
    public MeasureCostCompareInfo get(Object key)
    {
        return(MeasureCostCompareInfo)getObject(key);
    }
    public void set(int index, MeasureCostCompareInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasureCostCompareInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasureCostCompareInfo item)
    {
        return super.indexOf(item);
    }
}