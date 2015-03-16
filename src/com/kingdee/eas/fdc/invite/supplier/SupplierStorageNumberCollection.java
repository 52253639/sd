package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierStorageNumberCollection extends AbstractObjectCollection 
{
    public SupplierStorageNumberCollection()
    {
        super(SupplierStorageNumberInfo.class);
    }
    public boolean add(SupplierStorageNumberInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierStorageNumberCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierStorageNumberInfo item)
    {
        return removeObject(item);
    }
    public SupplierStorageNumberInfo get(int index)
    {
        return(SupplierStorageNumberInfo)getObject(index);
    }
    public SupplierStorageNumberInfo get(Object key)
    {
        return(SupplierStorageNumberInfo)getObject(key);
    }
    public void set(int index, SupplierStorageNumberInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierStorageNumberInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierStorageNumberInfo item)
    {
        return super.indexOf(item);
    }
}