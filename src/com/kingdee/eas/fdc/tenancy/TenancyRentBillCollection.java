package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyRentBillCollection extends AbstractObjectCollection 
{
    public TenancyRentBillCollection()
    {
        super(TenancyRentBillInfo.class);
    }
    public boolean add(TenancyRentBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyRentBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyRentBillInfo item)
    {
        return removeObject(item);
    }
    public TenancyRentBillInfo get(int index)
    {
        return(TenancyRentBillInfo)getObject(index);
    }
    public TenancyRentBillInfo get(Object key)
    {
        return(TenancyRentBillInfo)getObject(key);
    }
    public void set(int index, TenancyRentBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyRentBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyRentBillInfo item)
    {
        return super.indexOf(item);
    }
}