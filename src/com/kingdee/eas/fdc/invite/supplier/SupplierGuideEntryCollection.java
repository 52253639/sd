package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierGuideEntryCollection extends AbstractObjectCollection 
{
    public SupplierGuideEntryCollection()
    {
        super(SupplierGuideEntryInfo.class);
    }
    public boolean add(SupplierGuideEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierGuideEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierGuideEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierGuideEntryInfo get(int index)
    {
        return(SupplierGuideEntryInfo)getObject(index);
    }
    public SupplierGuideEntryInfo get(Object key)
    {
        return(SupplierGuideEntryInfo)getObject(key);
    }
    public void set(int index, SupplierGuideEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierGuideEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierGuideEntryInfo item)
    {
        return super.indexOf(item);
    }
}