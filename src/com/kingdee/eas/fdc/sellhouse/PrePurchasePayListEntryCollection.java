package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrePurchasePayListEntryCollection extends AbstractObjectCollection 
{
    public PrePurchasePayListEntryCollection()
    {
        super(PrePurchasePayListEntryInfo.class);
    }
    public boolean add(PrePurchasePayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrePurchasePayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrePurchasePayListEntryInfo item)
    {
        return removeObject(item);
    }
    public PrePurchasePayListEntryInfo get(int index)
    {
        return(PrePurchasePayListEntryInfo)getObject(index);
    }
    public PrePurchasePayListEntryInfo get(Object key)
    {
        return(PrePurchasePayListEntryInfo)getObject(key);
    }
    public void set(int index, PrePurchasePayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrePurchasePayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrePurchasePayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}