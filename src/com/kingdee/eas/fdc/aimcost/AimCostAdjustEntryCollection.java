package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostAdjustEntryCollection extends AbstractObjectCollection 
{
    public AimCostAdjustEntryCollection()
    {
        super(AimCostAdjustEntryInfo.class);
    }
    public boolean add(AimCostAdjustEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostAdjustEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostAdjustEntryInfo item)
    {
        return removeObject(item);
    }
    public AimCostAdjustEntryInfo get(int index)
    {
        return(AimCostAdjustEntryInfo)getObject(index);
    }
    public AimCostAdjustEntryInfo get(Object key)
    {
        return(AimCostAdjustEntryInfo)getObject(key);
    }
    public void set(int index, AimCostAdjustEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostAdjustEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostAdjustEntryInfo item)
    {
        return super.indexOf(item);
    }
}