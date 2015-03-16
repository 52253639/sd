package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CollectionCollection extends AbstractObjectCollection 
{
    public CollectionCollection()
    {
        super(CollectionInfo.class);
    }
    public boolean add(CollectionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CollectionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CollectionInfo item)
    {
        return removeObject(item);
    }
    public CollectionInfo get(int index)
    {
        return(CollectionInfo)getObject(index);
    }
    public CollectionInfo get(Object key)
    {
        return(CollectionInfo)getObject(key);
    }
    public void set(int index, CollectionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CollectionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CollectionInfo item)
    {
        return super.indexOf(item);
    }
}