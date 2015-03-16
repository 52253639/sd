package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomPriceAdjustHisCollection extends AbstractObjectCollection 
{
    public RoomPriceAdjustHisCollection()
    {
        super(RoomPriceAdjustHisInfo.class);
    }
    public boolean add(RoomPriceAdjustHisInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomPriceAdjustHisCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomPriceAdjustHisInfo item)
    {
        return removeObject(item);
    }
    public RoomPriceAdjustHisInfo get(int index)
    {
        return(RoomPriceAdjustHisInfo)getObject(index);
    }
    public RoomPriceAdjustHisInfo get(Object key)
    {
        return(RoomPriceAdjustHisInfo)getObject(key);
    }
    public void set(int index, RoomPriceAdjustHisInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomPriceAdjustHisInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomPriceAdjustHisInfo item)
    {
        return super.indexOf(item);
    }
}