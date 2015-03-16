package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TranCustomerEntryCollection extends AbstractObjectCollection 
{
    public TranCustomerEntryCollection()
    {
        super(TranCustomerEntryInfo.class);
    }
    public boolean add(TranCustomerEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TranCustomerEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TranCustomerEntryInfo item)
    {
        return removeObject(item);
    }
    public TranCustomerEntryInfo get(int index)
    {
        return(TranCustomerEntryInfo)getObject(index);
    }
    public TranCustomerEntryInfo get(Object key)
    {
        return(TranCustomerEntryInfo)getObject(key);
    }
    public void set(int index, TranCustomerEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TranCustomerEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TranCustomerEntryInfo item)
    {
        return super.indexOf(item);
    }
}