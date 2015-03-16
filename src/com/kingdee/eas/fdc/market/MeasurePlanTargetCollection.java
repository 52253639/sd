package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasurePlanTargetCollection extends AbstractObjectCollection 
{
    public MeasurePlanTargetCollection()
    {
        super(MeasurePlanTargetInfo.class);
    }
    public boolean add(MeasurePlanTargetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasurePlanTargetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasurePlanTargetInfo item)
    {
        return removeObject(item);
    }
    public MeasurePlanTargetInfo get(int index)
    {
        return(MeasurePlanTargetInfo)getObject(index);
    }
    public MeasurePlanTargetInfo get(Object key)
    {
        return(MeasurePlanTargetInfo)getObject(key);
    }
    public void set(int index, MeasurePlanTargetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasurePlanTargetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasurePlanTargetInfo item)
    {
        return super.indexOf(item);
    }
}