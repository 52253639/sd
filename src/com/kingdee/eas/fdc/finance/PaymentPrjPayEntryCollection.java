package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaymentPrjPayEntryCollection extends AbstractObjectCollection 
{
    public PaymentPrjPayEntryCollection()
    {
        super(PaymentPrjPayEntryInfo.class);
    }
    public boolean add(PaymentPrjPayEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaymentPrjPayEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaymentPrjPayEntryInfo item)
    {
        return removeObject(item);
    }
    public PaymentPrjPayEntryInfo get(int index)
    {
        return(PaymentPrjPayEntryInfo)getObject(index);
    }
    public PaymentPrjPayEntryInfo get(Object key)
    {
        return(PaymentPrjPayEntryInfo)getObject(key);
    }
    public void set(int index, PaymentPrjPayEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaymentPrjPayEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaymentPrjPayEntryInfo item)
    {
        return super.indexOf(item);
    }
}