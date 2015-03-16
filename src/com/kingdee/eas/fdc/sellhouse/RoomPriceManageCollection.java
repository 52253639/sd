package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPriceManageCollection extends AbstractObjectCollection 
{
    public RoomPriceManageCollection()
    {
        super(RoomPriceManageInfo.class);
    }
    public boolean add(RoomPriceManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPriceManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPriceManageInfo item)
    {
        return removeObject(item);
    }
    public RoomPriceManageInfo get(int index)
    {
        return(RoomPriceManageInfo)getObject(index);
    }
    public RoomPriceManageInfo get(Object key)
    {
        return(RoomPriceManageInfo)getObject(key);
    }
    public void set(int index, RoomPriceManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPriceManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPriceManageInfo item)
    {
        return super.indexOf(item);
    }
}