package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FeesWarrantEntrysCollection extends AbstractObjectCollection 
{
    public FeesWarrantEntrysCollection()
    {
        super(FeesWarrantEntrysInfo.class);
    }
    public boolean add(FeesWarrantEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FeesWarrantEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FeesWarrantEntrysInfo item)
    {
        return removeObject(item);
    }
    public FeesWarrantEntrysInfo get(int index)
    {
        return(FeesWarrantEntrysInfo)getObject(index);
    }
    public FeesWarrantEntrysInfo get(Object key)
    {
        return(FeesWarrantEntrysInfo)getObject(key);
    }
    public void set(int index, FeesWarrantEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FeesWarrantEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FeesWarrantEntrysInfo item)
    {
        return super.indexOf(item);
    }
}