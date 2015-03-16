package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SinObligateAttachEntryCollection extends AbstractObjectCollection 
{
    public SinObligateAttachEntryCollection()
    {
        super(SinObligateAttachEntryInfo.class);
    }
    public boolean add(SinObligateAttachEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SinObligateAttachEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SinObligateAttachEntryInfo item)
    {
        return removeObject(item);
    }
    public SinObligateAttachEntryInfo get(int index)
    {
        return(SinObligateAttachEntryInfo)getObject(index);
    }
    public SinObligateAttachEntryInfo get(Object key)
    {
        return(SinObligateAttachEntryInfo)getObject(key);
    }
    public void set(int index, SinObligateAttachEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SinObligateAttachEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SinObligateAttachEntryInfo item)
    {
        return super.indexOf(item);
    }
}