package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingCollection extends AbstractObjectCollection 
{
    public ProgrammingCollection()
    {
        super(ProgrammingInfo.class);
    }
    public boolean add(ProgrammingInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingInfo get(int index)
    {
        return(ProgrammingInfo)getObject(index);
    }
    public ProgrammingInfo get(Object key)
    {
        return(ProgrammingInfo)getObject(key);
    }
    public void set(int index, ProgrammingInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingInfo item)
    {
        return super.indexOf(item);
    }
}