package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChangeWFTypeCollection extends AbstractObjectCollection 
{
    public ChangeWFTypeCollection()
    {
        super(ChangeWFTypeInfo.class);
    }
    public boolean add(ChangeWFTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChangeWFTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChangeWFTypeInfo item)
    {
        return removeObject(item);
    }
    public ChangeWFTypeInfo get(int index)
    {
        return(ChangeWFTypeInfo)getObject(index);
    }
    public ChangeWFTypeInfo get(Object key)
    {
        return(ChangeWFTypeInfo)getObject(key);
    }
    public void set(int index, ChangeWFTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChangeWFTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChangeWFTypeInfo item)
    {
        return super.indexOf(item);
    }
}