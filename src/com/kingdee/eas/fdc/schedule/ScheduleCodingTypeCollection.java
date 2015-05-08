package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ScheduleCodingTypeCollection extends AbstractObjectCollection 
{
    public ScheduleCodingTypeCollection()
    {
        super(ScheduleCodingTypeInfo.class);
    }
    public boolean add(ScheduleCodingTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ScheduleCodingTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ScheduleCodingTypeInfo item)
    {
        return removeObject(item);
    }
    public ScheduleCodingTypeInfo get(int index)
    {
        return(ScheduleCodingTypeInfo)getObject(index);
    }
    public ScheduleCodingTypeInfo get(Object key)
    {
        return(ScheduleCodingTypeInfo)getObject(key);
    }
    public void set(int index, ScheduleCodingTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ScheduleCodingTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ScheduleCodingTypeInfo item)
    {
        return super.indexOf(item);
    }
}