package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseCollection extends AbstractObjectCollection 
{
    public AppraiseCollection()
    {
        super(AppraiseInfo.class);
    }
    public boolean add(AppraiseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseInfo item)
    {
        return removeObject(item);
    }
    public AppraiseInfo get(int index)
    {
        return(AppraiseInfo)getObject(index);
    }
    public AppraiseInfo get(Object key)
    {
        return(AppraiseInfo)getObject(key);
    }
    public void set(int index, AppraiseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseInfo item)
    {
        return super.indexOf(item);
    }
}