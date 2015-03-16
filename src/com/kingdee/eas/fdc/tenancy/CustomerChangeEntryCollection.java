package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerChangeEntryCollection extends AbstractObjectCollection 
{
    public CustomerChangeEntryCollection()
    {
        super(CustomerChangeEntryInfo.class);
    }
    public boolean add(CustomerChangeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerChangeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerChangeEntryInfo item)
    {
        return removeObject(item);
    }
    public CustomerChangeEntryInfo get(int index)
    {
        return(CustomerChangeEntryInfo)getObject(index);
    }
    public CustomerChangeEntryInfo get(Object key)
    {
        return(CustomerChangeEntryInfo)getObject(key);
    }
    public void set(int index, CustomerChangeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerChangeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerChangeEntryInfo item)
    {
        return super.indexOf(item);
    }
}