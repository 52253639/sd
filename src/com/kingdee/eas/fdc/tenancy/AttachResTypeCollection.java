package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachResTypeCollection extends AbstractObjectCollection 
{
    public AttachResTypeCollection()
    {
        super(AttachResTypeInfo.class);
    }
    public boolean add(AttachResTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachResTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachResTypeInfo item)
    {
        return removeObject(item);
    }
    public AttachResTypeInfo get(int index)
    {
        return(AttachResTypeInfo)getObject(index);
    }
    public AttachResTypeInfo get(Object key)
    {
        return(AttachResTypeInfo)getObject(key);
    }
    public void set(int index, AttachResTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachResTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachResTypeInfo item)
    {
        return super.indexOf(item);
    }
}