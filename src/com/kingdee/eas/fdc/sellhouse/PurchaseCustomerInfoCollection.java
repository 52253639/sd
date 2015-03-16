package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseCustomerInfoCollection extends AbstractObjectCollection 
{
    public PurchaseCustomerInfoCollection()
    {
        super(PurchaseCustomerInfoInfo.class);
    }
    public boolean add(PurchaseCustomerInfoInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseCustomerInfoCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseCustomerInfoInfo item)
    {
        return removeObject(item);
    }
    public PurchaseCustomerInfoInfo get(int index)
    {
        return(PurchaseCustomerInfoInfo)getObject(index);
    }
    public PurchaseCustomerInfoInfo get(Object key)
    {
        return(PurchaseCustomerInfoInfo)getObject(key);
    }
    public void set(int index, PurchaseCustomerInfoInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseCustomerInfoInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseCustomerInfoInfo item)
    {
        return super.indexOf(item);
    }
}