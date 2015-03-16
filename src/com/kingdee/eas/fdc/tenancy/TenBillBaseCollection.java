package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenBillBaseCollection extends AbstractObjectCollection 
{
    public TenBillBaseCollection()
    {
        super(TenBillBaseInfo.class);
    }
    public boolean add(TenBillBaseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenBillBaseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenBillBaseInfo item)
    {
        return removeObject(item);
    }
    public TenBillBaseInfo get(int index)
    {
        return(TenBillBaseInfo)getObject(index);
    }
    public TenBillBaseInfo get(Object key)
    {
        return(TenBillBaseInfo)getObject(key);
    }
    public void set(int index, TenBillBaseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenBillBaseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenBillBaseInfo item)
    {
        return super.indexOf(item);
    }
}