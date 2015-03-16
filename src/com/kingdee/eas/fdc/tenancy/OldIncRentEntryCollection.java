package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OldIncRentEntryCollection extends AbstractObjectCollection 
{
    public OldIncRentEntryCollection()
    {
        super(OldIncRentEntryInfo.class);
    }
    public boolean add(OldIncRentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OldIncRentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OldIncRentEntryInfo item)
    {
        return removeObject(item);
    }
    public OldIncRentEntryInfo get(int index)
    {
        return(OldIncRentEntryInfo)getObject(index);
    }
    public OldIncRentEntryInfo get(Object key)
    {
        return(OldIncRentEntryInfo)getObject(key);
    }
    public void set(int index, OldIncRentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OldIncRentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OldIncRentEntryInfo item)
    {
        return super.indexOf(item);
    }
}