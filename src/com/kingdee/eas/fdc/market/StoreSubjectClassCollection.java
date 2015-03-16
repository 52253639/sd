package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StoreSubjectClassCollection extends AbstractObjectCollection 
{
    public StoreSubjectClassCollection()
    {
        super(StoreSubjectClassInfo.class);
    }
    public boolean add(StoreSubjectClassInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StoreSubjectClassCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StoreSubjectClassInfo item)
    {
        return removeObject(item);
    }
    public StoreSubjectClassInfo get(int index)
    {
        return(StoreSubjectClassInfo)getObject(index);
    }
    public StoreSubjectClassInfo get(Object key)
    {
        return(StoreSubjectClassInfo)getObject(key);
    }
    public void set(int index, StoreSubjectClassInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StoreSubjectClassInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StoreSubjectClassInfo item)
    {
        return super.indexOf(item);
    }
}