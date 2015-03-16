package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FeesWarrantCollection extends AbstractObjectCollection 
{
    public FeesWarrantCollection()
    {
        super(FeesWarrantInfo.class);
    }
    public boolean add(FeesWarrantInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FeesWarrantCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FeesWarrantInfo item)
    {
        return removeObject(item);
    }
    public FeesWarrantInfo get(int index)
    {
        return(FeesWarrantInfo)getObject(index);
    }
    public FeesWarrantInfo get(Object key)
    {
        return(FeesWarrantInfo)getObject(key);
    }
    public void set(int index, FeesWarrantInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FeesWarrantInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FeesWarrantInfo item)
    {
        return super.indexOf(item);
    }
}