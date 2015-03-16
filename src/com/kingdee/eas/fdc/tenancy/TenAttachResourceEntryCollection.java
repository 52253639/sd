package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenAttachResourceEntryCollection extends AbstractObjectCollection 
{
    public TenAttachResourceEntryCollection()
    {
        super(TenAttachResourceEntryInfo.class);
    }
    public boolean add(TenAttachResourceEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenAttachResourceEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenAttachResourceEntryInfo item)
    {
        return removeObject(item);
    }
    public TenAttachResourceEntryInfo get(int index)
    {
        return(TenAttachResourceEntryInfo)getObject(index);
    }
    public TenAttachResourceEntryInfo get(Object key)
    {
        return(TenAttachResourceEntryInfo)getObject(key);
    }
    public void set(int index, TenAttachResourceEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenAttachResourceEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenAttachResourceEntryInfo item)
    {
        return super.indexOf(item);
    }
}