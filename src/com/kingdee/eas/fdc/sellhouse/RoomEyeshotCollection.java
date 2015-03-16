package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomEyeshotCollection extends AbstractObjectCollection 
{
    public RoomEyeshotCollection()
    {
        super(RoomEyeshotInfo.class);
    }
    public boolean add(RoomEyeshotInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomEyeshotCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomEyeshotInfo item)
    {
        return removeObject(item);
    }
    public RoomEyeshotInfo get(int index)
    {
        return(RoomEyeshotInfo)getObject(index);
    }
    public RoomEyeshotInfo get(Object key)
    {
        return(RoomEyeshotInfo)getObject(key);
    }
    public void set(int index, RoomEyeshotInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomEyeshotInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomEyeshotInfo item)
    {
        return super.indexOf(item);
    }
}