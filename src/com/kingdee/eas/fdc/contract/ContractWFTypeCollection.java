package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWFTypeCollection extends AbstractObjectCollection 
{
    public ContractWFTypeCollection()
    {
        super(ContractWFTypeInfo.class);
    }
    public boolean add(ContractWFTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWFTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWFTypeInfo item)
    {
        return removeObject(item);
    }
    public ContractWFTypeInfo get(int index)
    {
        return(ContractWFTypeInfo)getObject(index);
    }
    public ContractWFTypeInfo get(Object key)
    {
        return(ContractWFTypeInfo)getObject(key);
    }
    public void set(int index, ContractWFTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWFTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWFTypeInfo item)
    {
        return super.indexOf(item);
    }
}