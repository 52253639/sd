package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingCollection extends AbstractObjectCollection 
{
    public BuildingCollection()
    {
        super(BuildingInfo.class);
    }
    public boolean add(BuildingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingInfo item)
    {
        return removeObject(item);
    }
    public BuildingInfo get(int index)
    {
        return(BuildingInfo)getObject(index);
    }
    public BuildingInfo get(Object key)
    {
        return(BuildingInfo)getObject(key);
    }
    public void set(int index, BuildingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingInfo item)
    {
        return super.indexOf(item);
    }
}