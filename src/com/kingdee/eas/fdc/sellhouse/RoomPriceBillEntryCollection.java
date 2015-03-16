package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPriceBillEntryCollection extends AbstractObjectCollection 
{
    public RoomPriceBillEntryCollection()
    {
        super(RoomPriceBillEntryInfo.class);
    }
    public boolean add(RoomPriceBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPriceBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPriceBillEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomPriceBillEntryInfo get(int index)
    {
        return(RoomPriceBillEntryInfo)getObject(index);
    }
    public RoomPriceBillEntryInfo get(Object key)
    {
        return(RoomPriceBillEntryInfo)getObject(key);
    }
    public void set(int index, RoomPriceBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPriceBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPriceBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}