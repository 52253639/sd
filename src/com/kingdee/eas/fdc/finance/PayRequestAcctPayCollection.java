package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayRequestAcctPayCollection extends AbstractObjectCollection 
{
    public PayRequestAcctPayCollection()
    {
        super(PayRequestAcctPayInfo.class);
    }
    public boolean add(PayRequestAcctPayInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayRequestAcctPayCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayRequestAcctPayInfo item)
    {
        return removeObject(item);
    }
    public PayRequestAcctPayInfo get(int index)
    {
        return(PayRequestAcctPayInfo)getObject(index);
    }
    public PayRequestAcctPayInfo get(Object key)
    {
        return(PayRequestAcctPayInfo)getObject(key);
    }
    public void set(int index, PayRequestAcctPayInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayRequestAcctPayInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayRequestAcctPayInfo item)
    {
        return super.indexOf(item);
    }
}