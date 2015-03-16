package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentNoCostSplitEntryCollection extends AbstractObjectCollection 
{
    public PaymentNoCostSplitEntryCollection()
    {
        super(PaymentNoCostSplitEntryInfo.class);
    }
    public boolean add(PaymentNoCostSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentNoCostSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentNoCostSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public PaymentNoCostSplitEntryInfo get(int index)
    {
        return(PaymentNoCostSplitEntryInfo)getObject(index);
    }
    public PaymentNoCostSplitEntryInfo get(Object key)
    {
        return(PaymentNoCostSplitEntryInfo)getObject(key);
    }
    public void set(int index, PaymentNoCostSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentNoCostSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentNoCostSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}