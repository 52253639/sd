package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingPriceSetCollection extends AbstractObjectCollection 
{
    public BuildingPriceSetCollection()
    {
        super(BuildingPriceSetInfo.class);
    }
    public boolean add(BuildingPriceSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingPriceSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingPriceSetInfo item)
    {
        return removeObject(item);
    }
    public BuildingPriceSetInfo get(int index)
    {
        return(BuildingPriceSetInfo)getObject(index);
    }
    public BuildingPriceSetInfo get(Object key)
    {
        return(BuildingPriceSetInfo)getObject(key);
    }
    public void set(int index, BuildingPriceSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingPriceSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingPriceSetInfo item)
    {
        return super.indexOf(item);
    }
}