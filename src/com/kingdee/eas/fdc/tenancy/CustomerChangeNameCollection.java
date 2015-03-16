package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerChangeNameCollection extends AbstractObjectCollection 
{
    public CustomerChangeNameCollection()
    {
        super(CustomerChangeNameInfo.class);
    }
    public boolean add(CustomerChangeNameInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerChangeNameCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerChangeNameInfo item)
    {
        return removeObject(item);
    }
    public CustomerChangeNameInfo get(int index)
    {
        return(CustomerChangeNameInfo)getObject(index);
    }
    public CustomerChangeNameInfo get(Object key)
    {
        return(CustomerChangeNameInfo)getObject(key);
    }
    public void set(int index, CustomerChangeNameInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerChangeNameInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerChangeNameInfo item)
    {
        return super.indexOf(item);
    }
}