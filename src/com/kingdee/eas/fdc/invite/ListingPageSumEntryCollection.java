package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ListingPageSumEntryCollection extends AbstractObjectCollection 
{
    public ListingPageSumEntryCollection()
    {
        super(ListingPageSumEntryInfo.class);
    }
    public boolean add(ListingPageSumEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ListingPageSumEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ListingPageSumEntryInfo item)
    {
        return removeObject(item);
    }
    public ListingPageSumEntryInfo get(int index)
    {
        return(ListingPageSumEntryInfo)getObject(index);
    }
    public ListingPageSumEntryInfo get(Object key)
    {
        return(ListingPageSumEntryInfo)getObject(key);
    }
    public void set(int index, ListingPageSumEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ListingPageSumEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ListingPageSumEntryInfo item)
    {
        return super.indexOf(item);
    }
}