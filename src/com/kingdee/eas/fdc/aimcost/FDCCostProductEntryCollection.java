package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCCostProductEntryCollection extends AbstractObjectCollection 
{
    public FDCCostProductEntryCollection()
    {
        super(FDCCostProductEntryInfo.class);
    }
    public boolean add(FDCCostProductEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCCostProductEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCCostProductEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCCostProductEntryInfo get(int index)
    {
        return(FDCCostProductEntryInfo)getObject(index);
    }
    public FDCCostProductEntryInfo get(Object key)
    {
        return(FDCCostProductEntryInfo)getObject(key);
    }
    public void set(int index, FDCCostProductEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCCostProductEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCCostProductEntryInfo item)
    {
        return super.indexOf(item);
    }
}