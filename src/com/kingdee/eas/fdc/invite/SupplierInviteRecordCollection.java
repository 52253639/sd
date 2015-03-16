package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierInviteRecordCollection extends AbstractObjectCollection 
{
    public SupplierInviteRecordCollection()
    {
        super(SupplierInviteRecordInfo.class);
    }
    public boolean add(SupplierInviteRecordInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierInviteRecordCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierInviteRecordInfo item)
    {
        return removeObject(item);
    }
    public SupplierInviteRecordInfo get(int index)
    {
        return(SupplierInviteRecordInfo)getObject(index);
    }
    public SupplierInviteRecordInfo get(Object key)
    {
        return(SupplierInviteRecordInfo)getObject(key);
    }
    public void set(int index, SupplierInviteRecordInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierInviteRecordInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierInviteRecordInfo item)
    {
        return super.indexOf(item);
    }
}