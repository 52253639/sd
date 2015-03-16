package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyRoomEntryCollection extends AbstractObjectCollection 
{
    public TenancyRoomEntryCollection()
    {
        super(TenancyRoomEntryInfo.class);
    }
    public boolean add(TenancyRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyRoomEntryInfo get(int index)
    {
        return(TenancyRoomEntryInfo)getObject(index);
    }
    public TenancyRoomEntryInfo get(Object key)
    {
        return(TenancyRoomEntryInfo)getObject(key);
    }
    public void set(int index, TenancyRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}