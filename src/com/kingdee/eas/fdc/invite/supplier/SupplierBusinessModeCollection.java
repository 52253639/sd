package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierBusinessModeCollection extends AbstractObjectCollection 
{
    public SupplierBusinessModeCollection()
    {
        super(SupplierBusinessModeInfo.class);
    }
    public boolean add(SupplierBusinessModeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierBusinessModeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierBusinessModeInfo item)
    {
        return removeObject(item);
    }
    public SupplierBusinessModeInfo get(int index)
    {
        return(SupplierBusinessModeInfo)getObject(index);
    }
    public SupplierBusinessModeInfo get(Object key)
    {
        return(SupplierBusinessModeInfo)getObject(key);
    }
    public void set(int index, SupplierBusinessModeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierBusinessModeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierBusinessModeInfo item)
    {
        return super.indexOf(item);
    }
}