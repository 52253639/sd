package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenBillOtherPayCollection extends AbstractObjectCollection 
{
    public TenBillOtherPayCollection()
    {
        super(TenBillOtherPayInfo.class);
    }
    public boolean add(TenBillOtherPayInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenBillOtherPayCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenBillOtherPayInfo item)
    {
        return removeObject(item);
    }
    public TenBillOtherPayInfo get(int index)
    {
        return(TenBillOtherPayInfo)getObject(index);
    }
    public TenBillOtherPayInfo get(Object key)
    {
        return(TenBillOtherPayInfo)getObject(key);
    }
    public void set(int index, TenBillOtherPayInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenBillOtherPayInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenBillOtherPayInfo item)
    {
        return super.indexOf(item);
    }
}