package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierFileTypeCollection extends AbstractObjectCollection 
{
    public SupplierFileTypeCollection()
    {
        super(SupplierFileTypeInfo.class);
    }
    public boolean add(SupplierFileTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierFileTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierFileTypeInfo item)
    {
        return removeObject(item);
    }
    public SupplierFileTypeInfo get(int index)
    {
        return(SupplierFileTypeInfo)getObject(index);
    }
    public SupplierFileTypeInfo get(Object key)
    {
        return(SupplierFileTypeInfo)getObject(key);
    }
    public void set(int index, SupplierFileTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierFileTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierFileTypeInfo item)
    {
        return super.indexOf(item);
    }
}