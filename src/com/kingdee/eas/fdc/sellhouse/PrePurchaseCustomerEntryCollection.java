package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrePurchaseCustomerEntryCollection extends AbstractObjectCollection 
{
    public PrePurchaseCustomerEntryCollection()
    {
        super(PrePurchaseCustomerEntryInfo.class);
    }
    public boolean add(PrePurchaseCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrePurchaseCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrePurchaseCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public PrePurchaseCustomerEntryInfo get(int index)
    {
        return(PrePurchaseCustomerEntryInfo)getObject(index);
    }
    public PrePurchaseCustomerEntryInfo get(Object key)
    {
        return(PrePurchaseCustomerEntryInfo)getObject(key);
    }
    public void set(int index, PrePurchaseCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrePurchaseCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrePurchaseCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}