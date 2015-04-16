package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SupplierApplyCollection extends AbstractObjectCollection 
{
    public SupplierApplyCollection()
    {
        super(SupplierApplyInfo.class);
    }
    public boolean add(SupplierApplyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SupplierApplyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SupplierApplyInfo item)
    {
        return removeObject(item);
    }
    public SupplierApplyInfo get(int index)
    {
        return(SupplierApplyInfo)getObject(index);
    }
    public SupplierApplyInfo get(Object key)
    {
        return(SupplierApplyInfo)getObject(key);
    }
    public void set(int index, SupplierApplyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SupplierApplyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SupplierApplyInfo item)
    {
        return super.indexOf(item);
    }
}