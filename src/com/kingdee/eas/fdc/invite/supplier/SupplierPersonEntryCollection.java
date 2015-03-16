package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierPersonEntryCollection extends AbstractObjectCollection 
{
    public SupplierPersonEntryCollection()
    {
        super(SupplierPersonEntryInfo.class);
    }
    public boolean add(SupplierPersonEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierPersonEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierPersonEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierPersonEntryInfo get(int index)
    {
        return(SupplierPersonEntryInfo)getObject(index);
    }
    public SupplierPersonEntryInfo get(Object key)
    {
        return(SupplierPersonEntryInfo)getObject(key);
    }
    public void set(int index, SupplierPersonEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierPersonEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierPersonEntryInfo item)
    {
        return super.indexOf(item);
    }
}