package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListTempletCollection extends AbstractObjectCollection 
{
    public NewListTempletCollection()
    {
        super(NewListTempletInfo.class);
    }
    public boolean add(NewListTempletInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListTempletCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListTempletInfo item)
    {
        return removeObject(item);
    }
    public NewListTempletInfo get(int index)
    {
        return(NewListTempletInfo)getObject(index);
    }
    public NewListTempletInfo get(Object key)
    {
        return(NewListTempletInfo)getObject(key);
    }
    public void set(int index, NewListTempletInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListTempletInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListTempletInfo item)
    {
        return super.indexOf(item);
    }
}