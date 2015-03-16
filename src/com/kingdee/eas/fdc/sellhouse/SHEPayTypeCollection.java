package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEPayTypeCollection extends AbstractObjectCollection 
{
    public SHEPayTypeCollection()
    {
        super(SHEPayTypeInfo.class);
    }
    public boolean add(SHEPayTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEPayTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEPayTypeInfo item)
    {
        return removeObject(item);
    }
    public SHEPayTypeInfo get(int index)
    {
        return(SHEPayTypeInfo)getObject(index);
    }
    public SHEPayTypeInfo get(Object key)
    {
        return(SHEPayTypeInfo)getObject(key);
    }
    public void set(int index, SHEPayTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEPayTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEPayTypeInfo item)
    {
        return super.indexOf(item);
    }
}