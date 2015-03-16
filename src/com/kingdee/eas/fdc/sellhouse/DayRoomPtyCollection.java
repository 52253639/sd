package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DayRoomPtyCollection extends AbstractObjectCollection 
{
    public DayRoomPtyCollection()
    {
        super(DayRoomPtyInfo.class);
    }
    public boolean add(DayRoomPtyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DayRoomPtyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DayRoomPtyInfo item)
    {
        return removeObject(item);
    }
    public DayRoomPtyInfo get(int index)
    {
        return(DayRoomPtyInfo)getObject(index);
    }
    public DayRoomPtyInfo get(Object key)
    {
        return(DayRoomPtyInfo)getObject(key);
    }
    public void set(int index, DayRoomPtyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DayRoomPtyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DayRoomPtyInfo item)
    {
        return super.indexOf(item);
    }
}