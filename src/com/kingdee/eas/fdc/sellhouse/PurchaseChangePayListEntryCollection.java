package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangePayListEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangePayListEntryCollection()
    {
        super(PurchaseChangePayListEntryInfo.class);
    }
    public boolean add(PurchaseChangePayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangePayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangePayListEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangePayListEntryInfo get(int index)
    {
        return(PurchaseChangePayListEntryInfo)getObject(index);
    }
    public PurchaseChangePayListEntryInfo get(Object key)
    {
        return(PurchaseChangePayListEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangePayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangePayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangePayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}