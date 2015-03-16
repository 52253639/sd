package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerWorkAreaEntryCollection extends AbstractObjectCollection 
{
    public CustomerWorkAreaEntryCollection()
    {
        super(CustomerWorkAreaEntryInfo.class);
    }
    public boolean add(CustomerWorkAreaEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerWorkAreaEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerWorkAreaEntryInfo item)
    {
        return removeObject(item);
    }
    public CustomerWorkAreaEntryInfo get(int index)
    {
        return(CustomerWorkAreaEntryInfo)getObject(index);
    }
    public CustomerWorkAreaEntryInfo get(Object key)
    {
        return(CustomerWorkAreaEntryInfo)getObject(key);
    }
    public void set(int index, CustomerWorkAreaEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerWorkAreaEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerWorkAreaEntryInfo item)
    {
        return super.indexOf(item);
    }
}