package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChequeCustomerEntryCollection extends AbstractObjectCollection 
{
    public ChequeCustomerEntryCollection()
    {
        super(ChequeCustomerEntryInfo.class);
    }
    public boolean add(ChequeCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChequeCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChequeCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public ChequeCustomerEntryInfo get(int index)
    {
        return(ChequeCustomerEntryInfo)getObject(index);
    }
    public ChequeCustomerEntryInfo get(Object key)
    {
        return(ChequeCustomerEntryInfo)getObject(key);
    }
    public void set(int index, ChequeCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChequeCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChequeCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}