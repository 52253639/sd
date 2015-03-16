package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyBillCollection extends AbstractObjectCollection 
{
    public TenancyBillCollection()
    {
        super(TenancyBillInfo.class);
    }
    public boolean add(TenancyBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyBillInfo item)
    {
        return removeObject(item);
    }
    public TenancyBillInfo get(int index)
    {
        return(TenancyBillInfo)getObject(index);
    }
    public TenancyBillInfo get(Object key)
    {
        return(TenancyBillInfo)getObject(key);
    }
    public void set(int index, TenancyBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyBillInfo item)
    {
        return super.indexOf(item);
    }
}