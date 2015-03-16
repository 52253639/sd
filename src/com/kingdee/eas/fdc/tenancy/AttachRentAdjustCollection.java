package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachRentAdjustCollection extends AbstractObjectCollection 
{
    public AttachRentAdjustCollection()
    {
        super(AttachRentAdjustInfo.class);
    }
    public boolean add(AttachRentAdjustInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachRentAdjustCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachRentAdjustInfo item)
    {
        return removeObject(item);
    }
    public AttachRentAdjustInfo get(int index)
    {
        return(AttachRentAdjustInfo)getObject(index);
    }
    public AttachRentAdjustInfo get(Object key)
    {
        return(AttachRentAdjustInfo)getObject(key);
    }
    public void set(int index, AttachRentAdjustInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachRentAdjustInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachRentAdjustInfo item)
    {
        return super.indexOf(item);
    }
}