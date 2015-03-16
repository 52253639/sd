package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListingPageCollection extends AbstractObjectCollection 
{
    public NewListingPageCollection()
    {
        super(NewListingPageInfo.class);
    }
    public boolean add(NewListingPageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListingPageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListingPageInfo item)
    {
        return removeObject(item);
    }
    public NewListingPageInfo get(int index)
    {
        return(NewListingPageInfo)getObject(index);
    }
    public NewListingPageInfo get(Object key)
    {
        return(NewListingPageInfo)getObject(key);
    }
    public void set(int index, NewListingPageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListingPageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListingPageInfo item)
    {
        return super.indexOf(item);
    }
}