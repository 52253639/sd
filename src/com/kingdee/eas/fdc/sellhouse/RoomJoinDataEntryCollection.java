package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomJoinDataEntryCollection extends AbstractObjectCollection 
{
    public RoomJoinDataEntryCollection()
    {
        super(RoomJoinDataEntryInfo.class);
    }
    public boolean add(RoomJoinDataEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomJoinDataEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomJoinDataEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomJoinDataEntryInfo get(int index)
    {
        return(RoomJoinDataEntryInfo)getObject(index);
    }
    public RoomJoinDataEntryInfo get(Object key)
    {
        return(RoomJoinDataEntryInfo)getObject(key);
    }
    public void set(int index, RoomJoinDataEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomJoinDataEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomJoinDataEntryInfo item)
    {
        return super.indexOf(item);
    }
}