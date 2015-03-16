package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingContracCostCollection extends AbstractObjectCollection 
{
    public ProgrammingContracCostCollection()
    {
        super(ProgrammingContracCostInfo.class);
    }
    public boolean add(ProgrammingContracCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingContracCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingContracCostInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingContracCostInfo get(int index)
    {
        return(ProgrammingContracCostInfo)getObject(index);
    }
    public ProgrammingContracCostInfo get(Object key)
    {
        return(ProgrammingContracCostInfo)getObject(key);
    }
    public void set(int index, ProgrammingContracCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingContracCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingContracCostInfo item)
    {
        return super.indexOf(item);
    }
}