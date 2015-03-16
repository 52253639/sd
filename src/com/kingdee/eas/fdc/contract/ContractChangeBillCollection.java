package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractChangeBillCollection extends AbstractObjectCollection 
{
    public ContractChangeBillCollection()
    {
        super(ContractChangeBillInfo.class);
    }
    public boolean add(ContractChangeBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractChangeBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractChangeBillInfo item)
    {
        return removeObject(item);
    }
    public ContractChangeBillInfo get(int index)
    {
        return(ContractChangeBillInfo)getObject(index);
    }
    public ContractChangeBillInfo get(Object key)
    {
        return(ContractChangeBillInfo)getObject(key);
    }
    public void set(int index, ContractChangeBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractChangeBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractChangeBillInfo item)
    {
        return super.indexOf(item);
    }
}