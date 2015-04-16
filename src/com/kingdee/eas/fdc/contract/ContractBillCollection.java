package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractBillCollection extends AbstractObjectCollection 
{
    public ContractBillCollection()
    {
        super(ContractBillInfo.class);
    }
    public boolean add(ContractBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractBillInfo item)
    {
        return removeObject(item);
    }
    public ContractBillInfo get(int index)
    {
        return(ContractBillInfo)getObject(index);
    }
    public ContractBillInfo get(Object key)
    {
        return(ContractBillInfo)getObject(key);
    }
    public void set(int index, ContractBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractBillInfo item)
    {
        return super.indexOf(item);
    }
}