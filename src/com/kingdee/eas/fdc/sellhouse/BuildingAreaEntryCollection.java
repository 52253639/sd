package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingAreaEntryCollection extends AbstractObjectCollection 
{
    public BuildingAreaEntryCollection()
    {
        super(BuildingAreaEntryInfo.class);
    }
    public boolean add(BuildingAreaEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingAreaEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingAreaEntryInfo item)
    {
        return removeObject(item);
    }
    public BuildingAreaEntryInfo get(int index)
    {
        return(BuildingAreaEntryInfo)getObject(index);
    }
    public BuildingAreaEntryInfo get(Object key)
    {
        return(BuildingAreaEntryInfo)getObject(key);
    }
    public void set(int index, BuildingAreaEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingAreaEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingAreaEntryInfo item)
    {
        return super.indexOf(item);
    }
}