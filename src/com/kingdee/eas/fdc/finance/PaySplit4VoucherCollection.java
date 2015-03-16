package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PaySplit4VoucherCollection extends AbstractObjectCollection 
{
    public PaySplit4VoucherCollection()
    {
        super(PaySplit4VoucherInfo.class);
    }
    public boolean add(PaySplit4VoucherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PaySplit4VoucherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PaySplit4VoucherInfo item)
    {
        return removeObject(item);
    }
    public PaySplit4VoucherInfo get(int index)
    {
        return(PaySplit4VoucherInfo)getObject(index);
    }
    public PaySplit4VoucherInfo get(Object key)
    {
        return(PaySplit4VoucherInfo)getObject(key);
    }
    public void set(int index, PaySplit4VoucherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PaySplit4VoucherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PaySplit4VoucherInfo item)
    {
        return super.indexOf(item);
    }
}