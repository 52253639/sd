package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingEntryCollection extends AbstractObjectCollection 
{
    public BuildingEntryCollection()
    {
        super(BuildingEntryInfo.class);
    }
    public boolean add(BuildingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingEntryInfo item)
    {
        return removeObject(item);
    }
    public BuildingEntryInfo get(int index)
    {
        return(BuildingEntryInfo)getObject(index);
    }
    public BuildingEntryInfo get(Object key)
    {
        return(BuildingEntryInfo)getObject(key);
    }
    public void set(int index, BuildingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingEntryInfo item)
    {
        return super.indexOf(item);
    }
}