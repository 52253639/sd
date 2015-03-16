package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PTEEnonomyCollection extends AbstractObjectCollection 
{
    public PTEEnonomyCollection()
    {
        super(PTEEnonomyInfo.class);
    }
    public boolean add(PTEEnonomyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PTEEnonomyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PTEEnonomyInfo item)
    {
        return removeObject(item);
    }
    public PTEEnonomyInfo get(int index)
    {
        return(PTEEnonomyInfo)getObject(index);
    }
    public PTEEnonomyInfo get(Object key)
    {
        return(PTEEnonomyInfo)getObject(key);
    }
    public void set(int index, PTEEnonomyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PTEEnonomyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PTEEnonomyInfo item)
    {
        return super.indexOf(item);
    }
}