package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierQualifyCollection extends AbstractObjectCollection 
{
    public SupplierQualifyCollection()
    {
        super(SupplierQualifyInfo.class);
    }
    public boolean add(SupplierQualifyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierQualifyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierQualifyInfo item)
    {
        return removeObject(item);
    }
    public SupplierQualifyInfo get(int index)
    {
        return(SupplierQualifyInfo)getObject(index);
    }
    public SupplierQualifyInfo get(Object key)
    {
        return(SupplierQualifyInfo)getObject(key);
    }
    public void set(int index, SupplierQualifyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierQualifyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierQualifyInfo item)
    {
        return super.indexOf(item);
    }
}