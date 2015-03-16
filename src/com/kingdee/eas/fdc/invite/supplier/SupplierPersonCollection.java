package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierPersonCollection extends AbstractObjectCollection 
{
    public SupplierPersonCollection()
    {
        super(SupplierPersonInfo.class);
    }
    public boolean add(SupplierPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierPersonInfo item)
    {
        return removeObject(item);
    }
    public SupplierPersonInfo get(int index)
    {
        return(SupplierPersonInfo)getObject(index);
    }
    public SupplierPersonInfo get(Object key)
    {
        return(SupplierPersonInfo)getObject(key);
    }
    public void set(int index, SupplierPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierPersonInfo item)
    {
        return super.indexOf(item);
    }
}