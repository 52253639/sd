package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHECustomerCollection extends AbstractObjectCollection 
{
    public SHECustomerCollection()
    {
        super(SHECustomerInfo.class);
    }
    public boolean add(SHECustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHECustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHECustomerInfo item)
    {
        return removeObject(item);
    }
    public SHECustomerInfo get(int index)
    {
        return(SHECustomerInfo)getObject(index);
    }
    public SHECustomerInfo get(Object key)
    {
        return(SHECustomerInfo)getObject(key);
    }
    public void set(int index, SHECustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHECustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHECustomerInfo item)
    {
        return super.indexOf(item);
    }
}