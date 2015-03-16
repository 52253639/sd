package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StoreOptionCollection extends AbstractObjectCollection 
{
    public StoreOptionCollection()
    {
        super(StoreOptionInfo.class);
    }
    public boolean add(StoreOptionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StoreOptionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StoreOptionInfo item)
    {
        return removeObject(item);
    }
    public StoreOptionInfo get(int index)
    {
        return(StoreOptionInfo)getObject(index);
    }
    public StoreOptionInfo get(Object key)
    {
        return(StoreOptionInfo)getObject(key);
    }
    public void set(int index, StoreOptionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StoreOptionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StoreOptionInfo item)
    {
        return super.indexOf(item);
    }
}