package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseCollection extends AbstractObjectCollection 
{
    public PurchaseCollection()
    {
        super(PurchaseInfo.class);
    }
    public boolean add(PurchaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseInfo item)
    {
        return removeObject(item);
    }
    public PurchaseInfo get(int index)
    {
        return(PurchaseInfo)getObject(index);
    }
    public PurchaseInfo get(Object key)
    {
        return(PurchaseInfo)getObject(key);
    }
    public void set(int index, PurchaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseInfo item)
    {
        return super.indexOf(item);
    }
}