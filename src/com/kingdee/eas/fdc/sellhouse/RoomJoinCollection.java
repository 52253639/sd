package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomJoinCollection extends AbstractObjectCollection 
{
    public RoomJoinCollection()
    {
        super(RoomJoinInfo.class);
    }
    public boolean add(RoomJoinInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomJoinCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomJoinInfo item)
    {
        return removeObject(item);
    }
    public RoomJoinInfo get(int index)
    {
        return(RoomJoinInfo)getObject(index);
    }
    public RoomJoinInfo get(Object key)
    {
        return(RoomJoinInfo)getObject(key);
    }
    public void set(int index, RoomJoinInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomJoinInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomJoinInfo item)
    {
        return super.indexOf(item);
    }
}