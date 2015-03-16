package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHERevBillEntryCollection extends AbstractObjectCollection 
{
    public SHERevBillEntryCollection()
    {
        super(SHERevBillEntryInfo.class);
    }
    public boolean add(SHERevBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHERevBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHERevBillEntryInfo item)
    {
        return removeObject(item);
    }
    public SHERevBillEntryInfo get(int index)
    {
        return(SHERevBillEntryInfo)getObject(index);
    }
    public SHERevBillEntryInfo get(Object key)
    {
        return(SHERevBillEntryInfo)getObject(key);
    }
    public void set(int index, SHERevBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHERevBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHERevBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}