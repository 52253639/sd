package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AttachResourceCollection extends AbstractObjectCollection 
{
    public AttachResourceCollection()
    {
        super(AttachResourceInfo.class);
    }
    public boolean add(AttachResourceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AttachResourceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AttachResourceInfo item)
    {
        return removeObject(item);
    }
    public AttachResourceInfo get(int index)
    {
        return(AttachResourceInfo)getObject(index);
    }
    public AttachResourceInfo get(Object key)
    {
        return(AttachResourceInfo)getObject(key);
    }
    public void set(int index, AttachResourceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AttachResourceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AttachResourceInfo item)
    {
        return super.indexOf(item);
    }
}