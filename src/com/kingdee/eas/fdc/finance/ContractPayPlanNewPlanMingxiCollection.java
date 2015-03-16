package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayPlanNewPlanMingxiCollection extends AbstractObjectCollection 
{
    public ContractPayPlanNewPlanMingxiCollection()
    {
        super(ContractPayPlanNewPlanMingxiInfo.class);
    }
    public boolean add(ContractPayPlanNewPlanMingxiInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayPlanNewPlanMingxiCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayPlanNewPlanMingxiInfo item)
    {
        return removeObject(item);
    }
    public ContractPayPlanNewPlanMingxiInfo get(int index)
    {
        return(ContractPayPlanNewPlanMingxiInfo)getObject(index);
    }
    public ContractPayPlanNewPlanMingxiInfo get(Object key)
    {
        return(ContractPayPlanNewPlanMingxiInfo)getObject(key);
    }
    public void set(int index, ContractPayPlanNewPlanMingxiInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayPlanNewPlanMingxiInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayPlanNewPlanMingxiInfo item)
    {
        return super.indexOf(item);
    }
}