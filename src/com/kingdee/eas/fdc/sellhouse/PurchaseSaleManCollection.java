package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseSaleManCollection extends AbstractObjectCollection 
{
    public PurchaseSaleManCollection()
    {
        super(PurchaseSaleManInfo.class);
    }
    public boolean add(PurchaseSaleManInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseSaleManCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseSaleManInfo item)
    {
        return removeObject(item);
    }
    public PurchaseSaleManInfo get(int index)
    {
        return(PurchaseSaleManInfo)getObject(index);
    }
    public PurchaseSaleManInfo get(Object key)
    {
        return(PurchaseSaleManInfo)getObject(key);
    }
    public void set(int index, PurchaseSaleManInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseSaleManInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseSaleManInfo item)
    {
        return super.indexOf(item);
    }
}