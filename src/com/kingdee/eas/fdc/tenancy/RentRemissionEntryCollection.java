package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RentRemissionEntryCollection extends AbstractObjectCollection 
{
    public RentRemissionEntryCollection()
    {
        super(RentRemissionEntryInfo.class);
    }
    public boolean add(RentRemissionEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RentRemissionEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RentRemissionEntryInfo item)
    {
        return removeObject(item);
    }
    public RentRemissionEntryInfo get(int index)
    {
        return(RentRemissionEntryInfo)getObject(index);
    }
    public RentRemissionEntryInfo get(Object key)
    {
        return(RentRemissionEntryInfo)getObject(key);
    }
    public void set(int index, RentRemissionEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RentRemissionEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RentRemissionEntryInfo item)
    {
        return super.indexOf(item);
    }
}