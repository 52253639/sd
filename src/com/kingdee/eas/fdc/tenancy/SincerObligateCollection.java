package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SincerObligateCollection extends AbstractObjectCollection 
{
    public SincerObligateCollection()
    {
        super(SincerObligateInfo.class);
    }
    public boolean add(SincerObligateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SincerObligateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SincerObligateInfo item)
    {
        return removeObject(item);
    }
    public SincerObligateInfo get(int index)
    {
        return(SincerObligateInfo)getObject(index);
    }
    public SincerObligateInfo get(Object key)
    {
        return(SincerObligateInfo)getObject(key);
    }
    public void set(int index, SincerObligateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SincerObligateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SincerObligateInfo item)
    {
        return super.indexOf(item);
    }
}