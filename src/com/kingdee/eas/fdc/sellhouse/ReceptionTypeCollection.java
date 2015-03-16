package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReceptionTypeCollection extends AbstractObjectCollection 
{
    public ReceptionTypeCollection()
    {
        super(ReceptionTypeInfo.class);
    }
    public boolean add(ReceptionTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReceptionTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReceptionTypeInfo item)
    {
        return removeObject(item);
    }
    public ReceptionTypeInfo get(int index)
    {
        return(ReceptionTypeInfo)getObject(index);
    }
    public ReceptionTypeInfo get(Object key)
    {
        return(ReceptionTypeInfo)getObject(key);
    }
    public void set(int index, ReceptionTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReceptionTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReceptionTypeInfo item)
    {
        return super.indexOf(item);
    }
}