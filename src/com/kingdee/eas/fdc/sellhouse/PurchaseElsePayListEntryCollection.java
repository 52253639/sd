package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseElsePayListEntryCollection extends AbstractObjectCollection 
{
    public PurchaseElsePayListEntryCollection()
    {
        super(PurchaseElsePayListEntryInfo.class);
    }
    public boolean add(PurchaseElsePayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseElsePayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseElsePayListEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseElsePayListEntryInfo get(int index)
    {
        return(PurchaseElsePayListEntryInfo)getObject(index);
    }
    public PurchaseElsePayListEntryInfo get(Object key)
    {
        return(PurchaseElsePayListEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseElsePayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseElsePayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseElsePayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}