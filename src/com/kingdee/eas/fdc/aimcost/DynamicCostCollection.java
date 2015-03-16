package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynamicCostCollection extends AbstractObjectCollection 
{
    public DynamicCostCollection()
    {
        super(DynamicCostInfo.class);
    }
    public boolean add(DynamicCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynamicCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynamicCostInfo item)
    {
        return removeObject(item);
    }
    public DynamicCostInfo get(int index)
    {
        return(DynamicCostInfo)getObject(index);
    }
    public DynamicCostInfo get(Object key)
    {
        return(DynamicCostInfo)getObject(key);
    }
    public void set(int index, DynamicCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynamicCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynamicCostInfo item)
    {
        return super.indexOf(item);
    }
}