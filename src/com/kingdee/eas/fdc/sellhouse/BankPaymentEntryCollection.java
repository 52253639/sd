package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BankPaymentEntryCollection extends AbstractObjectCollection 
{
    public BankPaymentEntryCollection()
    {
        super(BankPaymentEntryInfo.class);
    }
    public boolean add(BankPaymentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BankPaymentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BankPaymentEntryInfo item)
    {
        return removeObject(item);
    }
    public BankPaymentEntryInfo get(int index)
    {
        return(BankPaymentEntryInfo)getObject(index);
    }
    public BankPaymentEntryInfo get(Object key)
    {
        return(BankPaymentEntryInfo)getObject(key);
    }
    public void set(int index, BankPaymentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BankPaymentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BankPaymentEntryInfo item)
    {
        return super.indexOf(item);
    }
}