package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayPlanNewCollection extends AbstractObjectCollection 
{
    public ContractPayPlanNewCollection()
    {
        super(ContractPayPlanNewInfo.class);
    }
    public boolean add(ContractPayPlanNewInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayPlanNewCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayPlanNewInfo item)
    {
        return removeObject(item);
    }
    public ContractPayPlanNewInfo get(int index)
    {
        return(ContractPayPlanNewInfo)getObject(index);
    }
    public ContractPayPlanNewInfo get(Object key)
    {
        return(ContractPayPlanNewInfo)getObject(key);
    }
    public void set(int index, ContractPayPlanNewInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayPlanNewInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayPlanNewInfo item)
    {
        return super.indexOf(item);
    }
}