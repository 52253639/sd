package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseBillCollection extends AbstractObjectCollection 
{
    public PurchaseBillCollection()
    {
        super(PurchaseBillInfo.class);
    }
    public boolean add(PurchaseBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseBillInfo item)
    {
        return removeObject(item);
    }
    public PurchaseBillInfo get(int index)
    {
        return(PurchaseBillInfo)getObject(index);
    }
    public PurchaseBillInfo get(Object key)
    {
        return(PurchaseBillInfo)getObject(key);
    }
    public void set(int index, PurchaseBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseBillInfo item)
    {
        return super.indexOf(item);
    }
}