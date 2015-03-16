package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomCollection extends AbstractObjectCollection 
{
    public RoomCollection()
    {
        super(RoomInfo.class);
    }
    public boolean add(RoomInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomInfo item)
    {
        return removeObject(item);
    }
    public RoomInfo get(int index)
    {
        return(RoomInfo)getObject(index);
    }
    public RoomInfo get(Object key)
    {
        return(RoomInfo)getObject(key);
    }
    public void set(int index, RoomInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomInfo item)
    {
        return super.indexOf(item);
    }
}