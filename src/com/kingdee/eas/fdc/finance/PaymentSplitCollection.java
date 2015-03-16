package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentSplitCollection extends AbstractObjectCollection 
{
    public PaymentSplitCollection()
    {
        super(PaymentSplitInfo.class);
    }
    public boolean add(PaymentSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentSplitInfo item)
    {
        return removeObject(item);
    }
    public PaymentSplitInfo get(int index)
    {
        return(PaymentSplitInfo)getObject(index);
    }
    public PaymentSplitInfo get(Object key)
    {
        return(PaymentSplitInfo)getObject(key);
    }
    public void set(int index, PaymentSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentSplitInfo item)
    {
        return super.indexOf(item);
    }
}