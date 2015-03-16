package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertTypeCollection extends AbstractObjectCollection 
{
    public ExpertTypeCollection()
    {
        super(ExpertTypeInfo.class);
    }
    public boolean add(ExpertTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertTypeInfo item)
    {
        return removeObject(item);
    }
    public ExpertTypeInfo get(int index)
    {
        return(ExpertTypeInfo)getObject(index);
    }
    public ExpertTypeInfo get(Object key)
    {
        return(ExpertTypeInfo)getObject(key);
    }
    public void set(int index, ExpertTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertTypeInfo item)
    {
        return super.indexOf(item);
    }
}