package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomKeepDownBillCollection extends AbstractObjectCollection 
{
    public RoomKeepDownBillCollection()
    {
        super(RoomKeepDownBillInfo.class);
    }
    public boolean add(RoomKeepDownBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomKeepDownBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomKeepDownBillInfo item)
    {
        return removeObject(item);
    }
    public RoomKeepDownBillInfo get(int index)
    {
        return(RoomKeepDownBillInfo)getObject(index);
    }
    public RoomKeepDownBillInfo get(Object key)
    {
        return(RoomKeepDownBillInfo)getObject(key);
    }
    public void set(int index, RoomKeepDownBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomKeepDownBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomKeepDownBillInfo item)
    {
        return super.indexOf(item);
    }
}