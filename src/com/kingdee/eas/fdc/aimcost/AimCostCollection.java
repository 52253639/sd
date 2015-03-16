package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AimCostCollection extends AbstractObjectCollection 
{
    public AimCostCollection()
    {
        super(AimCostInfo.class);
    }
    public boolean add(AimCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AimCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AimCostInfo item)
    {
        return removeObject(item);
    }
    public AimCostInfo get(int index)
    {
        return(AimCostInfo)getObject(index);
    }
    public AimCostInfo get(Object key)
    {
        return(AimCostInfo)getObject(key);
    }
    public void set(int index, AimCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AimCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AimCostInfo item)
    {
        return super.indexOf(item);
    }
}