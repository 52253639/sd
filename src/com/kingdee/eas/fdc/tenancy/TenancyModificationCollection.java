package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyModificationCollection extends AbstractObjectCollection 
{
    public TenancyModificationCollection()
    {
        super(TenancyModificationInfo.class);
    }
    public boolean add(TenancyModificationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyModificationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyModificationInfo item)
    {
        return removeObject(item);
    }
    public TenancyModificationInfo get(int index)
    {
        return(TenancyModificationInfo)getObject(index);
    }
    public TenancyModificationInfo get(Object key)
    {
        return(TenancyModificationInfo)getObject(key);
    }
    public void set(int index, TenancyModificationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyModificationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyModificationInfo item)
    {
        return super.indexOf(item);
    }
}