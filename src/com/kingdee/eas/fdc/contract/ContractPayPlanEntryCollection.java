package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayPlanEntryCollection extends AbstractObjectCollection 
{
    public ContractPayPlanEntryCollection()
    {
        super(ContractPayPlanEntryInfo.class);
    }
    public boolean add(ContractPayPlanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayPlanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayPlanEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractPayPlanEntryInfo get(int index)
    {
        return(ContractPayPlanEntryInfo)getObject(index);
    }
    public ContractPayPlanEntryInfo get(Object key)
    {
        return(ContractPayPlanEntryInfo)getObject(key);
    }
    public void set(int index, ContractPayPlanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayPlanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayPlanEntryInfo item)
    {
        return super.indexOf(item);
    }
}