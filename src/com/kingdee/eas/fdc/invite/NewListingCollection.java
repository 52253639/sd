package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListingCollection extends AbstractObjectCollection 
{
    public NewListingCollection()
    {
        super(NewListingInfo.class);
    }
    public boolean add(NewListingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListingInfo item)
    {
        return removeObject(item);
    }
    public NewListingInfo get(int index)
    {
        return(NewListingInfo)getObject(index);
    }
    public NewListingInfo get(Object key)
    {
        return(NewListingInfo)getObject(key);
    }
    public void set(int index, NewListingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListingInfo item)
    {
        return super.indexOf(item);
    }
}