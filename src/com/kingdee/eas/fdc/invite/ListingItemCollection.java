package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ListingItemCollection extends AbstractObjectCollection 
{
    public ListingItemCollection()
    {
        super(ListingItemInfo.class);
    }
    public boolean add(ListingItemInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ListingItemCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ListingItemInfo item)
    {
        return removeObject(item);
    }
    public ListingItemInfo get(int index)
    {
        return(ListingItemInfo)getObject(index);
    }
    public ListingItemInfo get(Object key)
    {
        return(ListingItemInfo)getObject(key);
    }
    public void set(int index, ListingItemInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ListingItemInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ListingItemInfo item)
    {
        return super.indexOf(item);
    }
}