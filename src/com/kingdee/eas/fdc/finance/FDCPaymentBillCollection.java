package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCPaymentBillCollection extends AbstractObjectCollection 
{
    public FDCPaymentBillCollection()
    {
        super(FDCPaymentBillInfo.class);
    }
    public boolean add(FDCPaymentBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCPaymentBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCPaymentBillInfo item)
    {
        return removeObject(item);
    }
    public FDCPaymentBillInfo get(int index)
    {
        return(FDCPaymentBillInfo)getObject(index);
    }
    public FDCPaymentBillInfo get(Object key)
    {
        return(FDCPaymentBillInfo)getObject(key);
    }
    public void set(int index, FDCPaymentBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCPaymentBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCPaymentBillInfo item)
    {
        return super.indexOf(item);
    }
}