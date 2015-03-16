package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPayEntryCollection extends AbstractObjectCollection 
{
    public RoomPayEntryCollection()
    {
        super(RoomPayEntryInfo.class);
    }
    public boolean add(RoomPayEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPayEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPayEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomPayEntryInfo get(int index)
    {
        return(RoomPayEntryInfo)getObject(index);
    }
    public RoomPayEntryInfo get(Object key)
    {
        return(RoomPayEntryInfo)getObject(key);
    }
    public void set(int index, RoomPayEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPayEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPayEntryInfo item)
    {
        return super.indexOf(item);
    }
}