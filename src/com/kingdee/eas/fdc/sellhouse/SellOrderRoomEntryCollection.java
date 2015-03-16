package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SellOrderRoomEntryCollection extends AbstractObjectCollection 
{
    public SellOrderRoomEntryCollection()
    {
        super(SellOrderRoomEntryInfo.class);
    }
    public boolean add(SellOrderRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SellOrderRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SellOrderRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public SellOrderRoomEntryInfo get(int index)
    {
        return(SellOrderRoomEntryInfo)getObject(index);
    }
    public SellOrderRoomEntryInfo get(Object key)
    {
        return(SellOrderRoomEntryInfo)getObject(key);
    }
    public void set(int index, SellOrderRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SellOrderRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SellOrderRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}