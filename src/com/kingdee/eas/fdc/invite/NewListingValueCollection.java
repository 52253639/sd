package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListingValueCollection extends AbstractObjectCollection 
{
    public NewListingValueCollection()
    {
        super(NewListingValueInfo.class);
    }
    public boolean add(NewListingValueInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListingValueCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListingValueInfo item)
    {
        return removeObject(item);
    }
    public NewListingValueInfo get(int index)
    {
        return(NewListingValueInfo)getObject(index);
    }
    public NewListingValueInfo get(Object key)
    {
        return(NewListingValueInfo)getObject(key);
    }
    public void set(int index, NewListingValueInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListingValueInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListingValueInfo item)
    {
        return super.indexOf(item);
    }
}