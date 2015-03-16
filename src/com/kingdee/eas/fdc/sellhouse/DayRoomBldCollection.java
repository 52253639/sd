package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayRoomBldCollection extends AbstractObjectCollection 
{
    public DayRoomBldCollection()
    {
        super(DayRoomBldInfo.class);
    }
    public boolean add(DayRoomBldInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayRoomBldCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayRoomBldInfo item)
    {
        return removeObject(item);
    }
    public DayRoomBldInfo get(int index)
    {
        return(DayRoomBldInfo)getObject(index);
    }
    public DayRoomBldInfo get(Object key)
    {
        return(DayRoomBldInfo)getObject(key);
    }
    public void set(int index, DayRoomBldInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayRoomBldInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayRoomBldInfo item)
    {
        return super.indexOf(item);
    }
}