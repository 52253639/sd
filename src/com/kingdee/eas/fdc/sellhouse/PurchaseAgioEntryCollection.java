package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseAgioEntryCollection extends AbstractObjectCollection 
{
    public PurchaseAgioEntryCollection()
    {
        super(PurchaseAgioEntryInfo.class);
    }
    public boolean add(PurchaseAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseAgioEntryInfo get(int index)
    {
        return(PurchaseAgioEntryInfo)getObject(index);
    }
    public PurchaseAgioEntryInfo get(Object key)
    {
        return(PurchaseAgioEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}