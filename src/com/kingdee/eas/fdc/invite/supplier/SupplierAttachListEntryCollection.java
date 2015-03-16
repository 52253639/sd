package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierAttachListEntryCollection extends AbstractObjectCollection 
{
    public SupplierAttachListEntryCollection()
    {
        super(SupplierAttachListEntryInfo.class);
    }
    public boolean add(SupplierAttachListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierAttachListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierAttachListEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierAttachListEntryInfo get(int index)
    {
        return(SupplierAttachListEntryInfo)getObject(index);
    }
    public SupplierAttachListEntryInfo get(Object key)
    {
        return(SupplierAttachListEntryInfo)getObject(key);
    }
    public void set(int index, SupplierAttachListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierAttachListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierAttachListEntryInfo item)
    {
        return super.indexOf(item);
    }
}