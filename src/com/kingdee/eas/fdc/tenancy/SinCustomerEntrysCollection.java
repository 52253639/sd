package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SinCustomerEntrysCollection extends AbstractObjectCollection 
{
    public SinCustomerEntrysCollection()
    {
        super(SinCustomerEntrysInfo.class);
    }
    public boolean add(SinCustomerEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SinCustomerEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SinCustomerEntrysInfo item)
    {
        return removeObject(item);
    }
    public SinCustomerEntrysInfo get(int index)
    {
        return(SinCustomerEntrysInfo)getObject(index);
    }
    public SinCustomerEntrysInfo get(Object key)
    {
        return(SinCustomerEntrysInfo)getObject(key);
    }
    public void set(int index, SinCustomerEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SinCustomerEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SinCustomerEntrysInfo item)
    {
        return super.indexOf(item);
    }
}