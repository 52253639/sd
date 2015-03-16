package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachResourceRentBillCollection extends AbstractObjectCollection 
{
    public AttachResourceRentBillCollection()
    {
        super(AttachResourceRentBillInfo.class);
    }
    public boolean add(AttachResourceRentBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachResourceRentBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachResourceRentBillInfo item)
    {
        return removeObject(item);
    }
    public AttachResourceRentBillInfo get(int index)
    {
        return(AttachResourceRentBillInfo)getObject(index);
    }
    public AttachResourceRentBillInfo get(Object key)
    {
        return(AttachResourceRentBillInfo)getObject(key);
    }
    public void set(int index, AttachResourceRentBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachResourceRentBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachResourceRentBillInfo item)
    {
        return super.indexOf(item);
    }
}