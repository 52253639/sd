package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentNoCostSplitCollection extends AbstractObjectCollection 
{
    public PaymentNoCostSplitCollection()
    {
        super(PaymentNoCostSplitInfo.class);
    }
    public boolean add(PaymentNoCostSplitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentNoCostSplitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentNoCostSplitInfo item)
    {
        return removeObject(item);
    }
    public PaymentNoCostSplitInfo get(int index)
    {
        return(PaymentNoCostSplitInfo)getObject(index);
    }
    public PaymentNoCostSplitInfo get(Object key)
    {
        return(PaymentNoCostSplitInfo)getObject(key);
    }
    public void set(int index, PaymentNoCostSplitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentNoCostSplitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentNoCostSplitInfo item)
    {
        return super.indexOf(item);
    }
}