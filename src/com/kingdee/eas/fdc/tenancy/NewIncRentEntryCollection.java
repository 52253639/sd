package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewIncRentEntryCollection extends AbstractObjectCollection 
{
    public NewIncRentEntryCollection()
    {
        super(NewIncRentEntryInfo.class);
    }
    public boolean add(NewIncRentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewIncRentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewIncRentEntryInfo item)
    {
        return removeObject(item);
    }
    public NewIncRentEntryInfo get(int index)
    {
        return(NewIncRentEntryInfo)getObject(index);
    }
    public NewIncRentEntryInfo get(Object key)
    {
        return(NewIncRentEntryInfo)getObject(key);
    }
    public void set(int index, NewIncRentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewIncRentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewIncRentEntryInfo item)
    {
        return super.indexOf(item);
    }
}