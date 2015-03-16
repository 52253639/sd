package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomRecoverHistoryCollection extends AbstractObjectCollection 
{
    public RoomRecoverHistoryCollection()
    {
        super(RoomRecoverHistoryInfo.class);
    }
    public boolean add(RoomRecoverHistoryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomRecoverHistoryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomRecoverHistoryInfo item)
    {
        return removeObject(item);
    }
    public RoomRecoverHistoryInfo get(int index)
    {
        return(RoomRecoverHistoryInfo)getObject(index);
    }
    public RoomRecoverHistoryInfo get(Object key)
    {
        return(RoomRecoverHistoryInfo)getObject(key);
    }
    public void set(int index, RoomRecoverHistoryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomRecoverHistoryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomRecoverHistoryInfo item)
    {
        return super.indexOf(item);
    }
}