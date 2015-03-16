package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingFloorEntryCollection extends AbstractObjectCollection 
{
    public BuildingFloorEntryCollection()
    {
        super(BuildingFloorEntryInfo.class);
    }
    public boolean add(BuildingFloorEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingFloorEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingFloorEntryInfo item)
    {
        return removeObject(item);
    }
    public BuildingFloorEntryInfo get(int index)
    {
        return(BuildingFloorEntryInfo)getObject(index);
    }
    public BuildingFloorEntryInfo get(Object key)
    {
        return(BuildingFloorEntryInfo)getObject(key);
    }
    public void set(int index, BuildingFloorEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingFloorEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingFloorEntryInfo item)
    {
        return super.indexOf(item);
    }
}