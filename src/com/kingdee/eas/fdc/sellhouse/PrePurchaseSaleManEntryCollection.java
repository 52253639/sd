package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrePurchaseSaleManEntryCollection extends AbstractObjectCollection 
{
    public PrePurchaseSaleManEntryCollection()
    {
        super(PrePurchaseSaleManEntryInfo.class);
    }
    public boolean add(PrePurchaseSaleManEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrePurchaseSaleManEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrePurchaseSaleManEntryInfo item)
    {
        return removeObject(item);
    }
    public PrePurchaseSaleManEntryInfo get(int index)
    {
        return(PrePurchaseSaleManEntryInfo)getObject(index);
    }
    public PrePurchaseSaleManEntryInfo get(Object key)
    {
        return(PrePurchaseSaleManEntryInfo)getObject(key);
    }
    public void set(int index, PrePurchaseSaleManEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrePurchaseSaleManEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrePurchaseSaleManEntryInfo item)
    {
        return super.indexOf(item);
    }
}