package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateMeasureCostCollection extends AbstractObjectCollection 
{
    public TemplateMeasureCostCollection()
    {
        super(TemplateMeasureCostInfo.class);
    }
    public boolean add(TemplateMeasureCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateMeasureCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateMeasureCostInfo item)
    {
        return removeObject(item);
    }
    public TemplateMeasureCostInfo get(int index)
    {
        return(TemplateMeasureCostInfo)getObject(index);
    }
    public TemplateMeasureCostInfo get(Object key)
    {
        return(TemplateMeasureCostInfo)getObject(key);
    }
    public void set(int index, TemplateMeasureCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateMeasureCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateMeasureCostInfo item)
    {
        return super.indexOf(item);
    }
}