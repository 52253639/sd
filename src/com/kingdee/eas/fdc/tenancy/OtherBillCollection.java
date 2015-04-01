package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherBillCollection extends AbstractObjectCollection 
{
    public OtherBillCollection()
    {
        super(OtherBillInfo.class);
    }
    public boolean add(OtherBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherBillInfo item)
    {
        return removeObject(item);
    }
    public OtherBillInfo get(int index)
    {
        return(OtherBillInfo)getObject(index);
    }
    public OtherBillInfo get(Object key)
    {
        return(OtherBillInfo)getObject(key);
    }
    public void set(int index, OtherBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherBillInfo item)
    {
        return super.indexOf(item);
    }
}