package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractSettlementBillCollection extends AbstractObjectCollection 
{
    public ContractSettlementBillCollection()
    {
        super(ContractSettlementBillInfo.class);
    }
    public boolean add(ContractSettlementBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractSettlementBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractSettlementBillInfo item)
    {
        return removeObject(item);
    }
    public ContractSettlementBillInfo get(int index)
    {
        return(ContractSettlementBillInfo)getObject(index);
    }
    public ContractSettlementBillInfo get(Object key)
    {
        return(ContractSettlementBillInfo)getObject(key);
    }
    public void set(int index, ContractSettlementBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractSettlementBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractSettlementBillInfo item)
    {
        return super.indexOf(item);
    }
}