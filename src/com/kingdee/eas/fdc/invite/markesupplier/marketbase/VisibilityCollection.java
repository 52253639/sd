package com.kingdee.eas.fdc.invite.markesupplier.marketbase;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class VisibilityCollection extends AbstractObjectCollection 
{
    public VisibilityCollection()
    {
        super(VisibilityInfo.class);
    }
    public boolean add(VisibilityInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(VisibilityCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(VisibilityInfo item)
    {
        return removeObject(item);
    }
    public VisibilityInfo get(int index)
    {
        return(VisibilityInfo)getObject(index);
    }
    public VisibilityInfo get(Object key)
    {
        return(VisibilityInfo)getObject(key);
    }
    public void set(int index, VisibilityInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(VisibilityInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(VisibilityInfo item)
    {
        return super.indexOf(item);
    }
}