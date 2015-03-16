package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayPlanNewPlamCollection extends AbstractObjectCollection 
{
    public ContractPayPlanNewPlamCollection()
    {
        super(ContractPayPlanNewPlamInfo.class);
    }
    public boolean add(ContractPayPlanNewPlamInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayPlanNewPlamCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayPlanNewPlamInfo item)
    {
        return removeObject(item);
    }
    public ContractPayPlanNewPlamInfo get(int index)
    {
        return(ContractPayPlanNewPlamInfo)getObject(index);
    }
    public ContractPayPlanNewPlamInfo get(Object key)
    {
        return(ContractPayPlanNewPlamInfo)getObject(key);
    }
    public void set(int index, ContractPayPlanNewPlamInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayPlanNewPlamInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayPlanNewPlamInfo item)
    {
        return super.indexOf(item);
    }
}