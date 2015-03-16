package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomMortagageCollection extends AbstractObjectCollection 
{
    public RoomMortagageCollection()
    {
        super(RoomMortagageInfo.class);
    }
    public boolean add(RoomMortagageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomMortagageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomMortagageInfo item)
    {
        return removeObject(item);
    }
    public RoomMortagageInfo get(int index)
    {
        return(RoomMortagageInfo)getObject(index);
    }
    public RoomMortagageInfo get(Object key)
    {
        return(RoomMortagageInfo)getObject(key);
    }
    public void set(int index, RoomMortagageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomMortagageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomMortagageInfo item)
    {
        return super.indexOf(item);
    }
}