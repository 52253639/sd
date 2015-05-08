package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractAndTaskRelCollection extends AbstractObjectCollection 
{
    public ContractAndTaskRelCollection()
    {
        super(ContractAndTaskRelInfo.class);
    }
    public boolean add(ContractAndTaskRelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractAndTaskRelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractAndTaskRelInfo item)
    {
        return removeObject(item);
    }
    public ContractAndTaskRelInfo get(int index)
    {
        return(ContractAndTaskRelInfo)getObject(index);
    }
    public ContractAndTaskRelInfo get(Object key)
    {
        return(ContractAndTaskRelInfo)getObject(key);
    }
    public void set(int index, ContractAndTaskRelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractAndTaskRelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractAndTaskRelInfo item)
    {
        return super.indexOf(item);
    }
}