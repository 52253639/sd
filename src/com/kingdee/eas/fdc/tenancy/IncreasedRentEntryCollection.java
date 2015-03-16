package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class IncreasedRentEntryCollection extends AbstractObjectCollection 
{
    public IncreasedRentEntryCollection()
    {
        super(IncreasedRentEntryInfo.class);
    }
    public boolean add(IncreasedRentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(IncreasedRentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(IncreasedRentEntryInfo item)
    {
        return removeObject(item);
    }
    public IncreasedRentEntryInfo get(int index)
    {
        return(IncreasedRentEntryInfo)getObject(index);
    }
    public IncreasedRentEntryInfo get(Object key)
    {
        return(IncreasedRentEntryInfo)getObject(key);
    }
    public void set(int index, IncreasedRentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(IncreasedRentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(IncreasedRentEntryInfo item)
    {
        return super.indexOf(item);
    }
}