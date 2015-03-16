package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierSplAreaEntryCollection extends AbstractObjectCollection 
{
    public SupplierSplAreaEntryCollection()
    {
        super(SupplierSplAreaEntryInfo.class);
    }
    public boolean add(SupplierSplAreaEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierSplAreaEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierSplAreaEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierSplAreaEntryInfo get(int index)
    {
        return(SupplierSplAreaEntryInfo)getObject(index);
    }
    public SupplierSplAreaEntryInfo get(Object key)
    {
        return(SupplierSplAreaEntryInfo)getObject(key);
    }
    public void set(int index, SupplierSplAreaEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierSplAreaEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierSplAreaEntryInfo item)
    {
        return super.indexOf(item);
    }
}