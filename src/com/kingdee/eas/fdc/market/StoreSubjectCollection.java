package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StoreSubjectCollection extends AbstractObjectCollection 
{
    public StoreSubjectCollection()
    {
        super(StoreSubjectInfo.class);
    }
    public boolean add(StoreSubjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StoreSubjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StoreSubjectInfo item)
    {
        return removeObject(item);
    }
    public StoreSubjectInfo get(int index)
    {
        return(StoreSubjectInfo)getObject(index);
    }
    public StoreSubjectInfo get(Object key)
    {
        return(StoreSubjectInfo)getObject(key);
    }
    public void set(int index, StoreSubjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StoreSubjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StoreSubjectInfo item)
    {
        return super.indexOf(item);
    }
}