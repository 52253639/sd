package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SincerityPurchaseCustomerEntryCollection extends AbstractObjectCollection 
{
    public SincerityPurchaseCustomerEntryCollection()
    {
        super(SincerityPurchaseCustomerEntryInfo.class);
    }
    public boolean add(SincerityPurchaseCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SincerityPurchaseCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SincerityPurchaseCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public SincerityPurchaseCustomerEntryInfo get(int index)
    {
        return(SincerityPurchaseCustomerEntryInfo)getObject(index);
    }
    public SincerityPurchaseCustomerEntryInfo get(Object key)
    {
        return(SincerityPurchaseCustomerEntryInfo)getObject(key);
    }
    public void set(int index, SincerityPurchaseCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SincerityPurchaseCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SincerityPurchaseCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}