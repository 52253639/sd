package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCostLogCollection extends AbstractObjectCollection 
{
    public FDCCostLogCollection()
    {
        super(FDCCostLogInfo.class);
    }
    public boolean add(FDCCostLogInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCostLogCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCostLogInfo item)
    {
        return removeObject(item);
    }
    public FDCCostLogInfo get(int index)
    {
        return(FDCCostLogInfo)getObject(index);
    }
    public FDCCostLogInfo get(Object key)
    {
        return(FDCCostLogInfo)getObject(key);
    }
    public void set(int index, FDCCostLogInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCostLogInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCostLogInfo item)
    {
        return super.indexOf(item);
    }
}