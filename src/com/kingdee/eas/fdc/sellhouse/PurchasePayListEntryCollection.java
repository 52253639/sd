package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchasePayListEntryCollection extends AbstractObjectCollection 
{
    public PurchasePayListEntryCollection()
    {
        super(PurchasePayListEntryInfo.class);
    }
    public boolean add(PurchasePayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchasePayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchasePayListEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchasePayListEntryInfo get(int index)
    {
        return(PurchasePayListEntryInfo)getObject(index);
    }
    public PurchasePayListEntryInfo get(Object key)
    {
        return(PurchasePayListEntryInfo)getObject(key);
    }
    public void set(int index, PurchasePayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchasePayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchasePayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}