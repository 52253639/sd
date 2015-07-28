package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractTypeCollection extends AbstractObjectCollection 
{
    public ContractTypeCollection()
    {
        super(ContractTypeInfo.class);
    }
    public boolean add(ContractTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractTypeInfo item)
    {
        return removeObject(item);
    }
    public ContractTypeInfo get(int index)
    {
        return(ContractTypeInfo)getObject(index);
    }
    public ContractTypeInfo get(Object key)
    {
        return(ContractTypeInfo)getObject(key);
    }
    public void set(int index, ContractTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractTypeInfo item)
    {
        return super.indexOf(item);
    }
}