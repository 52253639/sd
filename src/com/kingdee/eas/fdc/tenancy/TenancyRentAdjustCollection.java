package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyRentAdjustCollection extends AbstractObjectCollection 
{
    public TenancyRentAdjustCollection()
    {
        super(TenancyRentAdjustInfo.class);
    }
    public boolean add(TenancyRentAdjustInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyRentAdjustCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyRentAdjustInfo item)
    {
        return removeObject(item);
    }
    public TenancyRentAdjustInfo get(int index)
    {
        return(TenancyRentAdjustInfo)getObject(index);
    }
    public TenancyRentAdjustInfo get(Object key)
    {
        return(TenancyRentAdjustInfo)getObject(key);
    }
    public void set(int index, TenancyRentAdjustInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyRentAdjustInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyRentAdjustInfo item)
    {
        return super.indexOf(item);
    }
}