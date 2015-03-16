package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SellSupplyPlanCollection extends AbstractObjectCollection 
{
    public SellSupplyPlanCollection()
    {
        super(SellSupplyPlanInfo.class);
    }
    public boolean add(SellSupplyPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SellSupplyPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SellSupplyPlanInfo item)
    {
        return removeObject(item);
    }
    public SellSupplyPlanInfo get(int index)
    {
        return(SellSupplyPlanInfo)getObject(index);
    }
    public SellSupplyPlanInfo get(Object key)
    {
        return(SellSupplyPlanInfo)getObject(key);
    }
    public void set(int index, SellSupplyPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SellSupplyPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SellSupplyPlanInfo item)
    {
        return super.indexOf(item);
    }
}