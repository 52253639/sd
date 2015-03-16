package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCSupplierServiceTypeCollection extends AbstractObjectCollection 
{
    public FDCSupplierServiceTypeCollection()
    {
        super(FDCSupplierServiceTypeInfo.class);
    }
    public boolean add(FDCSupplierServiceTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCSupplierServiceTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCSupplierServiceTypeInfo item)
    {
        return removeObject(item);
    }
    public FDCSupplierServiceTypeInfo get(int index)
    {
        return(FDCSupplierServiceTypeInfo)getObject(index);
    }
    public FDCSupplierServiceTypeInfo get(Object key)
    {
        return(FDCSupplierServiceTypeInfo)getObject(key);
    }
    public void set(int index, FDCSupplierServiceTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCSupplierServiceTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCSupplierServiceTypeInfo item)
    {
        return super.indexOf(item);
    }
}