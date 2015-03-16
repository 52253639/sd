package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangeAgioOldEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangeAgioOldEntryCollection()
    {
        super(PurchaseChangeAgioOldEntryInfo.class);
    }
    public boolean add(PurchaseChangeAgioOldEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangeAgioOldEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangeAgioOldEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangeAgioOldEntryInfo get(int index)
    {
        return(PurchaseChangeAgioOldEntryInfo)getObject(index);
    }
    public PurchaseChangeAgioOldEntryInfo get(Object key)
    {
        return(PurchaseChangeAgioOldEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangeAgioOldEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangeAgioOldEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangeAgioOldEntryInfo item)
    {
        return super.indexOf(item);
    }
}