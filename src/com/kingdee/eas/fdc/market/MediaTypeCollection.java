package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MediaTypeCollection extends AbstractObjectCollection 
{
    public MediaTypeCollection()
    {
        super(MediaTypeInfo.class);
    }
    public boolean add(MediaTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MediaTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MediaTypeInfo item)
    {
        return removeObject(item);
    }
    public MediaTypeInfo get(int index)
    {
        return(MediaTypeInfo)getObject(index);
    }
    public MediaTypeInfo get(Object key)
    {
        return(MediaTypeInfo)getObject(key);
    }
    public void set(int index, MediaTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MediaTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MediaTypeInfo item)
    {
        return super.indexOf(item);
    }
}