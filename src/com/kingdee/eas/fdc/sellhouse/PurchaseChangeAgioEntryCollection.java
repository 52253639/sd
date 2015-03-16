package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeAgioEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangeAgioEntryCollection()
    {
        super(PurchaseChangeAgioEntryInfo.class);
    }
    public boolean add(PurchaseChangeAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeAgioEntryInfo get(int index)
    {
        return(PurchaseChangeAgioEntryInfo)getObject(index);
    }
    public PurchaseChangeAgioEntryInfo get(Object key)
    {
        return(PurchaseChangeAgioEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}