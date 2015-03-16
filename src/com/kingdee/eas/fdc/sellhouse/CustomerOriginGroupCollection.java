package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerOriginGroupCollection extends AbstractObjectCollection 
{
    public CustomerOriginGroupCollection()
    {
        super(CustomerOriginGroupInfo.class);
    }
    public boolean add(CustomerOriginGroupInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerOriginGroupCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerOriginGroupInfo item)
    {
        return removeObject(item);
    }
    public CustomerOriginGroupInfo get(int index)
    {
        return(CustomerOriginGroupInfo)getObject(index);
    }
    public CustomerOriginGroupInfo get(Object key)
    {
        return(CustomerOriginGroupInfo)getObject(key);
    }
    public void set(int index, CustomerOriginGroupInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerOriginGroupInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerOriginGroupInfo item)
    {
        return super.indexOf(item);
    }
}