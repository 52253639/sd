package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPriceBuildingEntryCollection extends AbstractObjectCollection 
{
    public RoomPriceBuildingEntryCollection()
    {
        super(RoomPriceBuildingEntryInfo.class);
    }
    public boolean add(RoomPriceBuildingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPriceBuildingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPriceBuildingEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomPriceBuildingEntryInfo get(int index)
    {
        return(RoomPriceBuildingEntryInfo)getObject(index);
    }
    public RoomPriceBuildingEntryInfo get(Object key)
    {
        return(RoomPriceBuildingEntryInfo)getObject(key);
    }
    public void set(int index, RoomPriceBuildingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPriceBuildingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPriceBuildingEntryInfo item)
    {
        return super.indexOf(item);
    }
}