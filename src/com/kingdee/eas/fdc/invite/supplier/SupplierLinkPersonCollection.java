package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierLinkPersonCollection extends AbstractObjectCollection 
{
    public SupplierLinkPersonCollection()
    {
        super(SupplierLinkPersonInfo.class);
    }
    public boolean add(SupplierLinkPersonInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierLinkPersonCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierLinkPersonInfo item)
    {
        return removeObject(item);
    }
    public SupplierLinkPersonInfo get(int index)
    {
        return(SupplierLinkPersonInfo)getObject(index);
    }
    public SupplierLinkPersonInfo get(Object key)
    {
        return(SupplierLinkPersonInfo)getObject(key);
    }
    public void set(int index, SupplierLinkPersonInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierLinkPersonInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierLinkPersonInfo item)
    {
        return super.indexOf(item);
    }
}