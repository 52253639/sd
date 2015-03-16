package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierAttachListCollection extends AbstractObjectCollection 
{
    public SupplierAttachListCollection()
    {
        super(SupplierAttachListInfo.class);
    }
    public boolean add(SupplierAttachListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierAttachListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierAttachListInfo item)
    {
        return removeObject(item);
    }
    public SupplierAttachListInfo get(int index)
    {
        return(SupplierAttachListInfo)getObject(index);
    }
    public SupplierAttachListInfo get(Object key)
    {
        return(SupplierAttachListInfo)getObject(key);
    }
    public void set(int index, SupplierAttachListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierAttachListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierAttachListInfo item)
    {
        return super.indexOf(item);
    }
}