package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StoreItemCollection extends AbstractObjectCollection 
{
    public StoreItemCollection()
    {
        super(StoreItemInfo.class);
    }
    public boolean add(StoreItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StoreItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StoreItemInfo item)
    {
        return removeObject(item);
    }
    public StoreItemInfo get(int index)
    {
        return(StoreItemInfo)getObject(index);
    }
    public StoreItemInfo get(Object key)
    {
        return(StoreItemInfo)getObject(key);
    }
    public void set(int index, StoreItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StoreItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StoreItemInfo item)
    {
        return super.indexOf(item);
    }
}