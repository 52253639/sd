package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayPlanCollection extends AbstractObjectCollection 
{
    public ContractPayPlanCollection()
    {
        super(ContractPayPlanInfo.class);
    }
    public boolean add(ContractPayPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayPlanInfo item)
    {
        return removeObject(item);
    }
    public ContractPayPlanInfo get(int index)
    {
        return(ContractPayPlanInfo)getObject(index);
    }
    public ContractPayPlanInfo get(Object key)
    {
        return(ContractPayPlanInfo)getObject(key);
    }
    public void set(int index, ContractPayPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayPlanInfo item)
    {
        return super.indexOf(item);
    }
}