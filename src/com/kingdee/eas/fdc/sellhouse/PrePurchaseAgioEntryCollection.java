package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrePurchaseAgioEntryCollection extends AbstractObjectCollection 
{
    public PrePurchaseAgioEntryCollection()
    {
        super(PrePurchaseAgioEntryInfo.class);
    }
    public boolean add(PrePurchaseAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrePurchaseAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrePurchaseAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public PrePurchaseAgioEntryInfo get(int index)
    {
        return(PrePurchaseAgioEntryInfo)getObject(index);
    }
    public PrePurchaseAgioEntryInfo get(Object key)
    {
        return(PrePurchaseAgioEntryInfo)getObject(key);
    }
    public void set(int index, PrePurchaseAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrePurchaseAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrePurchaseAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}