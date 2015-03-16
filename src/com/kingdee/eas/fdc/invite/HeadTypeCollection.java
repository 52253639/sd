package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HeadTypeCollection extends AbstractObjectCollection 
{
    public HeadTypeCollection()
    {
        super(HeadTypeInfo.class);
    }
    public boolean add(HeadTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HeadTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HeadTypeInfo item)
    {
        return removeObject(item);
    }
    public HeadTypeInfo get(int index)
    {
        return(HeadTypeInfo)getObject(index);
    }
    public HeadTypeInfo get(Object key)
    {
        return(HeadTypeInfo)getObject(key);
    }
    public void set(int index, HeadTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HeadTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HeadTypeInfo item)
    {
        return super.indexOf(item);
    }
}