package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseChangePayListOldEntryCollection extends AbstractObjectCollection 
{
    public PurchaseChangePayListOldEntryCollection()
    {
        super(PurchaseChangePayListOldEntryInfo.class);
    }
    public boolean add(PurchaseChangePayListOldEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseChangePayListOldEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseChangePayListOldEntryInfo item)
    {
        return removeObject(item);
    }
    public PurchaseChangePayListOldEntryInfo get(int index)
    {
        return(PurchaseChangePayListOldEntryInfo)getObject(index);
    }
    public PurchaseChangePayListOldEntryInfo get(Object key)
    {
        return(PurchaseChangePayListOldEntryInfo)getObject(key);
    }
    public void set(int index, PurchaseChangePayListOldEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseChangePayListOldEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseChangePayListOldEntryInfo item)
    {
        return super.indexOf(item);
    }
}