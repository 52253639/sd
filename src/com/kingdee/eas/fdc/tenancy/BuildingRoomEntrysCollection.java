package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingRoomEntrysCollection extends AbstractObjectCollection 
{
    public BuildingRoomEntrysCollection()
    {
        super(BuildingRoomEntrysInfo.class);
    }
    public boolean add(BuildingRoomEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingRoomEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingRoomEntrysInfo item)
    {
        return removeObject(item);
    }
    public BuildingRoomEntrysInfo get(int index)
    {
        return(BuildingRoomEntrysInfo)getObject(index);
    }
    public BuildingRoomEntrysInfo get(Object key)
    {
        return(BuildingRoomEntrysInfo)getObject(key);
    }
    public void set(int index, BuildingRoomEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingRoomEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingRoomEntrysInfo item)
    {
        return super.indexOf(item);
    }
}