package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyOrderRoomEntryCollection extends AbstractObjectCollection 
{
    public TenancyOrderRoomEntryCollection()
    {
        super(TenancyOrderRoomEntryInfo.class);
    }
    public boolean add(TenancyOrderRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyOrderRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyOrderRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyOrderRoomEntryInfo get(int index)
    {
        return(TenancyOrderRoomEntryInfo)getObject(index);
    }
    public TenancyOrderRoomEntryInfo get(Object key)
    {
        return(TenancyOrderRoomEntryInfo)getObject(key);
    }
    public void set(int index, TenancyOrderRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyOrderRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyOrderRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}