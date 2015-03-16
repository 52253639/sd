package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseResultCollection extends AbstractObjectCollection 
{
    public AppraiseResultCollection()
    {
        super(AppraiseResultInfo.class);
    }
    public boolean add(AppraiseResultInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseResultCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseResultInfo item)
    {
        return removeObject(item);
    }
    public AppraiseResultInfo get(int index)
    {
        return(AppraiseResultInfo)getObject(index);
    }
    public AppraiseResultInfo get(Object key)
    {
        return(AppraiseResultInfo)getObject(key);
    }
    public void set(int index, AppraiseResultInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseResultInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseResultInfo item)
    {
        return super.indexOf(item);
    }
}