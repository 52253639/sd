package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyBookCollection extends AbstractObjectCollection 
{
    public RoomPropertyBookCollection()
    {
        super(RoomPropertyBookInfo.class);
    }
    public boolean add(RoomPropertyBookInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyBookCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyBookInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyBookInfo get(int index)
    {
        return(RoomPropertyBookInfo)getObject(index);
    }
    public RoomPropertyBookInfo get(Object key)
    {
        return(RoomPropertyBookInfo)getObject(key);
    }
    public void set(int index, RoomPropertyBookInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyBookInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyBookInfo item)
    {
        return super.indexOf(item);
    }
}