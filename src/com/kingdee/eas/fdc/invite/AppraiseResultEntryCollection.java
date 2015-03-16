package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseResultEntryCollection extends AbstractObjectCollection 
{
    public AppraiseResultEntryCollection()
    {
        super(AppraiseResultEntryInfo.class);
    }
    public boolean add(AppraiseResultEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseResultEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseResultEntryInfo item)
    {
        return removeObject(item);
    }
    public AppraiseResultEntryInfo get(int index)
    {
        return(AppraiseResultEntryInfo)getObject(index);
    }
    public AppraiseResultEntryInfo get(Object key)
    {
        return(AppraiseResultEntryInfo)getObject(key);
    }
    public void set(int index, AppraiseResultEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseResultEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseResultEntryInfo item)
    {
        return super.indexOf(item);
    }
}