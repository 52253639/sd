package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeNameNewCustomerEntryCollection extends AbstractObjectCollection 
{
    public ChangeNameNewCustomerEntryCollection()
    {
        super(ChangeNameNewCustomerEntryInfo.class);
    }
    public boolean add(ChangeNameNewCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeNameNewCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeNameNewCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeNameNewCustomerEntryInfo get(int index)
    {
        return(ChangeNameNewCustomerEntryInfo)getObject(index);
    }
    public ChangeNameNewCustomerEntryInfo get(Object key)
    {
        return(ChangeNameNewCustomerEntryInfo)getObject(key);
    }
    public void set(int index, ChangeNameNewCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeNameNewCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeNameNewCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}