package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostProductSplitEntryCollection extends AbstractObjectCollection 
{
    public AimCostProductSplitEntryCollection()
    {
        super(AimCostProductSplitEntryInfo.class);
    }
    public boolean add(AimCostProductSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostProductSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostProductSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public AimCostProductSplitEntryInfo get(int index)
    {
        return(AimCostProductSplitEntryInfo)getObject(index);
    }
    public AimCostProductSplitEntryInfo get(Object key)
    {
        return(AimCostProductSplitEntryInfo)getObject(key);
    }
    public void set(int index, AimCostProductSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostProductSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostProductSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}