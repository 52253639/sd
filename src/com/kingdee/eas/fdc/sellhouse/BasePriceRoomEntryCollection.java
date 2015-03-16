package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BasePriceRoomEntryCollection extends AbstractObjectCollection 
{
    public BasePriceRoomEntryCollection()
    {
        super(BasePriceRoomEntryInfo.class);
    }
    public boolean add(BasePriceRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BasePriceRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BasePriceRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public BasePriceRoomEntryInfo get(int index)
    {
        return(BasePriceRoomEntryInfo)getObject(index);
    }
    public BasePriceRoomEntryInfo get(Object key)
    {
        return(BasePriceRoomEntryInfo)getObject(key);
    }
    public void set(int index, BasePriceRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BasePriceRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BasePriceRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}