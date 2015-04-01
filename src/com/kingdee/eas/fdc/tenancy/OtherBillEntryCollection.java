package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OtherBillEntryCollection extends AbstractObjectCollection 
{
    public OtherBillEntryCollection()
    {
        super(OtherBillEntryInfo.class);
    }
    public boolean add(OtherBillEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OtherBillEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OtherBillEntryInfo item)
    {
        return removeObject(item);
    }
    public OtherBillEntryInfo get(int index)
    {
        return(OtherBillEntryInfo)getObject(index);
    }
    public OtherBillEntryInfo get(Object key)
    {
        return(OtherBillEntryInfo)getObject(key);
    }
    public void set(int index, OtherBillEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OtherBillEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OtherBillEntryInfo item)
    {
        return super.indexOf(item);
    }
}