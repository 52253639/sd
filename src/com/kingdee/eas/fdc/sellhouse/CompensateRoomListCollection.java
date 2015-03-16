package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompensateRoomListCollection extends AbstractObjectCollection 
{
    public CompensateRoomListCollection()
    {
        super(CompensateRoomListInfo.class);
    }
    public boolean add(CompensateRoomListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompensateRoomListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompensateRoomListInfo item)
    {
        return removeObject(item);
    }
    public CompensateRoomListInfo get(int index)
    {
        return(CompensateRoomListInfo)getObject(index);
    }
    public CompensateRoomListInfo get(Object key)
    {
        return(CompensateRoomListInfo)getObject(key);
    }
    public void set(int index, CompensateRoomListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompensateRoomListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompensateRoomListInfo item)
    {
        return super.indexOf(item);
    }
}