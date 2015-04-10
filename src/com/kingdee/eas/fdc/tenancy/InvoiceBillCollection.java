package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvoiceBillCollection extends AbstractObjectCollection 
{
    public InvoiceBillCollection()
    {
        super(InvoiceBillInfo.class);
    }
    public boolean add(InvoiceBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvoiceBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvoiceBillInfo item)
    {
        return removeObject(item);
    }
    public InvoiceBillInfo get(int index)
    {
        return(InvoiceBillInfo)getObject(index);
    }
    public InvoiceBillInfo get(Object key)
    {
        return(InvoiceBillInfo)getObject(key);
    }
    public void set(int index, InvoiceBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvoiceBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvoiceBillInfo item)
    {
        return super.indexOf(item);
    }
}