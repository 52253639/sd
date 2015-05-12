package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DepositDealBillCollection extends AbstractObjectCollection 
{
    public DepositDealBillCollection()
    {
        super(DepositDealBillInfo.class);
    }
    public boolean add(DepositDealBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DepositDealBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DepositDealBillInfo item)
    {
        return removeObject(item);
    }
    public DepositDealBillInfo get(int index)
    {
        return(DepositDealBillInfo)getObject(index);
    }
    public DepositDealBillInfo get(Object key)
    {
        return(DepositDealBillInfo)getObject(key);
    }
    public void set(int index, DepositDealBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DepositDealBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DepositDealBillInfo item)
    {
        return super.indexOf(item);
    }
}