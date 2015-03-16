package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RentFreeEntryCollection extends AbstractObjectCollection 
{
    public RentFreeEntryCollection()
    {
        super(RentFreeEntryInfo.class);
    }
    public boolean add(RentFreeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RentFreeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RentFreeEntryInfo item)
    {
        return removeObject(item);
    }
    public RentFreeEntryInfo get(int index)
    {
        return(RentFreeEntryInfo)getObject(index);
    }
    public RentFreeEntryInfo get(Object key)
    {
        return(RentFreeEntryInfo)getObject(key);
    }
    public void set(int index, RentFreeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RentFreeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RentFreeEntryInfo item)
    {
        return super.indexOf(item);
    }
}