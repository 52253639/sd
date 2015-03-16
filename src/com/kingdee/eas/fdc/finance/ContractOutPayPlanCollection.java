package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractOutPayPlanCollection extends AbstractObjectCollection 
{
    public ContractOutPayPlanCollection()
    {
        super(ContractOutPayPlanInfo.class);
    }
    public boolean add(ContractOutPayPlanInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractOutPayPlanCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractOutPayPlanInfo item)
    {
        return removeObject(item);
    }
    public ContractOutPayPlanInfo get(int index)
    {
        return(ContractOutPayPlanInfo)getObject(index);
    }
    public ContractOutPayPlanInfo get(Object key)
    {
        return(ContractOutPayPlanInfo)getObject(key);
    }
    public void set(int index, ContractOutPayPlanInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractOutPayPlanInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractOutPayPlanInfo item)
    {
        return super.indexOf(item);
    }
}