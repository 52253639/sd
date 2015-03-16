package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyOrderCollection extends AbstractObjectCollection 
{
    public TenancyOrderCollection()
    {
        super(TenancyOrderInfo.class);
    }
    public boolean add(TenancyOrderInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyOrderCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyOrderInfo item)
    {
        return removeObject(item);
    }
    public TenancyOrderInfo get(int index)
    {
        return(TenancyOrderInfo)getObject(index);
    }
    public TenancyOrderInfo get(Object key)
    {
        return(TenancyOrderInfo)getObject(key);
    }
    public void set(int index, TenancyOrderInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyOrderInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyOrderInfo item)
    {
        return super.indexOf(item);
    }
}