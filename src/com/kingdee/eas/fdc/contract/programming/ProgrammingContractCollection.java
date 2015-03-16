package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingContractCollection extends AbstractObjectCollection 
{
    public ProgrammingContractCollection()
    {
        super(ProgrammingContractInfo.class);
    }
    public boolean add(ProgrammingContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingContractInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingContractInfo get(int index)
    {
        return(ProgrammingContractInfo)getObject(index);
    }
    public ProgrammingContractInfo get(Object key)
    {
        return(ProgrammingContractInfo)getObject(key);
    }
    public void set(int index, ProgrammingContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingContractInfo item)
    {
        return super.indexOf(item);
    }
}