package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeCustomerEntryCollection extends AbstractObjectCollection 
{
    public ChangeCustomerEntryCollection()
    {
        super(ChangeCustomerEntryInfo.class);
    }
    public boolean add(ChangeCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public ChangeCustomerEntryInfo get(int index)
    {
        return(ChangeCustomerEntryInfo)getObject(index);
    }
    public ChangeCustomerEntryInfo get(Object key)
    {
        return(ChangeCustomerEntryInfo)getObject(key);
    }
    public void set(int index, ChangeCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}