package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomDisplaySetCollection extends AbstractObjectCollection 
{
    public RoomDisplaySetCollection()
    {
        super(RoomDisplaySetInfo.class);
    }
    public boolean add(RoomDisplaySetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomDisplaySetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomDisplaySetInfo item)
    {
        return removeObject(item);
    }
    public RoomDisplaySetInfo get(int index)
    {
        return(RoomDisplaySetInfo)getObject(index);
    }
    public RoomDisplaySetInfo get(Object key)
    {
        return(RoomDisplaySetInfo)getObject(key);
    }
    public void set(int index, RoomDisplaySetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomDisplaySetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomDisplaySetInfo item)
    {
        return super.indexOf(item);
    }
}