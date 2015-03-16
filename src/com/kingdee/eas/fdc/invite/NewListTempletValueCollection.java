package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListTempletValueCollection extends AbstractObjectCollection 
{
    public NewListTempletValueCollection()
    {
        super(NewListTempletValueInfo.class);
    }
    public boolean add(NewListTempletValueInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListTempletValueCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListTempletValueInfo item)
    {
        return removeObject(item);
    }
    public NewListTempletValueInfo get(int index)
    {
        return(NewListTempletValueInfo)getObject(index);
    }
    public NewListTempletValueInfo get(Object key)
    {
        return(NewListTempletValueInfo)getObject(key);
    }
    public void set(int index, NewListTempletValueInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListTempletValueInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListTempletValueInfo item)
    {
        return super.indexOf(item);
    }
}