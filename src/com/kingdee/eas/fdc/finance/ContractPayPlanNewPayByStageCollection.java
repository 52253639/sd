package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractPayPlanNewPayByStageCollection extends AbstractObjectCollection 
{
    public ContractPayPlanNewPayByStageCollection()
    {
        super(ContractPayPlanNewPayByStageInfo.class);
    }
    public boolean add(ContractPayPlanNewPayByStageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractPayPlanNewPayByStageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractPayPlanNewPayByStageInfo item)
    {
        return removeObject(item);
    }
    public ContractPayPlanNewPayByStageInfo get(int index)
    {
        return(ContractPayPlanNewPayByStageInfo)getObject(index);
    }
    public ContractPayPlanNewPayByStageInfo get(Object key)
    {
        return(ContractPayPlanNewPayByStageInfo)getObject(key);
    }
    public void set(int index, ContractPayPlanNewPayByStageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractPayPlanNewPayByStageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractPayPlanNewPayByStageInfo item)
    {
        return super.indexOf(item);
    }
}