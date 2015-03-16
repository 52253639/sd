package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyBookEntryCollection extends AbstractObjectCollection 
{
    public RoomPropertyBookEntryCollection()
    {
        super(RoomPropertyBookEntryInfo.class);
    }
    public boolean add(RoomPropertyBookEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyBookEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyBookEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyBookEntryInfo get(int index)
    {
        return(RoomPropertyBookEntryInfo)getObject(index);
    }
    public RoomPropertyBookEntryInfo get(Object key)
    {
        return(RoomPropertyBookEntryInfo)getObject(key);
    }
    public void set(int index, RoomPropertyBookEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyBookEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyBookEntryInfo item)
    {
        return super.indexOf(item);
    }
}