package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPropertyBatchCollection extends AbstractObjectCollection 
{
    public RoomPropertyBatchCollection()
    {
        super(RoomPropertyBatchInfo.class);
    }
    public boolean add(RoomPropertyBatchInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPropertyBatchCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPropertyBatchInfo item)
    {
        return removeObject(item);
    }
    public RoomPropertyBatchInfo get(int index)
    {
        return(RoomPropertyBatchInfo)getObject(index);
    }
    public RoomPropertyBatchInfo get(Object key)
    {
        return(RoomPropertyBatchInfo)getObject(key);
    }
    public void set(int index, RoomPropertyBatchInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPropertyBatchInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPropertyBatchInfo item)
    {
        return super.indexOf(item);
    }
}