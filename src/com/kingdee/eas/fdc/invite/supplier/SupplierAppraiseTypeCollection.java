package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierAppraiseTypeCollection extends AbstractObjectCollection 
{
    public SupplierAppraiseTypeCollection()
    {
        super(SupplierAppraiseTypeInfo.class);
    }
    public boolean add(SupplierAppraiseTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierAppraiseTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierAppraiseTypeInfo item)
    {
        return removeObject(item);
    }
    public SupplierAppraiseTypeInfo get(int index)
    {
        return(SupplierAppraiseTypeInfo)getObject(index);
    }
    public SupplierAppraiseTypeInfo get(Object key)
    {
        return(SupplierAppraiseTypeInfo)getObject(key);
    }
    public void set(int index, SupplierAppraiseTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierAppraiseTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierAppraiseTypeInfo item)
    {
        return super.indexOf(item);
    }
}