package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewRentFreeEntryCollection extends AbstractObjectCollection 
{
    public NewRentFreeEntryCollection()
    {
        super(NewRentFreeEntryInfo.class);
    }
    public boolean add(NewRentFreeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewRentFreeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewRentFreeEntryInfo item)
    {
        return removeObject(item);
    }
    public NewRentFreeEntryInfo get(int index)
    {
        return(NewRentFreeEntryInfo)getObject(index);
    }
    public NewRentFreeEntryInfo get(Object key)
    {
        return(NewRentFreeEntryInfo)getObject(key);
    }
    public void set(int index, NewRentFreeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewRentFreeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewRentFreeEntryInfo item)
    {
        return super.indexOf(item);
    }
}