package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomUsageCollection extends AbstractObjectCollection 
{
    public RoomUsageCollection()
    {
        super(RoomUsageInfo.class);
    }
    public boolean add(RoomUsageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomUsageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomUsageInfo item)
    {
        return removeObject(item);
    }
    public RoomUsageInfo get(int index)
    {
        return(RoomUsageInfo)getObject(index);
    }
    public RoomUsageInfo get(Object key)
    {
        return(RoomUsageInfo)getObject(key);
    }
    public void set(int index, RoomUsageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomUsageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomUsageInfo item)
    {
        return super.indexOf(item);
    }
}