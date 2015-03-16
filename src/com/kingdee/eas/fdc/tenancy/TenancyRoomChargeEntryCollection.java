package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyRoomChargeEntryCollection extends AbstractObjectCollection 
{
    public TenancyRoomChargeEntryCollection()
    {
        super(TenancyRoomChargeEntryInfo.class);
    }
    public boolean add(TenancyRoomChargeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyRoomChargeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyRoomChargeEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyRoomChargeEntryInfo get(int index)
    {
        return(TenancyRoomChargeEntryInfo)getObject(index);
    }
    public TenancyRoomChargeEntryInfo get(Object key)
    {
        return(TenancyRoomChargeEntryInfo)getObject(key);
    }
    public void set(int index, TenancyRoomChargeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyRoomChargeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyRoomChargeEntryInfo item)
    {
        return super.indexOf(item);
    }
}