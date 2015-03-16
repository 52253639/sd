package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProReferenceCollection extends AbstractObjectCollection 
{
    public ProReferenceCollection()
    {
        super(ProReferenceInfo.class);
    }
    public boolean add(ProReferenceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProReferenceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProReferenceInfo item)
    {
        return removeObject(item);
    }
    public ProReferenceInfo get(int index)
    {
        return(ProReferenceInfo)getObject(index);
    }
    public ProReferenceInfo get(Object key)
    {
        return(ProReferenceInfo)getObject(key);
    }
    public void set(int index, ProReferenceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProReferenceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProReferenceInfo item)
    {
        return super.indexOf(item);
    }
}