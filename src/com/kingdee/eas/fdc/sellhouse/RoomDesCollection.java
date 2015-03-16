package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomDesCollection extends AbstractObjectCollection 
{
    public RoomDesCollection()
    {
        super(RoomDesInfo.class);
    }
    public boolean add(RoomDesInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomDesCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomDesInfo item)
    {
        return removeObject(item);
    }
    public RoomDesInfo get(int index)
    {
        return(RoomDesInfo)getObject(index);
    }
    public RoomDesInfo get(Object key)
    {
        return(RoomDesInfo)getObject(key);
    }
    public void set(int index, RoomDesInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomDesInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomDesInfo item)
    {
        return super.indexOf(item);
    }
}