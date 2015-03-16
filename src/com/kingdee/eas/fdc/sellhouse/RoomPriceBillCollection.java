package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPriceBillCollection extends AbstractObjectCollection 
{
    public RoomPriceBillCollection()
    {
        super(RoomPriceBillInfo.class);
    }
    public boolean add(RoomPriceBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPriceBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPriceBillInfo item)
    {
        return removeObject(item);
    }
    public RoomPriceBillInfo get(int index)
    {
        return(RoomPriceBillInfo)getObject(index);
    }
    public RoomPriceBillInfo get(Object key)
    {
        return(RoomPriceBillInfo)getObject(key);
    }
    public void set(int index, RoomPriceBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPriceBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPriceBillInfo item)
    {
        return super.indexOf(item);
    }
}