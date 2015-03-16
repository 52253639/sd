package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OldRentFreeEntryCollection extends AbstractObjectCollection 
{
    public OldRentFreeEntryCollection()
    {
        super(OldRentFreeEntryInfo.class);
    }
    public boolean add(OldRentFreeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OldRentFreeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OldRentFreeEntryInfo item)
    {
        return removeObject(item);
    }
    public OldRentFreeEntryInfo get(int index)
    {
        return(OldRentFreeEntryInfo)getObject(index);
    }
    public OldRentFreeEntryInfo get(Object key)
    {
        return(OldRentFreeEntryInfo)getObject(key);
    }
    public void set(int index, OldRentFreeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OldRentFreeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OldRentFreeEntryInfo item)
    {
        return super.indexOf(item);
    }
}