package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerChangeOldEntryCollection extends AbstractObjectCollection 
{
    public CustomerChangeOldEntryCollection()
    {
        super(CustomerChangeOldEntryInfo.class);
    }
    public boolean add(CustomerChangeOldEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerChangeOldEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerChangeOldEntryInfo item)
    {
        return removeObject(item);
    }
    public CustomerChangeOldEntryInfo get(int index)
    {
        return(CustomerChangeOldEntryInfo)getObject(index);
    }
    public CustomerChangeOldEntryInfo get(Object key)
    {
        return(CustomerChangeOldEntryInfo)getObject(key);
    }
    public void set(int index, CustomerChangeOldEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerChangeOldEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerChangeOldEntryInfo item)
    {
        return super.indexOf(item);
    }
}