package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListTempletColumnCollection extends AbstractObjectCollection 
{
    public NewListTempletColumnCollection()
    {
        super(NewListTempletColumnInfo.class);
    }
    public boolean add(NewListTempletColumnInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListTempletColumnCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListTempletColumnInfo item)
    {
        return removeObject(item);
    }
    public NewListTempletColumnInfo get(int index)
    {
        return(NewListTempletColumnInfo)getObject(index);
    }
    public NewListTempletColumnInfo get(Object key)
    {
        return(NewListTempletColumnInfo)getObject(key);
    }
    public void set(int index, NewListTempletColumnInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListTempletColumnInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListTempletColumnInfo item)
    {
        return super.indexOf(item);
    }
}