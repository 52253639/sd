package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseTempletTypeCollection extends AbstractObjectCollection 
{
    public AppraiseTempletTypeCollection()
    {
        super(AppraiseTempletTypeInfo.class);
    }
    public boolean add(AppraiseTempletTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseTempletTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseTempletTypeInfo item)
    {
        return removeObject(item);
    }
    public AppraiseTempletTypeInfo get(int index)
    {
        return(AppraiseTempletTypeInfo)getObject(index);
    }
    public AppraiseTempletTypeInfo get(Object key)
    {
        return(AppraiseTempletTypeInfo)getObject(key);
    }
    public void set(int index, AppraiseTempletTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseTempletTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseTempletTypeInfo item)
    {
        return super.indexOf(item);
    }
}