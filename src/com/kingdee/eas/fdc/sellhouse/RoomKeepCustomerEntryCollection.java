package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RoomKeepCustomerEntryCollection extends AbstractObjectCollection 
{
    public RoomKeepCustomerEntryCollection()
    {
        super(RoomKeepCustomerEntryInfo.class);
    }
    public boolean add(RoomKeepCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RoomKeepCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RoomKeepCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public RoomKeepCustomerEntryInfo get(int index)
    {
        return(RoomKeepCustomerEntryInfo)getObject(index);
    }
    public RoomKeepCustomerEntryInfo get(Object key)
    {
        return(RoomKeepCustomerEntryInfo)getObject(key);
    }
    public void set(int index, RoomKeepCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RoomKeepCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RoomKeepCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}