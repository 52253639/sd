package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteSupplierEnterBillCollection extends AbstractObjectCollection 
{
    public InviteSupplierEnterBillCollection()
    {
        super(InviteSupplierEnterBillInfo.class);
    }
    public boolean add(InviteSupplierEnterBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteSupplierEnterBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteSupplierEnterBillInfo item)
    {
        return removeObject(item);
    }
    public InviteSupplierEnterBillInfo get(int index)
    {
        return(InviteSupplierEnterBillInfo)getObject(index);
    }
    public InviteSupplierEnterBillInfo get(Object key)
    {
        return(InviteSupplierEnterBillInfo)getObject(key);
    }
    public void set(int index, InviteSupplierEnterBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteSupplierEnterBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteSupplierEnterBillInfo item)
    {
        return super.indexOf(item);
    }
}