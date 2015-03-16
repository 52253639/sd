package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierQualifyEntryCollection extends AbstractObjectCollection 
{
    public SupplierQualifyEntryCollection()
    {
        super(SupplierQualifyEntryInfo.class);
    }
    public boolean add(SupplierQualifyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierQualifyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierQualifyEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierQualifyEntryInfo get(int index)
    {
        return(SupplierQualifyEntryInfo)getObject(index);
    }
    public SupplierQualifyEntryInfo get(Object key)
    {
        return(SupplierQualifyEntryInfo)getObject(key);
    }
    public void set(int index, SupplierQualifyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierQualifyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierQualifyEntryInfo item)
    {
        return super.indexOf(item);
    }
}