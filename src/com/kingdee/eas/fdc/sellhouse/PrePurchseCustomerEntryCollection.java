package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PrePurchseCustomerEntryCollection extends AbstractObjectCollection 
{
    public PrePurchseCustomerEntryCollection()
    {
        super(PrePurchseCustomerEntryInfo.class);
    }
    public boolean add(PrePurchseCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PrePurchseCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PrePurchseCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public PrePurchseCustomerEntryInfo get(int index)
    {
        return(PrePurchseCustomerEntryInfo)getObject(index);
    }
    public PrePurchseCustomerEntryInfo get(Object key)
    {
        return(PrePurchseCustomerEntryInfo)getObject(key);
    }
    public void set(int index, PrePurchseCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PrePurchseCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PrePurchseCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}