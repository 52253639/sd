package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierRGContractEntryCollection extends AbstractObjectCollection 
{
    public SupplierRGContractEntryCollection()
    {
        super(SupplierRGContractEntryInfo.class);
    }
    public boolean add(SupplierRGContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierRGContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierRGContractEntryInfo item)
    {
        return removeObject(item);
    }
    public SupplierRGContractEntryInfo get(int index)
    {
        return(SupplierRGContractEntryInfo)getObject(index);
    }
    public SupplierRGContractEntryInfo get(Object key)
    {
        return(SupplierRGContractEntryInfo)getObject(key);
    }
    public void set(int index, SupplierRGContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierRGContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierRGContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}