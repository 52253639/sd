package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildigRentEntrysCollection extends AbstractObjectCollection 
{
    public BuildigRentEntrysCollection()
    {
        super(BuildigRentEntrysInfo.class);
    }
    public boolean add(BuildigRentEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildigRentEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildigRentEntrysInfo item)
    {
        return removeObject(item);
    }
    public BuildigRentEntrysInfo get(int index)
    {
        return(BuildigRentEntrysInfo)getObject(index);
    }
    public BuildigRentEntrysInfo get(Object key)
    {
        return(BuildigRentEntrysInfo)getObject(key);
    }
    public void set(int index, BuildigRentEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildigRentEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildigRentEntrysInfo item)
    {
        return super.indexOf(item);
    }
}