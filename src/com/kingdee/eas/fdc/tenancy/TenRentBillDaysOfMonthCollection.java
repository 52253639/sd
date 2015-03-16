package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenRentBillDaysOfMonthCollection extends AbstractObjectCollection 
{
    public TenRentBillDaysOfMonthCollection()
    {
        super(TenRentBillDaysOfMonthInfo.class);
    }
    public boolean add(TenRentBillDaysOfMonthInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenRentBillDaysOfMonthCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenRentBillDaysOfMonthInfo item)
    {
        return removeObject(item);
    }
    public TenRentBillDaysOfMonthInfo get(int index)
    {
        return(TenRentBillDaysOfMonthInfo)getObject(index);
    }
    public TenRentBillDaysOfMonthInfo get(Object key)
    {
        return(TenRentBillDaysOfMonthInfo)getObject(key);
    }
    public void set(int index, TenRentBillDaysOfMonthInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenRentBillDaysOfMonthInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenRentBillDaysOfMonthInfo item)
    {
        return super.indexOf(item);
    }
}