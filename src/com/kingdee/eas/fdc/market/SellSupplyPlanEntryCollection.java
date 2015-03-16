package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SellSupplyPlanEntryCollection extends AbstractObjectCollection 
{
    public SellSupplyPlanEntryCollection()
    {
        super(SellSupplyPlanEntryInfo.class);
    }
    public boolean add(SellSupplyPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SellSupplyPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SellSupplyPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public SellSupplyPlanEntryInfo get(int index)
    {
        return(SellSupplyPlanEntryInfo)getObject(index);
    }
    public SellSupplyPlanEntryInfo get(Object key)
    {
        return(SellSupplyPlanEntryInfo)getObject(key);
    }
    public void set(int index, SellSupplyPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SellSupplyPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SellSupplyPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}