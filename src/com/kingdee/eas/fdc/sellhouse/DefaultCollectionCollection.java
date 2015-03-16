package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class DefaultCollectionCollection extends AbstractObjectCollection 
{
    public DefaultCollectionCollection()
    {
        super(DefaultCollectionInfo.class);
    }
    public boolean add(DefaultCollectionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(DefaultCollectionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(DefaultCollectionInfo item)
    {
        return removeObject(item);
    }
    public DefaultCollectionInfo get(int index)
    {
        return(DefaultCollectionInfo)getObject(index);
    }
    public DefaultCollectionInfo get(Object key)
    {
        return(DefaultCollectionInfo)getObject(key);
    }
    public void set(int index, DefaultCollectionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(DefaultCollectionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(DefaultCollectionInfo item)
    {
        return super.indexOf(item);
    }
}