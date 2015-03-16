package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListingEntryCollection extends AbstractObjectCollection 
{
    public NewListingEntryCollection()
    {
        super(NewListingEntryInfo.class);
    }
    public boolean add(NewListingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListingEntryInfo item)
    {
        return removeObject(item);
    }
    public NewListingEntryInfo get(int index)
    {
        return(NewListingEntryInfo)getObject(index);
    }
    public NewListingEntryInfo get(Object key)
    {
        return(NewListingEntryInfo)getObject(key);
    }
    public void set(int index, NewListingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListingEntryInfo item)
    {
        return super.indexOf(item);
    }
}