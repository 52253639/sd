package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeNameOldCustomerEntryCollection extends AbstractObjectCollection 
{
    public ChangeNameOldCustomerEntryCollection()
    {
        super(ChangeNameOldCustomerEntryInfo.class);
    }
    public boolean add(ChangeNameOldCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeNameOldCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeNameOldCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeNameOldCustomerEntryInfo get(int index)
    {
        return(ChangeNameOldCustomerEntryInfo)getObject(index);
    }
    public ChangeNameOldCustomerEntryInfo get(Object key)
    {
        return(ChangeNameOldCustomerEntryInfo)getObject(key);
    }
    public void set(int index, ChangeNameOldCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeNameOldCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeNameOldCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}