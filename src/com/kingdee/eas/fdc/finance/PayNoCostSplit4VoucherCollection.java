package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PayNoCostSplit4VoucherCollection extends AbstractObjectCollection 
{
    public PayNoCostSplit4VoucherCollection()
    {
        super(PayNoCostSplit4VoucherInfo.class);
    }
    public boolean add(PayNoCostSplit4VoucherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PayNoCostSplit4VoucherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PayNoCostSplit4VoucherInfo item)
    {
        return removeObject(item);
    }
    public PayNoCostSplit4VoucherInfo get(int index)
    {
        return(PayNoCostSplit4VoucherInfo)getObject(index);
    }
    public PayNoCostSplit4VoucherInfo get(Object key)
    {
        return(PayNoCostSplit4VoucherInfo)getObject(key);
    }
    public void set(int index, PayNoCostSplit4VoucherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PayNoCostSplit4VoucherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PayNoCostSplit4VoucherInfo item)
    {
        return super.indexOf(item);
    }
}