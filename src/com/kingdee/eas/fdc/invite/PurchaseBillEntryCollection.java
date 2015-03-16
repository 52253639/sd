package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseBillEntryCollection extends AbstractObjectCollection 
{
    public PurchaseBillEntryCollection()
    {
        super(PurchaseBillEntryInfo.class);
    }
    public boolean add(PurchaseBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseBillEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseBillEntryInfo get(int index)
    {
        return(PurchaseBillEntryInfo)getObject(index);
    }
    public PurchaseBillEntryInfo get(Object key)
    {
        return(PurchaseBillEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}