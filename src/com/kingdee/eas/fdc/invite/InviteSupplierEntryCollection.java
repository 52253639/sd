package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteSupplierEntryCollection extends AbstractObjectCollection 
{
    public InviteSupplierEntryCollection()
    {
        super(InviteSupplierEntryInfo.class);
    }
    public boolean add(InviteSupplierEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteSupplierEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteSupplierEntryInfo item)
    {
        return removeObject(item);
    }
    public InviteSupplierEntryInfo get(int index)
    {
        return(InviteSupplierEntryInfo)getObject(index);
    }
    public InviteSupplierEntryInfo get(Object key)
    {
        return(InviteSupplierEntryInfo)getObject(key);
    }
    public void set(int index, InviteSupplierEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteSupplierEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteSupplierEntryInfo item)
    {
        return super.indexOf(item);
    }
}