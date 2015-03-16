package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurCustomerCollection extends AbstractObjectCollection 
{
    public PurCustomerCollection()
    {
        super(PurCustomerInfo.class);
    }
    public boolean add(PurCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurCustomerInfo item)
    {
        return removeObject(item);
    }
    public PurCustomerInfo get(int index)
    {
        return(PurCustomerInfo)getObject(index);
    }
    public PurCustomerInfo get(Object key)
    {
        return(PurCustomerInfo)getObject(key);
    }
    public void set(int index, PurCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurCustomerInfo item)
    {
        return super.indexOf(item);
    }
}