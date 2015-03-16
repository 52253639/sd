package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompensateRoomListHisCollection extends AbstractObjectCollection 
{
    public CompensateRoomListHisCollection()
    {
        super(CompensateRoomListHisInfo.class);
    }
    public boolean add(CompensateRoomListHisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompensateRoomListHisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompensateRoomListHisInfo item)
    {
        return removeObject(item);
    }
    public CompensateRoomListHisInfo get(int index)
    {
        return(CompensateRoomListHisInfo)getObject(index);
    }
    public CompensateRoomListHisInfo get(Object key)
    {
        return(CompensateRoomListHisInfo)getObject(key);
    }
    public void set(int index, CompensateRoomListHisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompensateRoomListHisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompensateRoomListHisInfo item)
    {
        return super.indexOf(item);
    }
}