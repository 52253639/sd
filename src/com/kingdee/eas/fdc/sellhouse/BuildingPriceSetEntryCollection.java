package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingPriceSetEntryCollection extends AbstractObjectCollection 
{
    public BuildingPriceSetEntryCollection()
    {
        super(BuildingPriceSetEntryInfo.class);
    }
    public boolean add(BuildingPriceSetEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingPriceSetEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingPriceSetEntryInfo item)
    {
        return removeObject(item);
    }
    public BuildingPriceSetEntryInfo get(int index)
    {
        return(BuildingPriceSetEntryInfo)getObject(index);
    }
    public BuildingPriceSetEntryInfo get(Object key)
    {
        return(BuildingPriceSetEntryInfo)getObject(key);
    }
    public void set(int index, BuildingPriceSetEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingPriceSetEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingPriceSetEntryInfo item)
    {
        return super.indexOf(item);
    }
}