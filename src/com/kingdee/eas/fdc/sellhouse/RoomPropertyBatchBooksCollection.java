package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyBatchBooksCollection extends AbstractObjectCollection 
{
    public RoomPropertyBatchBooksCollection()
    {
        super(RoomPropertyBatchBooksInfo.class);
    }
    public boolean add(RoomPropertyBatchBooksInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyBatchBooksCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyBatchBooksInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyBatchBooksInfo get(int index)
    {
        return(RoomPropertyBatchBooksInfo)getObject(index);
    }
    public RoomPropertyBatchBooksInfo get(Object key)
    {
        return(RoomPropertyBatchBooksInfo)getObject(key);
    }
    public void set(int index, RoomPropertyBatchBooksInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyBatchBooksInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyBatchBooksInfo item)
    {
        return super.indexOf(item);
    }
}