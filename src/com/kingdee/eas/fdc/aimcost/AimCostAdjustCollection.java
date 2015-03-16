package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostAdjustCollection extends AbstractObjectCollection 
{
    public AimCostAdjustCollection()
    {
        super(AimCostAdjustInfo.class);
    }
    public boolean add(AimCostAdjustInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostAdjustCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostAdjustInfo item)
    {
        return removeObject(item);
    }
    public AimCostAdjustInfo get(int index)
    {
        return(AimCostAdjustInfo)getObject(index);
    }
    public AimCostAdjustInfo get(Object key)
    {
        return(AimCostAdjustInfo)getObject(key);
    }
    public void set(int index, AimCostAdjustInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostAdjustInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostAdjustInfo item)
    {
        return super.indexOf(item);
    }
}