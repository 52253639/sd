package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GTNBizTypeCollection extends AbstractObjectCollection 
{
    public GTNBizTypeCollection()
    {
        super(GTNBizTypeInfo.class);
    }
    public boolean add(GTNBizTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GTNBizTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GTNBizTypeInfo item)
    {
        return removeObject(item);
    }
    public GTNBizTypeInfo get(int index)
    {
        return(GTNBizTypeInfo)getObject(index);
    }
    public GTNBizTypeInfo get(Object key)
    {
        return(GTNBizTypeInfo)getObject(key);
    }
    public void set(int index, GTNBizTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GTNBizTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GTNBizTypeInfo item)
    {
        return super.indexOf(item);
    }
}