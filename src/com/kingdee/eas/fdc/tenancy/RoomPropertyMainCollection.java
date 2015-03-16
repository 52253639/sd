package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyMainCollection extends AbstractObjectCollection 
{
    public RoomPropertyMainCollection()
    {
        super(RoomPropertyMainInfo.class);
    }
    public boolean add(RoomPropertyMainInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyMainCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyMainInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyMainInfo get(int index)
    {
        return(RoomPropertyMainInfo)getObject(index);
    }
    public RoomPropertyMainInfo get(Object key)
    {
        return(RoomPropertyMainInfo)getObject(key);
    }
    public void set(int index, RoomPropertyMainInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyMainInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyMainInfo item)
    {
        return super.indexOf(item);
    }
}