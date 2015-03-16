package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerEntryCollection extends AbstractObjectCollection 
{
    public CustomerEntryCollection()
    {
        super(CustomerEntryInfo.class);
    }
    public boolean add(CustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public CustomerEntryInfo get(int index)
    {
        return(CustomerEntryInfo)getObject(index);
    }
    public CustomerEntryInfo get(Object key)
    {
        return(CustomerEntryInfo)getObject(key);
    }
    public void set(int index, CustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}