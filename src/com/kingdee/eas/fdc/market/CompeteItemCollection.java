package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompeteItemCollection extends AbstractObjectCollection 
{
    public CompeteItemCollection()
    {
        super(CompeteItemInfo.class);
    }
    public boolean add(CompeteItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompeteItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompeteItemInfo item)
    {
        return removeObject(item);
    }
    public CompeteItemInfo get(int index)
    {
        return(CompeteItemInfo)getObject(index);
    }
    public CompeteItemInfo get(Object key)
    {
        return(CompeteItemInfo)getObject(key);
    }
    public void set(int index, CompeteItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompeteItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompeteItemInfo item)
    {
        return super.indexOf(item);
    }
}