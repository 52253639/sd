package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BizTypeCollection extends AbstractObjectCollection 
{
    public BizTypeCollection()
    {
        super(BizTypeInfo.class);
    }
    public boolean add(BizTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BizTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BizTypeInfo item)
    {
        return removeObject(item);
    }
    public BizTypeInfo get(int index)
    {
        return(BizTypeInfo)getObject(index);
    }
    public BizTypeInfo get(Object key)
    {
        return(BizTypeInfo)getObject(key);
    }
    public void set(int index, BizTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BizTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BizTypeInfo item)
    {
        return super.indexOf(item);
    }
}