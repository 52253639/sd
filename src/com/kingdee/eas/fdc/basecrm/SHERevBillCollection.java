package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHERevBillCollection extends AbstractObjectCollection 
{
    public SHERevBillCollection()
    {
        super(SHERevBillInfo.class);
    }
    public boolean add(SHERevBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHERevBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHERevBillInfo item)
    {
        return removeObject(item);
    }
    public SHERevBillInfo get(int index)
    {
        return(SHERevBillInfo)getObject(index);
    }
    public SHERevBillInfo get(Object key)
    {
        return(SHERevBillInfo)getObject(key);
    }
    public void set(int index, SHERevBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHERevBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHERevBillInfo item)
    {
        return super.indexOf(item);
    }
}