package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SincerPaylistEntrysCollection extends AbstractObjectCollection 
{
    public SincerPaylistEntrysCollection()
    {
        super(SincerPaylistEntrysInfo.class);
    }
    public boolean add(SincerPaylistEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SincerPaylistEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SincerPaylistEntrysInfo item)
    {
        return removeObject(item);
    }
    public SincerPaylistEntrysInfo get(int index)
    {
        return(SincerPaylistEntrysInfo)getObject(index);
    }
    public SincerPaylistEntrysInfo get(Object key)
    {
        return(SincerPaylistEntrysInfo)getObject(key);
    }
    public void set(int index, SincerPaylistEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SincerPaylistEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SincerPaylistEntrysInfo item)
    {
        return super.indexOf(item);
    }
}