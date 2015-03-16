package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCostCollection extends AbstractObjectCollection 
{
    public FDCCostCollection()
    {
        super(FDCCostInfo.class);
    }
    public boolean add(FDCCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCostInfo item)
    {
        return removeObject(item);
    }
    public FDCCostInfo get(int index)
    {
        return(FDCCostInfo)getObject(index);
    }
    public FDCCostInfo get(Object key)
    {
        return(FDCCostInfo)getObject(key);
    }
    public void set(int index, FDCCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCostInfo item)
    {
        return super.indexOf(item);
    }
}