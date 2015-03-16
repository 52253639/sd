package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvoiceCollection extends AbstractObjectCollection 
{
    public InvoiceCollection()
    {
        super(InvoiceInfo.class);
    }
    public boolean add(InvoiceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvoiceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvoiceInfo item)
    {
        return removeObject(item);
    }
    public InvoiceInfo get(int index)
    {
        return(InvoiceInfo)getObject(index);
    }
    public InvoiceInfo get(Object key)
    {
        return(InvoiceInfo)getObject(key);
    }
    public void set(int index, InvoiceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvoiceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvoiceInfo item)
    {
        return super.indexOf(item);
    }
}