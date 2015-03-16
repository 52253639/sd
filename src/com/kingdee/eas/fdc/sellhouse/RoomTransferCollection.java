package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomTransferCollection extends AbstractObjectCollection 
{
    public RoomTransferCollection()
    {
        super(RoomTransferInfo.class);
    }
    public boolean add(RoomTransferInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomTransferCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomTransferInfo item)
    {
        return removeObject(item);
    }
    public RoomTransferInfo get(int index)
    {
        return(RoomTransferInfo)getObject(index);
    }
    public RoomTransferInfo get(Object key)
    {
        return(RoomTransferInfo)getObject(key);
    }
    public void set(int index, RoomTransferInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomTransferInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomTransferInfo item)
    {
        return super.indexOf(item);
    }
}