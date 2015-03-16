package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PerformTypeCollection extends AbstractObjectCollection 
{
    public PerformTypeCollection()
    {
        super(PerformTypeInfo.class);
    }
    public boolean add(PerformTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PerformTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PerformTypeInfo item)
    {
        return removeObject(item);
    }
    public PerformTypeInfo get(int index)
    {
        return(PerformTypeInfo)getObject(index);
    }
    public PerformTypeInfo get(Object key)
    {
        return(PerformTypeInfo)getObject(key);
    }
    public void set(int index, PerformTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PerformTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PerformTypeInfo item)
    {
        return super.indexOf(item);
    }
}