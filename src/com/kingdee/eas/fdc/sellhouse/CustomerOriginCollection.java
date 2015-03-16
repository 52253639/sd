package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerOriginCollection extends AbstractObjectCollection 
{
    public CustomerOriginCollection()
    {
        super(CustomerOriginInfo.class);
    }
    public boolean add(CustomerOriginInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerOriginCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerOriginInfo item)
    {
        return removeObject(item);
    }
    public CustomerOriginInfo get(int index)
    {
        return(CustomerOriginInfo)getObject(index);
    }
    public CustomerOriginInfo get(Object key)
    {
        return(CustomerOriginInfo)getObject(key);
    }
    public void set(int index, CustomerOriginInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerOriginInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerOriginInfo item)
    {
        return super.indexOf(item);
    }
}