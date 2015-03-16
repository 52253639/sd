package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListTempletPageCollection extends AbstractObjectCollection 
{
    public NewListTempletPageCollection()
    {
        super(NewListTempletPageInfo.class);
    }
    public boolean add(NewListTempletPageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListTempletPageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListTempletPageInfo item)
    {
        return removeObject(item);
    }
    public NewListTempletPageInfo get(int index)
    {
        return(NewListTempletPageInfo)getObject(index);
    }
    public NewListTempletPageInfo get(Object key)
    {
        return(NewListTempletPageInfo)getObject(key);
    }
    public void set(int index, NewListTempletPageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListTempletPageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListTempletPageInfo item)
    {
        return super.indexOf(item);
    }
}