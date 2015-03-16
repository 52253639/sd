package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BankPaymentCollection extends AbstractObjectCollection 
{
    public BankPaymentCollection()
    {
        super(BankPaymentInfo.class);
    }
    public boolean add(BankPaymentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BankPaymentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BankPaymentInfo item)
    {
        return removeObject(item);
    }
    public BankPaymentInfo get(int index)
    {
        return(BankPaymentInfo)getObject(index);
    }
    public BankPaymentInfo get(Object key)
    {
        return(BankPaymentInfo)getObject(key);
    }
    public void set(int index, BankPaymentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BankPaymentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BankPaymentInfo item)
    {
        return super.indexOf(item);
    }
}