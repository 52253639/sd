package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenAttachResourcePayListEntryCollection extends AbstractObjectCollection 
{
    public TenAttachResourcePayListEntryCollection()
    {
        super(TenAttachResourcePayListEntryInfo.class);
    }
    public boolean add(TenAttachResourcePayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenAttachResourcePayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenAttachResourcePayListEntryInfo item)
    {
        return removeObject(item);
    }
    public TenAttachResourcePayListEntryInfo get(int index)
    {
        return(TenAttachResourcePayListEntryInfo)getObject(index);
    }
    public TenAttachResourcePayListEntryInfo get(Object key)
    {
        return(TenAttachResourcePayListEntryInfo)getObject(key);
    }
    public void set(int index, TenAttachResourcePayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenAttachResourcePayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenAttachResourcePayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}