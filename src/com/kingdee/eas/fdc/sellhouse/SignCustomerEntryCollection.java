package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SignCustomerEntryCollection extends AbstractObjectCollection 
{
    public SignCustomerEntryCollection()
    {
        super(SignCustomerEntryInfo.class);
    }
    public boolean add(SignCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SignCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SignCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public SignCustomerEntryInfo get(int index)
    {
        return(SignCustomerEntryInfo)getObject(index);
    }
    public SignCustomerEntryInfo get(Object key)
    {
        return(SignCustomerEntryInfo)getObject(key);
    }
    public void set(int index, SignCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SignCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SignCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}