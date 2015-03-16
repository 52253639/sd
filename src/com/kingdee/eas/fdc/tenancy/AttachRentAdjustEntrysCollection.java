package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachRentAdjustEntrysCollection extends AbstractObjectCollection 
{
    public AttachRentAdjustEntrysCollection()
    {
        super(AttachRentAdjustEntrysInfo.class);
    }
    public boolean add(AttachRentAdjustEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachRentAdjustEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachRentAdjustEntrysInfo item)
    {
        return removeObject(item);
    }
    public AttachRentAdjustEntrysInfo get(int index)
    {
        return(AttachRentAdjustEntrysInfo)getObject(index);
    }
    public AttachRentAdjustEntrysInfo get(Object key)
    {
        return(AttachRentAdjustEntrysInfo)getObject(key);
    }
    public void set(int index, AttachRentAdjustEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachRentAdjustEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachRentAdjustEntrysInfo item)
    {
        return super.indexOf(item);
    }
}