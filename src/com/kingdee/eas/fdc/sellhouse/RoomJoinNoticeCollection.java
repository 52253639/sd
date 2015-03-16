package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomJoinNoticeCollection extends AbstractObjectCollection 
{
    public RoomJoinNoticeCollection()
    {
        super(RoomJoinNoticeInfo.class);
    }
    public boolean add(RoomJoinNoticeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomJoinNoticeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomJoinNoticeInfo item)
    {
        return removeObject(item);
    }
    public RoomJoinNoticeInfo get(int index)
    {
        return(RoomJoinNoticeInfo)getObject(index);
    }
    public RoomJoinNoticeInfo get(Object key)
    {
        return(RoomJoinNoticeInfo)getObject(key);
    }
    public void set(int index, RoomJoinNoticeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomJoinNoticeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomJoinNoticeInfo item)
    {
        return super.indexOf(item);
    }
}