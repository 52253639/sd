package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostProdcutSplitEntryCollection extends AbstractObjectCollection 
{
    public AimCostProdcutSplitEntryCollection()
    {
        super(AimCostProdcutSplitEntryInfo.class);
    }
    public boolean add(AimCostProdcutSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostProdcutSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostProdcutSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public AimCostProdcutSplitEntryInfo get(int index)
    {
        return(AimCostProdcutSplitEntryInfo)getObject(index);
    }
    public AimCostProdcutSplitEntryInfo get(Object key)
    {
        return(AimCostProdcutSplitEntryInfo)getObject(key);
    }
    public void set(int index, AimCostProdcutSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostProdcutSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostProdcutSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}