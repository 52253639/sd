package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyRoomPayListEntryCollection extends AbstractObjectCollection 
{
    public TenancyRoomPayListEntryCollection()
    {
        super(TenancyRoomPayListEntryInfo.class);
    }
    public boolean add(TenancyRoomPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyRoomPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyRoomPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyRoomPayListEntryInfo get(int index)
    {
        return(TenancyRoomPayListEntryInfo)getObject(index);
    }
    public TenancyRoomPayListEntryInfo get(Object key)
    {
        return(TenancyRoomPayListEntryInfo)getObject(key);
    }
    public void set(int index, TenancyRoomPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyRoomPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyRoomPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}