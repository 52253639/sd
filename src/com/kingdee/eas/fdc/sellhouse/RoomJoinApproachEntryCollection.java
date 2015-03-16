package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomJoinApproachEntryCollection extends AbstractObjectCollection 
{
    public RoomJoinApproachEntryCollection()
    {
        super(RoomJoinApproachEntryInfo.class);
    }
    public boolean add(RoomJoinApproachEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomJoinApproachEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomJoinApproachEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomJoinApproachEntryInfo get(int index)
    {
        return(RoomJoinApproachEntryInfo)getObject(index);
    }
    public RoomJoinApproachEntryInfo get(Object key)
    {
        return(RoomJoinApproachEntryInfo)getObject(key);
    }
    public void set(int index, RoomJoinApproachEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomJoinApproachEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomJoinApproachEntryInfo item)
    {
        return super.indexOf(item);
    }
}