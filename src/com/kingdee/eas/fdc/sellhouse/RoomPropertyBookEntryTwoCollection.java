package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyBookEntryTwoCollection extends AbstractObjectCollection 
{
    public RoomPropertyBookEntryTwoCollection()
    {
        super(RoomPropertyBookEntryTwoInfo.class);
    }
    public boolean add(RoomPropertyBookEntryTwoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyBookEntryTwoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyBookEntryTwoInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyBookEntryTwoInfo get(int index)
    {
        return(RoomPropertyBookEntryTwoInfo)getObject(index);
    }
    public RoomPropertyBookEntryTwoInfo get(Object key)
    {
        return(RoomPropertyBookEntryTwoInfo)getObject(key);
    }
    public void set(int index, RoomPropertyBookEntryTwoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyBookEntryTwoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyBookEntryTwoInfo item)
    {
        return super.indexOf(item);
    }
}