package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FlatLayerTypeCollection extends AbstractObjectCollection 
{
    public FlatLayerTypeCollection()
    {
        super(FlatLayerTypeInfo.class);
    }
    public boolean add(FlatLayerTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FlatLayerTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FlatLayerTypeInfo item)
    {
        return removeObject(item);
    }
    public FlatLayerTypeInfo get(int index)
    {
        return(FlatLayerTypeInfo)getObject(index);
    }
    public FlatLayerTypeInfo get(Object key)
    {
        return(FlatLayerTypeInfo)getObject(key);
    }
    public void set(int index, FlatLayerTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FlatLayerTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FlatLayerTypeInfo item)
    {
        return super.indexOf(item);
    }
}