package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvoiceBillEntryCollection extends AbstractObjectCollection 
{
    public InvoiceBillEntryCollection()
    {
        super(InvoiceBillEntryInfo.class);
    }
    public boolean add(InvoiceBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvoiceBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvoiceBillEntryInfo item)
    {
        return removeObject(item);
    }
    public InvoiceBillEntryInfo get(int index)
    {
        return(InvoiceBillEntryInfo)getObject(index);
    }
    public InvoiceBillEntryInfo get(Object key)
    {
        return(InvoiceBillEntryInfo)getObject(key);
    }
    public void set(int index, InvoiceBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvoiceBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvoiceBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}