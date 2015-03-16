package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractChangeSettleBillCollection extends AbstractObjectCollection 
{
    public ContractChangeSettleBillCollection()
    {
        super(ContractChangeSettleBillInfo.class);
    }
    public boolean add(ContractChangeSettleBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractChangeSettleBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractChangeSettleBillInfo item)
    {
        return removeObject(item);
    }
    public ContractChangeSettleBillInfo get(int index)
    {
        return(ContractChangeSettleBillInfo)getObject(index);
    }
    public ContractChangeSettleBillInfo get(Object key)
    {
        return(ContractChangeSettleBillInfo)getObject(key);
    }
    public void set(int index, ContractChangeSettleBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractChangeSettleBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractChangeSettleBillInfo item)
    {
        return super.indexOf(item);
    }
}