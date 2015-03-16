package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPriceAdjustEntryCollection extends AbstractObjectCollection 
{
    public RoomPriceAdjustEntryCollection()
    {
        super(RoomPriceAdjustEntryInfo.class);
    }
    public boolean add(RoomPriceAdjustEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPriceAdjustEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPriceAdjustEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomPriceAdjustEntryInfo get(int index)
    {
        return(RoomPriceAdjustEntryInfo)getObject(index);
    }
    public RoomPriceAdjustEntryInfo get(Object key)
    {
        return(RoomPriceAdjustEntryInfo)getObject(key);
    }
    public void set(int index, RoomPriceAdjustEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPriceAdjustEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPriceAdjustEntryInfo item)
    {
        return super.indexOf(item);
    }
}