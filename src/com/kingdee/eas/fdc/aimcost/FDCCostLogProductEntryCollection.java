package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCostLogProductEntryCollection extends AbstractObjectCollection 
{
    public FDCCostLogProductEntryCollection()
    {
        super(FDCCostLogProductEntryInfo.class);
    }
    public boolean add(FDCCostLogProductEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCostLogProductEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCostLogProductEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCCostLogProductEntryInfo get(int index)
    {
        return(FDCCostLogProductEntryInfo)getObject(index);
    }
    public FDCCostLogProductEntryInfo get(Object key)
    {
        return(FDCCostLogProductEntryInfo)getObject(key);
    }
    public void set(int index, FDCCostLogProductEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCostLogProductEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCostLogProductEntryInfo item)
    {
        return super.indexOf(item);
    }
}