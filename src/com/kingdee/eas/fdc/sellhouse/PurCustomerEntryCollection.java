package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurCustomerEntryCollection extends AbstractObjectCollection 
{
    public PurCustomerEntryCollection()
    {
        super(PurCustomerEntryInfo.class);
    }
    public boolean add(PurCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public PurCustomerEntryInfo get(int index)
    {
        return(PurCustomerEntryInfo)getObject(index);
    }
    public PurCustomerEntryInfo get(Object key)
    {
        return(PurCustomerEntryInfo)getObject(key);
    }
    public void set(int index, PurCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}