package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractWithoutTextCollection extends AbstractObjectCollection 
{
    public ContractWithoutTextCollection()
    {
        super(ContractWithoutTextInfo.class);
    }
    public boolean add(ContractWithoutTextInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractWithoutTextCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractWithoutTextInfo item)
    {
        return removeObject(item);
    }
    public ContractWithoutTextInfo get(int index)
    {
        return(ContractWithoutTextInfo)getObject(index);
    }
    public ContractWithoutTextInfo get(Object key)
    {
        return(ContractWithoutTextInfo)getObject(key);
    }
    public void set(int index, ContractWithoutTextInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractWithoutTextInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractWithoutTextInfo item)
    {
        return super.indexOf(item);
    }
}