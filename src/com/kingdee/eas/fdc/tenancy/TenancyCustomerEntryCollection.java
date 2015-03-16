package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyCustomerEntryCollection extends AbstractObjectCollection 
{
    public TenancyCustomerEntryCollection()
    {
        super(TenancyCustomerEntryInfo.class);
    }
    public boolean add(TenancyCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyCustomerEntryInfo get(int index)
    {
        return(TenancyCustomerEntryInfo)getObject(index);
    }
    public TenancyCustomerEntryInfo get(Object key)
    {
        return(TenancyCustomerEntryInfo)getObject(key);
    }
    public void set(int index, TenancyCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}