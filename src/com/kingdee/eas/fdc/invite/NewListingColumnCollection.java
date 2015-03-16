package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListingColumnCollection extends AbstractObjectCollection 
{
    public NewListingColumnCollection()
    {
        super(NewListingColumnInfo.class);
    }
    public boolean add(NewListingColumnInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListingColumnCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListingColumnInfo item)
    {
        return removeObject(item);
    }
    public NewListingColumnInfo get(int index)
    {
        return(NewListingColumnInfo)getObject(index);
    }
    public NewListingColumnInfo get(Object key)
    {
        return(NewListingColumnInfo)getObject(key);
    }
    public void set(int index, NewListingColumnInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListingColumnInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListingColumnInfo item)
    {
        return super.indexOf(item);
    }
}