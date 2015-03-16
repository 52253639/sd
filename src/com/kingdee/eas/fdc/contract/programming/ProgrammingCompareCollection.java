package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingCompareCollection extends AbstractObjectCollection 
{
    public ProgrammingCompareCollection()
    {
        super(ProgrammingCompareInfo.class);
    }
    public boolean add(ProgrammingCompareInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingCompareCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingCompareInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingCompareInfo get(int index)
    {
        return(ProgrammingCompareInfo)getObject(index);
    }
    public ProgrammingCompareInfo get(Object key)
    {
        return(ProgrammingCompareInfo)getObject(key);
    }
    public void set(int index, ProgrammingCompareInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingCompareInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingCompareInfo item)
    {
        return super.indexOf(item);
    }
}