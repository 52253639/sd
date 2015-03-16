package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachResourceRentEntrysCollection extends AbstractObjectCollection 
{
    public AttachResourceRentEntrysCollection()
    {
        super(AttachResourceRentEntrysInfo.class);
    }
    public boolean add(AttachResourceRentEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachResourceRentEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachResourceRentEntrysInfo item)
    {
        return removeObject(item);
    }
    public AttachResourceRentEntrysInfo get(int index)
    {
        return(AttachResourceRentEntrysInfo)getObject(index);
    }
    public AttachResourceRentEntrysInfo get(Object key)
    {
        return(AttachResourceRentEntrysInfo)getObject(key);
    }
    public void set(int index, AttachResourceRentEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachResourceRentEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachResourceRentEntrysInfo item)
    {
        return super.indexOf(item);
    }
}