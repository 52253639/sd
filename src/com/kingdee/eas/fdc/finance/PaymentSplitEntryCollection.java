package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentSplitEntryCollection extends AbstractObjectCollection 
{
    public PaymentSplitEntryCollection()
    {
        super(PaymentSplitEntryInfo.class);
    }
    public boolean add(PaymentSplitEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentSplitEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentSplitEntryInfo item)
    {
        return removeObject(item);
    }
    public PaymentSplitEntryInfo get(int index)
    {
        return(PaymentSplitEntryInfo)getObject(index);
    }
    public PaymentSplitEntryInfo get(Object key)
    {
        return(PaymentSplitEntryInfo)getObject(key);
    }
    public void set(int index, PaymentSplitEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentSplitEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentSplitEntryInfo item)
    {
        return super.indexOf(item);
    }
}