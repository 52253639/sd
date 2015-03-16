package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DynamicCostProductSplitEntryCollection extends AbstractObjectCollection 
{
    public DynamicCostProductSplitEntryCollection()
    {
        super(DynamicCostProductSplitEntryInfo.class);
    }
    public boolean add(DynamicCostProductSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DynamicCostProductSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DynamicCostProductSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public DynamicCostProductSplitEntryInfo get(int index)
    {
        return(DynamicCostProductSplitEntryInfo)getObject(index);
    }
    public DynamicCostProductSplitEntryInfo get(Object key)
    {
        return(DynamicCostProductSplitEntryInfo)getObject(key);
    }
    public void set(int index, DynamicCostProductSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DynamicCostProductSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DynamicCostProductSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}