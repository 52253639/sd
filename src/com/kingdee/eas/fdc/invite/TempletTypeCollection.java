package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TempletTypeCollection extends AbstractObjectCollection 
{
    public TempletTypeCollection()
    {
        super(TempletTypeInfo.class);
    }
    public boolean add(TempletTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TempletTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TempletTypeInfo item)
    {
        return removeObject(item);
    }
    public TempletTypeInfo get(int index)
    {
        return(TempletTypeInfo)getObject(index);
    }
    public TempletTypeInfo get(Object key)
    {
        return(TempletTypeInfo)getObject(key);
    }
    public void set(int index, TempletTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TempletTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TempletTypeInfo item)
    {
        return super.indexOf(item);
    }
}