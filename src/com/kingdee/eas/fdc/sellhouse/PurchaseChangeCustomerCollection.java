package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeCustomerCollection extends AbstractObjectCollection 
{
    public PurchaseChangeCustomerCollection()
    {
        super(PurchaseChangeCustomerInfo.class);
    }
    public boolean add(PurchaseChangeCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeCustomerInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeCustomerInfo get(int index)
    {
        return(PurchaseChangeCustomerInfo)getObject(index);
    }
    public PurchaseChangeCustomerInfo get(Object key)
    {
        return(PurchaseChangeCustomerInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeCustomerInfo item)
    {
        return super.indexOf(item);
    }
}