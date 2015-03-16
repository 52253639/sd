package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingContractEconomyCollection extends AbstractObjectCollection 
{
    public ProgrammingContractEconomyCollection()
    {
        super(ProgrammingContractEconomyInfo.class);
    }
    public boolean add(ProgrammingContractEconomyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingContractEconomyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingContractEconomyInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingContractEconomyInfo get(int index)
    {
        return(ProgrammingContractEconomyInfo)getObject(index);
    }
    public ProgrammingContractEconomyInfo get(Object key)
    {
        return(ProgrammingContractEconomyInfo)getObject(key);
    }
    public void set(int index, ProgrammingContractEconomyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingContractEconomyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingContractEconomyInfo item)
    {
        return super.indexOf(item);
    }
}