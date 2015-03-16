package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MeasurePlanTargetEntryCollection extends AbstractObjectCollection 
{
    public MeasurePlanTargetEntryCollection()
    {
        super(MeasurePlanTargetEntryInfo.class);
    }
    public boolean add(MeasurePlanTargetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MeasurePlanTargetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MeasurePlanTargetEntryInfo item)
    {
        return removeObject(item);
    }
    public MeasurePlanTargetEntryInfo get(int index)
    {
        return(MeasurePlanTargetEntryInfo)getObject(index);
    }
    public MeasurePlanTargetEntryInfo get(Object key)
    {
        return(MeasurePlanTargetEntryInfo)getObject(key);
    }
    public void set(int index, MeasurePlanTargetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MeasurePlanTargetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MeasurePlanTargetEntryInfo item)
    {
        return super.indexOf(item);
    }
}