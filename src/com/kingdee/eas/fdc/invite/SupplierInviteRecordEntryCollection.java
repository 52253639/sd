package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierInviteRecordEntryCollection extends AbstractObjectCollection 
{
    public SupplierInviteRecordEntryCollection()
    {
        super(SupplierInviteRecordEntryInfo.class);
    }
    public boolean add(SupplierInviteRecordEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierInviteRecordEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierInviteRecordEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierInviteRecordEntryInfo get(int index)
    {
        return(SupplierInviteRecordEntryInfo)getObject(index);
    }
    public SupplierInviteRecordEntryInfo get(Object key)
    {
        return(SupplierInviteRecordEntryInfo)getObject(key);
    }
    public void set(int index, SupplierInviteRecordEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierInviteRecordEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierInviteRecordEntryInfo item)
    {
        return super.indexOf(item);
    }
}